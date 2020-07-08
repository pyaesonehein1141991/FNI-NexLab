package org.tat.fni.api.domain.services.ProposalServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.MedicalKeyFactorValue;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonAddOn;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.addon.AddOn;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.repository.MedicalProposalRepository;
import org.tat.fni.api.domain.services.AddOnService;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.domain.services.GuardainService;
import org.tat.fni.api.domain.services.OccupationService;
import org.tat.fni.api.domain.services.OrganizationService;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.domain.services.ProductService;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.domain.services.TownShipService;
import org.tat.fni.api.domain.services.Interfaces.ICustomIdGenerator;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProductsProposalService;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProposalService;
import org.tat.fni.api.dto.InsuredPersonAddOnDTO;
import org.tat.fni.api.dto.MedProInsuAddOnDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.GroupHealthInsuranceDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.HealthProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.HealthProposalInsuredPersonDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.IndividualHealthInsuranceDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class HealthProposalService implements IMedicalProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;

	@Autowired
	private BranchService branchService;

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
	private GuardainService guardainService;

	@Autowired
	private AddOnService addOnService;

	@Autowired
	private IMedicalProposalService medicalProposalService;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Value("${individualHealthProductId}")
	private String individualHealthProductId;

	@Value("${groupHealthProductId}")
	private String groupHealthProductId;

	private List<MedProInsuAddOnDTO> insuredPersonAddOnList;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> List<MedicalProposal> createDtoToProposal(T proposalDto) {
		try {
			// convert IndividualHealthProposalDTO to lifeproposal
			List<MedicalProposal> healthProposalList = proposalDto instanceof IndividualHealthInsuranceDTO
					? convertIndividualProposalDTOToProposal((IndividualHealthInsuranceDTO) proposalDto)
					: convertGroupProposalDTOToProposal((GroupHealthInsuranceDTO) proposalDto);

			medicalProposalRepo.saveAll(healthProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(healthProposalList.get(0).getProposalNo());
			String referenceNo = healthProposalList.get(0).getId();
			String referenceType = "HEALTH";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return healthProposalList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public <T> List<MedicalProposal> convertIndividualProposalDTOToProposal(T proposalDto) {

		List<MedicalProposal> medicalProposalList = new ArrayList<>();
		IndividualHealthInsuranceDTO individualHealthInsuranceDTO = (IndividualHealthInsuranceDTO) proposalDto;

		try {
			Optional<Branch> branchOptional = branchService.findById(individualHealthInsuranceDTO.getBranchId());
			Optional<Organization> organizationOptional = organizationService
					.findById(individualHealthInsuranceDTO.getOrganizationId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(individualHealthInsuranceDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(individualHealthInsuranceDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService
					.findById(individualHealthInsuranceDTO.getSalesPointsId());

			individualHealthInsuranceDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();
				
				Customer customer = medicalProposalService.checkCustomerAvailability(individualHealthInsuranceDTO.getCustomer());

				if (customer == null) {
					medicalProposalService.createNewCustomer(individualHealthInsuranceDTO.getCustomer());
				} else {
					medicalProposal.setCustomer(customer);
				}

				medicalProposalService.setPeriodMonthForKeyFacterValue(individualHealthInsuranceDTO.getPeriodMonth(),
						individualHealthInsuranceDTO.getPaymentTypeId());

				medicalProposal.getMedicalProposalInsuredPersonList()
						.add(createInsuredPerson(insuredPerson, individualHealthInsuranceDTO));
				medicalProposal.setComplete(true);
				medicalProposal.setStatus(false);
				medicalProposal.setProposalType(ProposalType.UNDERWRITING);
				medicalProposal.setSubmittedDate(individualHealthInsuranceDTO.getSubmittedDate());

				if (organizationOptional.isPresent()) {
					medicalProposal.setOrganization(organizationOptional.get());
				}

				if (agentOptional.isPresent()) {
					medicalProposal.setAgent(agentOptional.get());
				}

				if (paymentTypeOptional.isPresent()) {
					medicalProposal.setPaymentType(paymentTypeOptional.get());
				}
				if (branchOptional.isPresent()) {
					medicalProposal.setBranch(branchOptional.get());
				}

				if (salesPointsOptional.isPresent()) {
					medicalProposal.setSalesPoints(salesPointsOptional.get());
				}

				String proposalNo = customIdRepo.getNextId("HEALTH_PROPOSAL_NO", null);
				medicalProposal.setStartDate(individualHealthInsuranceDTO.getStartDate());
				medicalProposal.setEndDate(individualHealthInsuranceDTO.getEndDate());
				medicalProposal.setSaleChannelType(SaleChannelType.AGENT);
				medicalProposal.setPeriodMonth(individualHealthInsuranceDTO.getPeriodMonth());
				medicalProposal.setProposalNo(proposalNo);

				medicalProposal = medicalProposalService.calculatePremium(medicalProposal);
				medicalProposalService.calculateTermPremium(medicalProposal);

				medicalProposalList.add(medicalProposal);
			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return medicalProposalList;
	}

	@Override
	public <T> List<MedicalProposal> convertGroupProposalDTOToProposal(T proposalDto) {

		List<MedicalProposal> medicalProposalList = new ArrayList<>();
		GroupHealthInsuranceDTO groupHealthInsuranceDTO = (GroupHealthInsuranceDTO) proposalDto;

		try {
			Optional<Branch> branchOptional = branchService.findById(groupHealthInsuranceDTO.getBranchId());
			Optional<Organization> organizationOptional = organizationService
					.findById(groupHealthInsuranceDTO.getOrganizationId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(groupHealthInsuranceDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(groupHealthInsuranceDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService
					.findById(groupHealthInsuranceDTO.getSalesPointsId());

			groupHealthInsuranceDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();

				medicalProposal.getMedicalProposalInsuredPersonList()
						.add(createInsuredPerson(insuredPerson, groupHealthInsuranceDTO));
				medicalProposal.setComplete(true);
				medicalProposal.setProposalType(ProposalType.UNDERWRITING);
				medicalProposal.setSubmittedDate(groupHealthInsuranceDTO.getSubmittedDate());

				if (organizationOptional.isPresent()) {
					medicalProposal.setOrganization(organizationOptional.get());
				}

				if (agentOptional.isPresent()) {
					medicalProposal.setAgent(agentOptional.get());
				}

				if (paymentTypeOptional.isPresent()) {
					medicalProposal.setPaymentType(paymentTypeOptional.get());
				}
				if (branchOptional.isPresent()) {
					medicalProposal.setBranch(branchOptional.get());
				}

				if (salesPointsOptional.isPresent()) {
					medicalProposal.setSalesPoints(salesPointsOptional.get());
				}

				String proposalNo = customIdRepo.getNextId("HEALTH_PROPOSAL_NO", null);
				medicalProposal.setStartDate(groupHealthInsuranceDTO.getStartDate());
				medicalProposal.setEndDate(groupHealthInsuranceDTO.getEndDate());
				medicalProposal.setSaleChannelType(SaleChannelType.AGENT);
				medicalProposal.setPeriodMonth(groupHealthInsuranceDTO.getPeriodMonth());
				medicalProposal.setProposalNo(proposalNo);

				medicalProposal = medicalProposalService.calculatePremium(medicalProposal);
				medicalProposalService.calculateTermPremium(medicalProposal);

				medicalProposalList.add(medicalProposal);
			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return medicalProposalList;
	}

	@Override
	public <T> MedicalProposalInsuredPerson createInsuredPerson(T proposalInsuredPersonDTO, T proposalDto) {

		HealthProposalInsuredPersonDTO dto = (HealthProposalInsuredPersonDTO) proposalInsuredPersonDTO;
		try {
			Optional<Product> productOptional = proposalDto instanceof IndividualHealthInsuranceDTO
					? productService.findById(individualHealthProductId)
					: productService.findById(groupHealthProductId);
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());

			MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();

			insuredPerson.setAge(dto.getAge());
			insuredPerson.setProduct(productOptional.get());

			insuredPerson.setPremium(dto.getPremium());
			insuredPerson.setUnit(dto.getUnit());
			insuredPerson.setNeedMedicalCheckup(dto.isNeedMedicalCheckup());

			String insPersonCodeNo = customIdRepo.getNextId("HEALTH_INSUPERSON_CODE_NO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary));
			});
			insuredPerson.getProduct().getKeyFactorList().forEach(keyfactor -> {
				insuredPerson.getKeyFactorValueList()
						.add(medicalProposalService.createKeyFactorValue(keyfactor, insuredPerson, dto));
			});
			dto.getInsuredPersonAddonOnList().forEach(addon -> {
				insuredPerson.getInsuredPersonAddOnList().add(createInsuredPersonAddon(addon, insuredPerson));
			});

			return insuredPerson;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public InsuredPersonKeyFactorValue createKeyFactorValue(KeyFactor keyfactor, ProposalInsuredPerson insuredPerson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer createNewCustomer(ProposalInsuredPerson insuredPersonDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MedicalProposalInsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
			T insuredPersonBeneficiariesDto) {
		try {
			HealthProposalInsuredPersonBeneficiariesDTO dto = (HealthProposalInsuredPersonBeneficiariesDTO) insuredPersonBeneficiariesDto;

			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());
			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());

			MedicalProposalInsuredPersonBeneficiaries beneficiary = new MedicalProposalInsuredPersonBeneficiaries();
			beneficiary.setInitialId(dto.getInitialId());
			beneficiary.setDateOfBirth(dto.getDateOfBirth());
			beneficiary.setPercentage(dto.getPercentage());
			beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
			beneficiary.setIdNo(dto.getIdNo());
//			beneficiary.setMobile(dto.getMobile());
//			beneficiary.setPhone(dto.getPhone());
			beneficiary.setResidentAddress(residentAddress);
			beneficiary.setName(name);

			if (townshipOptional.isPresent()) {
//				beneficiary.setResidentTownship(townshipOptional.get());
			}
			if (relationshipOptional.isPresent()) {
				beneficiary.setRelationship(relationshipOptional.get());
			}

			String beneficiaryNo = customIdRepo.getNextId("HEALTH_BENEFICIARY_NO", null);
			beneficiary.setBeneficiaryNo(beneficiaryNo);
			return beneficiary;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public MedicalProposalInsuredPersonAddOn createInsuredPersonAddon(InsuredPersonAddOnDTO addOnDTO,
			MedicalProposalInsuredPerson insuredPerson) {

		try {

			AddOn addOn = addOnService.findAddOnById(addOnDTO.getMedicalProductAddOnId());

			MedicalProposalInsuredPersonAddOn addon = new MedicalProposalInsuredPersonAddOn();
			addon.setUnit(addOnDTO.getUnit());
			addon.setSumInsured(insuredPerson.getSumInsured());
			addon.setPremium(addOnDTO.getPremium());
			addon.setAddOn(addOn);
			addon.setKeyFactorValueList(insuredPerson.getKeyFactorValueList());

			return addon;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	/* create KeyfactorValue of addOn */
	private List<MedicalKeyFactorValue> createNewKeyFactorValueList(AddOn addOn) {
		List<MedicalKeyFactorValue> addOnKeyFactorValueList = new ArrayList<MedicalKeyFactorValue>();
		MedicalKeyFactorValue fkv;
		for (KeyFactor kf : addOn.getKeyFactorList()) {
			fkv = new MedicalKeyFactorValue(kf);
			addOnKeyFactorValueList.add(fkv);
		}
		return addOnKeyFactorValueList;
	}

}
