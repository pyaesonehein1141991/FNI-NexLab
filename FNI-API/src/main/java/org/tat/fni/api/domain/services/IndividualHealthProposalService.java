package org.tat.fni.api.domain.services;

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
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.MedicalPolicy;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonGuardian;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.repository.CustomerRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.repository.MedicalProposalRepository;
import org.tat.fni.api.dto.healthInsuranceDTO.IndividualHealthInsuranceDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.IndividualHealthProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.IndividualHealthProposalInsuredPersonDTO;
import org.tat.fni.api.dto.proposalDTO.ProposalMedicalDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class IndividualHealthProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;
	
	@Autowired
	private LifePolicyService commonLifeProposalService;

	@Autowired
	private BranchService branchService;

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
	private GuardainService guardainService;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Value("${individualHealthProductId}")
	private String individualHealthProductId;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<MedicalProposal> createIndividualHealthDtoToProposal(IndividualHealthInsuranceDTO individualHealthInsuranceDTO) {
		try {
			// convert IndividualHealthProposalDTO to lifeproposal
			List<MedicalProposal> individualHealthProposalList = convertIndividualHealthProposalDTOToProposal(individualHealthInsuranceDTO);
			medicalProposalRepo.saveAll(individualHealthProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(individualHealthProposalList.get(0).getProposalNo());
			String referenceNo = individualHealthProposalList.get(0).getId();
			String referenceType = "HEALTH";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return individualHealthProposalList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	// ForshortTermEndowmentlifeDto to proposal
	public List<MedicalProposal> convertIndividualHealthProposalDTOToProposal(IndividualHealthInsuranceDTO individualHealthInsuranceDTO) {
		List<MedicalProposal> medicalProposalList = new ArrayList<>();
		try {
			Optional<Branch> branchOptional = branchService.findById(individualHealthInsuranceDTO.getBranchId());
			Optional<Organization> organizationOptional = organizationService.findById(individualHealthInsuranceDTO.getOrganizationId());
			Optional<Customer> customerOptional = customerService.findById(individualHealthInsuranceDTO.getCustomerId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService.findById(individualHealthInsuranceDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(individualHealthInsuranceDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService.findById(individualHealthInsuranceDTO.getSalesPointsId());

			individualHealthInsuranceDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();

				medicalProposal.getMedicalProposalInsuredPersonList().add(createInsuredPersonForIndividualHealth(insuredPerson));
				medicalProposal.setComplete(true);
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

				if (customerOptional.isPresent()) {
					medicalProposal.setCustomer(customerOptional.get());
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
				medicalProposalList.add(medicalProposal);
			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return medicalProposalList;
	}

	private MedicalProposalInsuredPerson createInsuredPersonForIndividualHealth(IndividualHealthProposalInsuredPersonDTO dto) {
		try {
			Optional<Product> productOptional = productService.findById(individualHealthProductId);
			Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());
			Optional<RelationShip> relationShipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<MedicalProposalInsuredPersonGuardian> guardianOptional = guardainService.findById(dto.getGuardianId());

			MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();

			insuredPerson.setProduct(productOptional.get());
			insuredPerson.setPremium(dto.getPremium());
			insuredPerson.setUnit(dto.getUnit());
			insuredPerson.setNeedMedicalCheckup(dto.isNeedMedicalCheckup());
			insuredPerson.setCustomer(customerOptional.get());
			insuredPerson.setGuardian(guardianOptional.get());
			insuredPerson.setRejectReason(dto.getRejectReason());

			String insPersonCodeNo = customIdRepo.getNextId("HEALTH_INSUPERSON_CODE_NO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary));
			});
			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	/*
	 * private Customer createNewCustomer(MedicalProposalInsuredPerson dto) {
	 * Customer customer = new Customer(); try {
	 * customer.setInitialId(dto.getInitialId());
	 * customer.setFatherName(dto.getFatherName());
	 * customer.setIdNo(dto.getIdNo());
	 * customer.setDateOfBirth(dto.getDateOfBirth());
	 * customer.setGender(dto.getGender()); customer.setIdType(dto.getIdType());
	 * customer.setResidentAddress(dto.getResidentAddress());
	 * customer.setName(dto.getName());
	 * customer.setOccupation(dto.getOccupation());
	 * customer.setRecorder(dto.getRecorder()); customer =
	 * customerRepo.save(customer); } catch (Exception e) { e.printStackTrace();
	 * } return customer; }
	 */

	private MedicalProposalInsuredPersonBeneficiaries createInsuredPersonBeneficiareis(IndividualHealthProposalInsuredPersonBeneficiariesDTO dto) {
		try {
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
			beneficiary.setPercentage(dto.getPercentage());
			beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
			beneficiary.setIdNo(dto.getIdNo());
			beneficiary.setFatherName(dto.getFatherName());
			beneficiary.setResidentAddress(residentAddress);
			beneficiary.setName(name);
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
	
	public List<MedicalPolicy> retrievePolicyInfo(ProposalMedicalDTO proposalDto) {

		return commonLifeProposalService.retrieveMedicalPolicyList(proposalDto);

	}

}
