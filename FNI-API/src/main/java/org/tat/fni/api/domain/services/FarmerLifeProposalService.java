package org.tat.fni.api.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.common.ResidentAddress;
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalStatus;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifePolicyRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
//import org.tat.fni.api.domain.repository.FarmerRepository;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalInsuredPersonDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class FarmerLifeProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;
	
	@Autowired
	private LifePolicyService commonLifeProposalService;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private TownShipService townShipService;

	@Autowired
	private OccupationService occupationService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SalePointService salePointService;

	@Autowired
	private RelationshipService relationshipService;

	@Autowired
	private RiskyOccupationService riskyOccupationService;

	@Autowired
	private ICustomIdGenerator customId;

	@Value("${farmerProductId}")
	private String farmerpProductId;

	public List<LifeProposal> createFarmerProposalDTOToProposal(FarmerProposalDTO farmerProposalDTO) {
		try {

			List<LifeProposal> farmerProposalList = convertFarmerProposalDTOToProposal(farmerProposalDTO);
			lifeProposalRepo.saveAll(farmerProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(farmerProposalList.get(0).getProposalNo());
			String referenceNo = farmerProposalList.get(0).getId();
			String referenceType = "FARMER";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return farmerProposalList;

		} catch (DAOException e) {

			logger.error("JOEERROR:" + e.getMessage(), e);
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private List<LifeProposal> convertFarmerProposalDTOToProposal(FarmerProposalDTO farmerProposalDTO) {

		Optional<Product> productOptional = productService.findById(farmerProposalDTO.getProductId());
		Optional<Branch> branchOptional = branchService.findById(farmerProposalDTO.getBranchId());
		Optional<Customer> customerOptional = customerService.findById(farmerProposalDTO.getCustomerId());
		Optional<Organization> organizationOptional = organizationService
				.findById(farmerProposalDTO.getOrganizationId());
		Optional<PaymentType> paymentTypeOptional = paymentTypeService.findById(farmerProposalDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentService.findById(farmerProposalDTO.getAgentId());
		Optional<SalesPoints> salePointOptional = salePointService.findById(farmerProposalDTO.getSalesPointsId());

		List<LifeProposal> lifeProposalList = new ArrayList<>();

		try {
			farmerProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPersonForFarmer(insuredPerson));

				lifeProposal.setComplete(true);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(farmerProposalDTO.getSubmittedDate());
				lifeProposal.setPeriodMonth(farmerProposalDTO.getPeriodMonth());
				lifeProposal.setSaleChannelType(SaleChannelType.AGENT);

				if (branchOptional.isPresent()) {
					lifeProposal.setBranch(branchOptional.get());
				}
				if (organizationOptional.isPresent()) {
					lifeProposal.setOrganization(organizationOptional.get());
				}
				if (paymentTypeOptional.isPresent()) {
					lifeProposal.setPaymentType(paymentTypeOptional.get());
				}
				if (agentOptional.isPresent()) {
					lifeProposal.setAgent(agentOptional.get());
				}
				if (salePointOptional.isPresent()) {
					lifeProposal.setSalesPoints(salePointOptional.get());
				}
				if (customerOptional.isPresent()) {
					lifeProposal.setCustomer(customerOptional.get());
				}

				String proposalNo = customId.getNextId("FARMER_PROPOSAL_NO", null);
				lifeProposal.setStartDate(farmerProposalDTO.getStartDate());
				lifeProposal.setEndDate(farmerProposalDTO.getEndDate());
				lifeProposal.setProposalNo(proposalNo);

				lifeProposalList.add(lifeProposal);

			});

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

		return lifeProposalList;
	}

	private ProposalInsuredPerson createInsuredPersonForFarmer(FarmerProposalInsuredPersonDTO dto) {
		try {

			Optional<Product> productOptional = productService.findById(farmerpProductId);
			Optional<Township> townshipOptional = townShipService.findById(dto.getResidentTownshipId());
			Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<RiskyOccupation> riskyOccupationOptional = riskyOccupationService
					.findRiskyOccupationById(dto.getRiskyOccupationID());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());
			residentAddress.setTownship(townshipOptional.get());

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
			insuredPerson.setApproved(dto.isApprove());
			insuredPerson.setNeedMedicalCheckup(dto.isNeedMedicalCheckup());
			insuredPerson.setRejectReason(dto.getRejectReason());
			insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
			insuredPerson.setIdNo(dto.getIdNo());
			insuredPerson.setFatherName(dto.getFatherName());
			insuredPerson.setDateOfBirth(dto.getDateOfBirth());
			insuredPerson.setPhone(dto.getPhone());
			insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
			insuredPerson.setGender(Gender.valueOf(dto.getGender()));
			insuredPerson.setResidentAddress(residentAddress);
			insuredPerson.setName(name);

			if (occupationOptional.isPresent()) {
				insuredPerson.setOccupation(occupationOptional.get());
			}
			if (relationshipOptional.isPresent()) {
				insuredPerson.setRelationship(relationshipOptional.get());
			}
			if (riskyOccupationOptional.isPresent()) {
				insuredPerson.setRiskyOccupation(riskyOccupationOptional.get());
			}

			String insPersonCodeNo = customId.getNextId("LIFE_INSUREDPERSON_CODENO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);

			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary));
			});

			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
			FarmerProposalInsuredPersonBeneficiariesDTO dto) {
		try {
			Optional<Township> townshipOptional = townShipService.findById(dto.getResidentTownshipId());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());
			residentAddress.setTownship(townshipOptional.get());

			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());

			InsuredPersonBeneficiaries beneficiary = new InsuredPersonBeneficiaries();
			beneficiary.setInitialId(dto.getInitialId());
			beneficiary.setPercentage(dto.getPercentage());
			beneficiary.setPhone(dto.getPhone());
			beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
			beneficiary.setIdNo(dto.getIdNo());
			beneficiary.setGender(Gender.valueOf(dto.getGender()));
			beneficiary.setResidentAddress(residentAddress);
			beneficiary.setAge(dto.getAge());
			beneficiary.setName(name);

			if (relationshipOptional.isPresent()) {
				beneficiary.setRelationship(relationshipOptional.get());
			}

			String beneficiaryNo = customId.getNextId("LIFE_BENEFICIARY_NO", null);
			beneficiary.setBeneficiaryNo(beneficiaryNo);

			return beneficiary;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

}
