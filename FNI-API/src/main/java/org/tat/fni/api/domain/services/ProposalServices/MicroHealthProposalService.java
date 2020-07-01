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
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonGuardian;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.repository.MedicalProposalRepository;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.domain.services.CustomerService;
import org.tat.fni.api.domain.services.GuardainService;
import org.tat.fni.api.domain.services.OrganizationService;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.domain.services.ProductService;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.domain.services.TownShipService;
import org.tat.fni.api.domain.services.Interfaces.ICustomIdGenerator;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProductsProposalService;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProposalService;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class MicroHealthProposalService implements IMedicalProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;

	@Autowired
	private BranchService branchService;

	@Autowired
	private GuardainService guardainService;

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
	private RelationshipService relationshipService;
	
	@Autowired
	private IMedicalProposalService medicalProposalService;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Value("${microHealthProductId}")
	private String microHealthProductId;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> List<MedicalProposal> createDtoToProposal(T proposalDto) {
		try {
			MicroHealthDTO microHealthInsuranceDTO = (MicroHealthDTO) proposalDto;
			
			// convert MicroHealthProposalDTO to lifeproposal
			List<MedicalProposal> microHealthProposalList = convertIndividualProposalDTOToProposal(microHealthInsuranceDTO);
			medicalProposalRepo.saveAll(microHealthProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(microHealthProposalList.get(0).getProposalNo());
			String referenceNo = microHealthProposalList.get(0).getId();
			String referenceType = "MICRO_HEALTH";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return microHealthProposalList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public <T> List<MedicalProposal> convertIndividualProposalDTOToProposal(T proposalDto) {
		
		List<MedicalProposal> medicalProposalList = new ArrayList<>();
		MicroHealthDTO microHealthInsuranceDTO = (MicroHealthDTO) proposalDto;
		try {
			Optional<Branch> branchOptional = branchService.findById(microHealthInsuranceDTO.getBranchId());
			Optional<Organization> organizationOptional = organizationService.findById(microHealthInsuranceDTO.getOrganizationId());
			Optional<Customer> customerOptional = customerService.findById(microHealthInsuranceDTO.getCustomerId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService.findById(microHealthInsuranceDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(microHealthInsuranceDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService.findById(microHealthInsuranceDTO.getSalesPointsId());

			microHealthInsuranceDTO.getMicrohealthproposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();
				
				medicalProposalService.setPeriodMonthForKeyFacterValue(
						microHealthInsuranceDTO.getPeriodMonth(), microHealthInsuranceDTO.getPaymentTypeId());

				medicalProposal.getMedicalProposalInsuredPersonList()
					.add(createInsuredPerson(insuredPerson, microHealthInsuranceDTO));
				medicalProposal.setComplete(true);
				medicalProposal.setProposalType(ProposalType.UNDERWRITING);
				medicalProposal.setSubmittedDate(microHealthInsuranceDTO.getSubmittedDate());

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
				medicalProposal.setStartDate(microHealthInsuranceDTO.getStartDate());
				medicalProposal.setEndDate(microHealthInsuranceDTO.getEndDate());
				medicalProposal.setSaleChannelType(SaleChannelType.DIRECTMARKETING);
				medicalProposal.setPeriodMonth(microHealthInsuranceDTO.getPeriodMonth());
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> MedicalProposalInsuredPerson createInsuredPerson(T proposalInsuredPersonDTO, T proposalDto) {
		
		try {
			MicroHealthProposalInsuredPersonDTO dto = (MicroHealthProposalInsuredPersonDTO) proposalInsuredPersonDTO;
			
			Optional<Product> productOptional = productService.findById(microHealthProductId);
			Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());
			Optional<RelationShip> relationShipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<MedicalProposalInsuredPersonGuardian> guardianOptional = guardainService.findById(dto.getGuardianId());

			MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();
			insuredPerson.setAge(dto.getAge());
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
			insuredPerson.getProduct().getKeyFactorList().forEach(keyfactor -> {
				insuredPerson.getKeyFactorValueList().add(medicalProposalService.createKeyFactorValue(keyfactor, insuredPerson, dto));
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
			MicroHealthProposalInsuredPersonBeneficiariesDTO dto = 
					(MicroHealthProposalInsuredPersonBeneficiariesDTO) insuredPersonBeneficiariesDto;
			
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
			beneficiary.setFatherName(dto.getFatherName());
			beneficiary.setIdNo(dto.getIdNo());
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

}
