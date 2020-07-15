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
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.domain.services.BaseService;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.domain.services.OccupationService;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.domain.services.ProductService;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.domain.services.Interfaces.ICustomIdGenerator;
import org.tat.fni.api.domain.services.Interfaces.ILifeProductsProposalService;
import org.tat.fni.api.domain.services.Interfaces.ILifeProposalService;
import org.tat.fni.api.dto.endowmentLifeDTO.EndowmentLifeDTO;
import org.tat.fni.api.dto.endowmentLifeDTO.EndowmentLifeProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.endowmentLifeDTO.EndowmentLifeProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class EndowmentLifeProposalService extends BaseService implements ILifeProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private BranchService branchService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private OccupationService occupationService;

	@Autowired
	private SalePointService salePointService;

	@Autowired
	private RelationshipService relationshipService;

	@Autowired
	private ICustomIdGenerator customId;

	@Autowired
	private ILifeProposalService lifeProposalService;

	@Value("${publicTermLifeProductId}")
	private String publicTermLifeProductId;
	
	@Value("${branchId}")
	private String branchId;

	@Value("${salespointId}")
	private String salespointId;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> List<LifeProposal> createDtoToProposal(T proposalDto) {
		try {

			EndowmentLifeDTO publicLifeDTO = (EndowmentLifeDTO) proposalDto;

			List<LifeProposal> publicLifeProposalList = convertProposalDTOToProposal(publicLifeDTO);
			lifeProposalRepo.saveAll(publicLifeProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(publicLifeProposalList.get(0).getProposalNo());
			String referenceNo = publicLifeProposalList.get(0).getId();
			String referenceType = "ENDOWMENT_LIFE";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return publicLifeProposalList;

		} catch (DAOException e) {

			logger.error("JOEERROR:" + e.getMessage(), e);
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public <T> List<LifeProposal> convertProposalDTOToProposal(T proposalDto) {

		EndowmentLifeDTO publicLifeDTO = (EndowmentLifeDTO) proposalDto;

		Optional<PaymentType> paymentTypeOptional = paymentTypeService.findById(publicLifeDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentService.findById(publicLifeDTO.getAgentId());
		Optional<Branch> branchOptional = branchService.findById(branchId);
		Optional<SalesPoints> salePointOptional = salePointService.findById(salespointId);

		List<LifeProposal> lifeProposalList = new ArrayList<>();

		try {
			publicLifeDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				Customer customer = lifeProposalService.checkCustomerAvailability(publicLifeDTO.getCustomer());

				if (customer == null) {
					lifeProposal.setCustomer(lifeProposalService.createNewCustomer(publicLifeDTO.getCustomer()));
				} else {
					lifeProposal.setCustomer(customer);
				}

				lifeProposalService.setPeriodMonthForKeyFacterValue(publicLifeDTO.getPeriodMonth(),
						publicLifeDTO.getPaymentTypeId());

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));

				lifeProposal.setComplete(false);
//				lifeProposal.setStatus(false);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(publicLifeDTO.getSubmittedDate());
				lifeProposal.setPeriodMonth(publicLifeDTO.getPeriodMonth());
				lifeProposal.setSaleChannelType(SaleChannelType.AGENT);

				if (paymentTypeOptional.isPresent()) {
					lifeProposal.setPaymentType(paymentTypeOptional.get());
				}
				if (agentOptional.isPresent()) {
					lifeProposal.setAgent(agentOptional.get());
				}
				if (salePointOptional.isPresent()) {
					lifeProposal.setSalesPoints(salePointOptional.get());
				}
				if (branchOptional.isPresent()) {
					lifeProposal.setBranch(branchOptional.get());
				}

				String proposalNo = customId.getNextId("PUBLICLIFE_PROPOSAL_NO", null);
				lifeProposal.setStartDate(publicLifeDTO.getStartDate());
				lifeProposal.setEndDate(publicLifeDTO.getEndDate());
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

			EndowmentLifeProposalInsuredPersonDTO dto = (EndowmentLifeProposalInsuredPersonDTO) proposalInsuredPersonDTO;

			Optional<Product> productOptional = productService.findById(publicTermLifeProductId);
			Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());

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
			insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
			insuredPerson.setIdNo(dto.getIdNo());
			insuredPerson.setFatherName(dto.getFatherName());
			insuredPerson.setDateOfBirth(dto.getDateOfBirth());
			insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
			insuredPerson.setGender(Gender.valueOf(dto.getGender()));
			insuredPerson.setResidentAddress(residentAddress);
			insuredPerson.setName(name);

			if (occupationOptional.isPresent()) {
				insuredPerson.setOccupation(occupationOptional.get());
			}

			String insPersonCodeNo = customId.getNextId("LIFE_INSUREDPERSON_CODENO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);

			insuredPerson.getProduct().getKeyFactorList().forEach(keyfactor -> {
				insuredPerson.getKeyFactorValueList()
						.add(lifeProposalService.createKeyFactorValue(keyfactor, insuredPerson, dto));
			});

			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList()
						.add(createInsuredPersonBeneficiareis(beneficiary, insuredPerson));
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

			EndowmentLifeProposalInsuredPersonBeneficiariesDTO dto = (EndowmentLifeProposalInsuredPersonBeneficiariesDTO) insuredPersonBeneficiariesDto;

			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
//			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());

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
