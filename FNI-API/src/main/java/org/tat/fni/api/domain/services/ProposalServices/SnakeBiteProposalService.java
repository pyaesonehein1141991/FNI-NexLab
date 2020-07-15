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
import org.tat.fni.api.common.emumdata.Gender;
import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.DateUtils;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.RiskyOccupation;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.domain.services.BaseService;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.domain.services.CustomerService;
import org.tat.fni.api.domain.services.OccupationService;
import org.tat.fni.api.domain.services.OrganizationService;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.domain.services.ProductService;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.domain.services.RiskyOccupationService;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.domain.services.TownShipService;
import org.tat.fni.api.domain.services.Interfaces.ICustomIdGenerator;
import org.tat.fni.api.domain.services.Interfaces.ILifeProductsProposalService;
import org.tat.fni.api.domain.services.Interfaces.ILifeProposalService;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class SnakeBiteProposalService extends BaseService implements ILifeProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

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
	private ILifeProposalService lifeProposalService;

	@Value("${snakeBiteProductId}")
	private String snakeBiteProductId;
	
	@Value("${branchId}")
	private String branchId;

	@Value("${salespointId}")
	private String salespointId;

	@Autowired
	private ICustomIdGenerator customId;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> List<LifeProposal> createDtoToProposal(T proposalDto) {
		try {

			SnakeBiteDTO snakeBiteDTO = (SnakeBiteDTO) proposalDto;

			List<LifeProposal> snakeBiteProposalList = convertProposalDTOToProposal(snakeBiteDTO);
			lifeProposalRepo.saveAll(snakeBiteProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(snakeBiteProposalList.get(0).getProposalNo());
			String referenceNo = snakeBiteProposalList.get(0).getId();
			String referenceType = "SNAKE_BITE";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return snakeBiteProposalList;
		} catch (DAOException e) {

			logger.error("JOEERROR:" + e.getMessage(), e);
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public <T> List<LifeProposal> convertProposalDTOToProposal(T proposalDto) {

		SnakeBiteDTO snakeBiteDTO = (SnakeBiteDTO) proposalDto;

		Optional<Product> productOptional = productService.findById(snakeBiteProductId);
		Optional<Branch> branchOptional = branchService.findById(branchId);
		Optional<Organization> organizationOptional = organizationService.findById(snakeBiteDTO.getOrganizationId());
		Optional<PaymentType> paymentTypeOptional = paymentTypeService.findById(snakeBiteDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentService.findById(snakeBiteDTO.getAgentId());
		Optional<SalesPoints> salePointOptional = salePointService.findById(salespointId);

		List<LifeProposal> lifeProposalList = new ArrayList<>();

		try {

			snakeBiteDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				Customer customer = lifeProposalService.checkCustomerAvailability(snakeBiteDTO.getCustomer());

				if (customer == null) {
					lifeProposalService.createNewCustomer(snakeBiteDTO.getCustomer());
				} else {
					lifeProposal.setCustomer(customer);
				}

				lifeProposalService.setPeriodMonthForKeyFacterValue(snakeBiteDTO.getPeriodMonth(),
						snakeBiteDTO.getPaymentTypeId());

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));

				lifeProposal.setComplete(false);
//				lifeProposal.setStatus(false);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(snakeBiteDTO.getSubmittedDate());
				lifeProposal.setPeriodMonth(snakeBiteDTO.getPeriodMonth());
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

				String proposalNo = customId.getNextId("SNAKEBITE_PROPOSAL_NO", null);
				lifeProposal.setStartDate(snakeBiteDTO.getStartDate());
				lifeProposal.setEndDate(snakeBiteDTO.getEndDate());
				lifeProposal.setProposalNo(proposalNo);

				lifeProposal = lifeProposalService.calculatePremium(lifeProposal);
				lifeProposalService.calculateTermPremium(lifeProposal);

				lifeProposalList.add(lifeProposal);

			});

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

		return lifeProposalList;
	}

	@Override
	public <T> ProposalInsuredPerson createInsuredPerson(T proposalInsuredPersonDTO) {
		try {

			SnakeBiteProposalInsuredPersonDTO dto = (SnakeBiteProposalInsuredPersonDTO) proposalInsuredPersonDTO;

			Optional<Product> productOptional = productService.findById(snakeBiteProductId);
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
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
			insuredPerson.setProposedPremium(dto.getProposedPremium());
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
			insuredPerson.setUnit(dto.getUnit());
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

			insuredPerson.getProduct().getKeyFactorList().forEach(keyfactor -> {
				insuredPerson.getKeyFactorValueList()
						.add(lifeProposalService.createKeyFactorValue(keyfactor, insuredPerson, dto));
			});
			
			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(createInsuredPersonBeneficiareis(beneficiary, insuredPerson));
			});

			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public <T> InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(T insuredPersonBeneficiariesDto,
			ProposalInsuredPerson insuredPerson) {
		try {

			SnakeBiteProposalInsuredPersonBeneficiariesDTO dto = (SnakeBiteProposalInsuredPersonBeneficiariesDTO) insuredPersonBeneficiariesDto;

			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
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
			beneficiary.setProposalInsuredPerson(insuredPerson);

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
