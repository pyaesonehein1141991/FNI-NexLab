package org.tat.fni.api.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.KeyFactorChecker;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.IPremiumCalculatorService;
import org.tat.fni.api.domain.InsuredPersonAddon;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.PremiumCalData;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.CustomerRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.commonServices.LifeProposalService;
import org.tat.fni.api.dto.InsuredPersonInfoDTO;
import org.tat.fni.api.dto.proposalDTO.ProposalLifeDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermEndowmentLifeDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;
import org.tat.fni.api.exception.SystemException;

@Service
public class ShortTermLifeProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Autowired
	private LifeProposalService commonLifeProposalService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private IPremiumCalculatorService premiumCalculatorService;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private SalePointService salePointService;

	@Autowired
	private ProductService productService;

	@Autowired
	private TownShipService townShipService;

	@Autowired
	private OccupationService occupationService;

	@Autowired
	private RelationshipService relationshipService;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private RiskyOccupationService riskyOccupationService;

	private InsuredPersonInfoDTO insuredPersonInfoDTO;

	@Value("${shorttermLifeProductId}")
	private String shorttermLifeProductId;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifeProposal> createShortTermEndowmentLifeDtoToProposal(
			ShortTermEndowmentLifeDTO shortTermEndowmentLifeDto) {
		try {
			// convert shortTermEndowmentlifeProposalDTO to lifeproposal
			List<LifeProposal> shortTermEndowmentLifeProposalList = convertShortTermEndowmentLifeProposalDTOToProposal(
					shortTermEndowmentLifeDto);

			;

			shortTermEndowmentLifeProposalList = lifeProposalRepo.saveAll(shortTermEndowmentLifeProposalList);

			String id = DateUtils.formattedSqlDate(new Date())
					.concat(shortTermEndowmentLifeProposalList.get(0).getProposalNo());
			String referenceNo = shortTermEndowmentLifeProposalList.get(0).getId();
			// TODO FIXME PSH Modify for All product
			String referenceType = "SHORT_ENDOWMENT_LIFE";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return shortTermEndowmentLifeProposalList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	// ForshortTermEndowmentlifeDto to proposal
	public List<LifeProposal> convertShortTermEndowmentLifeProposalDTOToProposal(
			ShortTermEndowmentLifeDTO shortTermEndowmentLifeDto) {
		List<LifeProposal> lifeProposalList = new ArrayList<>();
		try {
			Optional<Organization> organizationOptional = organizationService
					.findById(shortTermEndowmentLifeDto.getOrganizationId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(shortTermEndowmentLifeDto.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(shortTermEndowmentLifeDto.getAgentId());
			Optional<Customer> customerOptional = customerService.findById(shortTermEndowmentLifeDto.getCustomerId());
			Optional<Branch> branchOptional = branchService.findById(shortTermEndowmentLifeDto.getBranchId());
			Optional<SalesPoints> salesPointsOptional = salePointService
					.findById(shortTermEndowmentLifeDto.getSalesPointsId());

			shortTermEndowmentLifeDto.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPersonForShortTerm(insuredPerson));

				lifeProposal.setComplete(true);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(shortTermEndowmentLifeDto.getSubmittedDate());

				if (organizationOptional.isPresent()) {
					lifeProposal.setOrganization(organizationOptional.get());
				}

				if (agentOptional.isPresent()) {
					lifeProposal.setAgent(agentOptional.get());
				}

				if (paymentTypeOptional.isPresent()) {
					lifeProposal.setPaymentType(paymentTypeOptional.get());
				}

				if (customerOptional.isPresent()) {
					lifeProposal.setCustomer(customerOptional.get());
				}

				if (branchOptional.isPresent()) {
					lifeProposal.setBranch(branchOptional.get());
				}

				if (salesPointsOptional.isPresent()) {
					lifeProposal.setSalesPoints(salesPointsOptional.get());
				}

				String proposalNo = customIdRepo.getNextId("SHORT_ENDOWMENT_PROPOSAL_NO", null);
				lifeProposal.setStartDate(shortTermEndowmentLifeDto.getStartDate());
				lifeProposal.setSaleChannelType(SaleChannelType.AGENT);
				lifeProposal.setEndDate(shortTermEndowmentLifeDto.getEndDate());
				lifeProposal.setProposalNo(proposalNo);
				lifeProposalList.add(lifeProposal);

				// calculateTermPremium(lifeProposal);

				// calculatePremium(lifeProposal);

			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return lifeProposalList;
	}

	private ProposalInsuredPerson createInsuredPersonForShortTerm(ShortTermProposalInsuredPersonDTO dto) {
		try {
			Optional<Product> productOptional = productService.findById(shorttermLifeProductId);
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
			Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());
			Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<RiskyOccupation> riskyOccupationOptional = riskyOccupationService
					.findRiskyOccupationById(dto.getRiskoccupationID());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());

			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());

			ProposalInsuredPerson insuredPerson = new ProposalInsuredPerson();

			insuredPerson.setProduct(productOptional.get());
			insuredPerson.setInitialId(dto.getInitialId());
			insuredPerson.setProposedSumInsured(dto.getProposedSumInsured());
			insuredPerson.setProposedPremium(dto.getProposedPremium());
			insuredPerson.setApprovedSumInsured(dto.getApprovedSumInsured());
			insuredPerson.setBasicTermPremium(dto.getBasicTermPremium());
			insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
			insuredPerson.setIdNo(dto.getIdNo());
			insuredPerson.setNeedMedicalCheckup(dto.isNeedMedicalCheckup());
			insuredPerson.setRejectReason(dto.getRejectReason());
			insuredPerson.setFatherName(dto.getFatherName());
			insuredPerson.setDateOfBirth(dto.getDateOfBirth());
			insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
			insuredPerson.setGender(Gender.valueOf(dto.getGender()));
			insuredPerson.setResidentAddress(residentAddress);
			insuredPerson.setPhone(dto.getPhone());
			insuredPerson.setName(name);
			if (occupationOptional.isPresent()) {
				insuredPerson.setOccupation(occupationOptional.get());
			}
			if (customerOptional.isPresent()) {
				insuredPerson.setCustomer(customerOptional.get());
			} else {
				insuredPerson.setCustomer(createNewCustomer(insuredPerson));

			}
			if (riskyOccupationOptional.isPresent()) {
				insuredPerson.setRiskyOccupation(riskyOccupationOptional.get());
			}
			if (relationshipOptional.isPresent()) {
				insuredPerson.setRelationship(relationshipOptional.get());
			}

			for (InsuredPersonKeyFactorValue vehKF : insuredPerson.getKeyFactorValueList()) {
				KeyFactor kf = vehKF.getKeyFactor();
				if (KeyFactorChecker.isSumInsured(kf)) {
					vehKF.setValue(dto.getApprovedSumInsured() + "");
				} else if (KeyFactorChecker.isAge(kf) || KeyFactorChecker.isMedicalAge(kf)) {
					vehKF.setValue(dto.getAge() + "");
				} else if (KeyFactorChecker.isTerm(kf)) {
					vehKF.setValue(dto.getPeriodMonth() + "");
				}
			}

			String insPersonCodeNo = customIdRepo.getNextId("LIFE_INSUREDPERSON_CODENO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary));

			});
			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private Customer createNewCustomer(ProposalInsuredPerson dto) {
		Customer customer = new Customer();
		try {
			customer.setInitialId(dto.getInitialId());
			customer.setFatherName(dto.getFatherName());
			customer.setIdNo(dto.getIdNo());
			customer.setDateOfBirth(dto.getDateOfBirth());
			customer.setGender(dto.getGender());
			customer.setIdType(dto.getIdType());
			customer.setResidentAddress(dto.getResidentAddress());
			customer.setName(dto.getName());
			customer.setOccupation(dto.getOccupation());
			customer.setRecorder(dto.getRecorder());
			customer = customerRepo.save(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}

	private InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
			ShortTermProposalInsuredPersonBeneficiariesDTO dto) {
		try {
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());
			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());

			InsuredPersonBeneficiaries beneficiary = new InsuredPersonBeneficiaries();
			beneficiary.setInitialId(dto.getInitialId());
			beneficiary.setPercentage(dto.getPercentage());
			beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
			beneficiary.setIdNo(dto.getIdNo());
			beneficiary.setGender(Gender.valueOf(dto.getGender()));
			beneficiary.setResidentAddress(residentAddress);
			beneficiary.setName(name);
			if (relationshipOptional.isPresent()) {
				beneficiary.setRelationship(relationshipOptional.get());
			}
			String beneficiaryNo = customIdRepo.getNextId("LIFE_BENEFICIARY_NO", null);
			beneficiary.setBeneficiaryNo(beneficiaryNo);
			return beneficiary;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	public void calculateTermPremium(LifeProposal lifeProposal) {
		int paymentType = lifeProposal.getPaymentType().getMonth();
		// boolean isStudentLife = KeyFactorChecker
		// .isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId());
		int paymentTerm = 0;
		double premium = 0, termPremium = 0, addOnPremium = 0;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			premium = pv.getProposedPremium();
			if (paymentType > 0) {
				// if (isStudentLife) {
				// paymentTerm = (lifeProposal.getPeriodOfYears() - 3) * 12 /
				// paymentType;
				// } else
				paymentTerm = lifeProposal.getPeriodMonth() / paymentType;// lifeProposal.getPeriodOfYear()*12

				termPremium = (paymentType * premium) / 12;
				pv.setBasicTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum ***
				if (KeyFactorChecker
						.isPersonalAccident(lifeProposal.getProposalInsuredPersonList().get(0).getProduct()))
					termPremium = (premium / 12) * lifeProposal.getPeriodMonth();
				else
					termPremium = (lifeProposal.getPeriodOfYears() * premium);
				pv.setBasicTermPremium(termPremium);
			}
			lifeProposal.setPaymentTerm(paymentTerm);

			addOnPremium = pv.getAddOnPremium();
			if (paymentType > 0) {
				termPremium = (paymentType * addOnPremium) / 12;
				pv.setAddOnTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum AddOn Premium***
				termPremium = (lifeProposal.getPeriodMonth() * addOnPremium);
				pv.setAddOnTermPremium(termPremium);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void calculatePremium(LifeProposal lifeProposal) {
		Double premium;
		Double premiumRate;
		double proposedSI;
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			proposedSI = pv.getProposedSumInsured();
			/* set approved SI (after approved, edit SI from enquire) */
			if (pv.isApproved()) {
				pv.setApprovedSumInsured(proposedSI);
			}
			Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
			for (InsuredPersonKeyFactorValue insukf : pv.getKeyFactorValueList()) {
				keyfatorValueMap.put(insukf.getKeyFactor(), insukf.getValue());
			}
			premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
			pv.setPremiumRate(premiumRate);
			premium = premiumCalculatorService.calulatePremium(premiumRate, product,
					new PremiumCalData(null, proposedSI, null, null));

			pv.setProposedPremium(premium);

			if (premium == null || premium < 0) {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}

			List<InsuredPersonAddon> insuredPersonAddOnList = pv.getInsuredPersonAddOnList();
			if (insuredPersonAddOnList != null && !insuredPersonAddOnList.isEmpty()) {
				for (InsuredPersonAddon insuredPersonAddOn : insuredPersonAddOnList) {
					double addOnPremium = 0.0;
					double addOnPremiumRate = 0.0;
					Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
					if (insuredPersonAddOn.getAddOn().isBaseOnKeyFactor()) {
						for (KeyFactor kf : insuredPersonAddOn.getAddOn().getKeyFactorList()) {
							innerLoop: for (InsuredPersonKeyFactorValue ipKf : pv.getKeyFactorValueList()) {
								if (kf.equals(ipKf.getKeyFactor())) {
									addOnKeyfatorValueMap.put(kf, ipKf.getValue());
									break innerLoop;
								}
							}
							if (KeyFactorChecker.isGender(kf)) {
								addOnKeyfatorValueMap.put(kf, pv.getGender().getLabel());
							}
						}
					}
					addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap,
							insuredPersonAddOn.getAddOn(),
							new PremiumCalData(insuredPersonAddOn.getProposedSumInsured(), proposedSI,
									pv.getProposedPremium(), null));
					addOnPremiumRate = premiumCalculatorService.findPremiumRate(addOnKeyfatorValueMap,
							insuredPersonAddOn.getAddOn());
					insuredPersonAddOn.setPremiumRate(addOnPremiumRate);
					insuredPersonAddOn.setProposedPremium(addOnPremium);
				}
			}

		}
	}

	public List<LifePolicy> retrievePolicyInfo(ProposalLifeDTO proposalDto) {

		return commonLifeProposalService.retrieveLifePolicyList(proposalDto);

	}

}
