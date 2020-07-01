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
import org.tat.fni.api.domain.Occupation;
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
import org.tat.fni.api.dto.criticalIllnessDTO.CriticalillnessProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.CriticalillnessProposalInsuredPersonDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.GroupCriticalIllnessDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.IndividualCriticalIllnessDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class CriticalillnessProposalService implements IMedicalProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;

	@Autowired
	private BranchService branchService;

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
	private IMedicalProposalService medicalProposalService;

	@Autowired
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Value("${individualCriticalillnessProductID}")
	private String individualCriticalillnessProductID;

	@Value("${groupCriticalillnessProductID}")
	private String groupCriticalillnessProductID;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> List<MedicalProposal> createDtoToProposal(T proposalDto) {

		try {
			// convert CriticalProposalDTO to lifeproposal
			List<MedicalProposal> criticalillnessProposalList = proposalDto instanceof IndividualCriticalIllnessDTO
					? convertIndividualProposalDTOToProposal((IndividualCriticalIllnessDTO) proposalDto)
					: convertGroupProposalDTOToProposal((GroupCriticalIllnessDTO) proposalDto);

			medicalProposalRepo.saveAll(criticalillnessProposalList);

			String id = DateUtils.formattedSqlDate(new Date())
					.concat(criticalillnessProposalList.get(0).getProposalNo());
			String referenceNo = criticalillnessProposalList.get(0).getId();
			String referenceType = "CRITICAL_ILLNESS";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);
			return criticalillnessProposalList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public <T> List<MedicalProposal> convertIndividualProposalDTOToProposal(T proposalDto) {

		List<MedicalProposal> medicalProposalList = new ArrayList<>();
		IndividualCriticalIllnessDTO criticalIllnessDTO = (IndividualCriticalIllnessDTO) proposalDto;

		try {
			Optional<Branch> branchOptional = branchService.findById(criticalIllnessDTO.getBranchId());
			Optional<Organization> organizationOptional = organizationService
					.findById(criticalIllnessDTO.getOrganizationId());
			Optional<Customer> customerOptional = customerService.findById(criticalIllnessDTO.getCustomerId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(criticalIllnessDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(criticalIllnessDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService
					.findById(criticalIllnessDTO.getSalesPointsId());

			criticalIllnessDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();

				medicalProposal.getMedicalProposalInsuredPersonList()
						.add(createInsuredPerson(insuredPerson, criticalIllnessDTO));
				medicalProposal.setComplete(true);
				medicalProposal.setPeriodMonth(criticalIllnessDTO.getPeriodMonth());
				medicalProposal.setProposalType(ProposalType.UNDERWRITING);
				medicalProposal.setSubmittedDate(criticalIllnessDTO.getSubmittedDate());

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
				medicalProposal.setStartDate(criticalIllnessDTO.getStartDate());
				medicalProposal.setEndDate(criticalIllnessDTO.getEndDate());
				medicalProposal.setSaleChannelType(SaleChannelType.AGENT);
				medicalProposal.setPeriodMonth(criticalIllnessDTO.getPeriodMonth());
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
		GroupCriticalIllnessDTO criticalIllnessDTO = (GroupCriticalIllnessDTO) proposalDto;

		try {
			Optional<Organization> organizationOptional = organizationService
					.findById(criticalIllnessDTO.getOrganizationId());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(criticalIllnessDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(criticalIllnessDTO.getAgentId());
			Optional<SalesPoints> salesPointsOptional = salePointService
					.findById(criticalIllnessDTO.getSalesPointsId());

			criticalIllnessDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				MedicalProposal medicalProposal = new MedicalProposal();

				medicalProposal.getMedicalProposalInsuredPersonList()
						.add(createInsuredPerson(insuredPerson, criticalIllnessDTO));
				medicalProposal.setComplete(true);
				medicalProposal.setPeriodMonth(criticalIllnessDTO.getPeriodMonth());
				medicalProposal.setProposalType(ProposalType.UNDERWRITING);
				medicalProposal.setSubmittedDate(criticalIllnessDTO.getSubmittedDate());

				if (organizationOptional.isPresent()) {
					medicalProposal.setOrganization(organizationOptional.get());
				}

				if (agentOptional.isPresent()) {
					medicalProposal.setAgent(agentOptional.get());
				}

				if (paymentTypeOptional.isPresent()) {
					medicalProposal.setPaymentType(paymentTypeOptional.get());
				}
				if (salesPointsOptional.isPresent()) {
					medicalProposal.setSalesPoints(salesPointsOptional.get());
				}

				String proposalNo = customIdRepo.getNextId("HEALTH_PROPOSAL_NO", null);
				medicalProposal.setStartDate(criticalIllnessDTO.getStartDate());
				medicalProposal.setEndDate(criticalIllnessDTO.getEndDate());
				medicalProposal.setSaleChannelType(SaleChannelType.AGENT);
				medicalProposal.setPeriodMonth(criticalIllnessDTO.getPeriodMonth());
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

		try {
			CriticalillnessProposalInsuredPersonDTO dto = (CriticalillnessProposalInsuredPersonDTO) proposalInsuredPersonDTO;

			Optional<Product> productOptional = proposalDto instanceof IndividualCriticalIllnessDTO
					? productService.findById(individualCriticalillnessProductID)
					: productService.findById(groupCriticalillnessProductID);
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
			CriticalillnessProposalInsuredPersonBeneficiariesDTO dto = (CriticalillnessProposalInsuredPersonBeneficiariesDTO) insuredPersonBeneficiariesDto;

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

}
