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
import org.tat.fni.api.domain.GradeInfo;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.School;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.domain.services.BaseService;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.domain.services.CustomerService;
import org.tat.fni.api.domain.services.GradeInfoService;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.domain.services.ProductService;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.domain.services.SchoolService;
import org.tat.fni.api.domain.services.TownShipService;
import org.tat.fni.api.domain.services.Interfaces.ICustomIdGenerator;
import org.tat.fni.api.domain.services.Interfaces.ILifeProductsProposalService;
import org.tat.fni.api.domain.services.Interfaces.ILifeProposalService;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermProposalInsuredPersonDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifeDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifeProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;
import org.tat.fni.api.common.RiskyOccupation;

@Service
public class StudentLifeProposalService extends BaseService implements ILifeProductsProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private TownShipService townShipService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SalePointService salePointService;

	@Autowired
	private RelationshipService relationshipService;

	@Autowired
	private GradeInfoService gradeInfoServie;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private ILifeProposalService lifeProposalService;

	@Value("${studentLifeProductId}")
	private String studentLifeProductId;
	
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

			StudentLifeDTO studentLifeProposalDTO = (StudentLifeDTO) proposalDto;

			List<LifeProposal> studentLifeProposalList = convertProposalDTOToProposal(studentLifeProposalDTO);
			lifeProposalRepo.saveAll(studentLifeProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(studentLifeProposalList.get(0).getProposalNo());
			String referenceNo = studentLifeProposalList.get(0).getId();
			String referenceType = "STUDENT_LIFE";
			String createdDate = DateUtils.formattedSqlDate(new Date());
			String workflowDate = DateUtils.formattedSqlDate(new Date());

			lifeProposalRepo.saveToWorkflow(id, referenceNo, referenceType, createdDate);
			lifeProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, createdDate, workflowDate);

			return studentLifeProposalList;

		} catch (DAOException e) {

			logger.error("JOEERROR:" + e.getMessage(), e);
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public <T> List<LifeProposal> convertProposalDTOToProposal(T proposalDto) {

		StudentLifeDTO studentLifeProposalDTO = (StudentLifeDTO) proposalDto;

		Optional<Branch> branchOptional = branchService.findById(branchId);
		Optional<PaymentType> paymentTypeOptional = paymentTypeService
				.findById(studentLifeProposalDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentService.findById(studentLifeProposalDTO.getAgentId());
		Optional<SalesPoints> salePointOptional = salePointService.findById(salespointId);

		List<LifeProposal> lifeProposalList = new ArrayList<>();

		try {
			studentLifeProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				Customer customer = lifeProposalService.checkCustomerAvailability(studentLifeProposalDTO.getCustomer());

				if (customer == null) {
					lifeProposalService.createNewCustomer(studentLifeProposalDTO.getCustomer());
				} else {
					lifeProposal.setCustomer(customer);
				}

				lifeProposalService.setPeriodMonthForKeyFacterValue(studentLifeProposalDTO.getPeriodMonth(),
						studentLifeProposalDTO.getPaymentTypeId());

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPerson(insuredPerson));

				lifeProposal.setComplete(false);
//				lifeProposal.setStatus(false);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(studentLifeProposalDTO.getSubmittedDate());
				lifeProposal.setPeriodMonth(studentLifeProposalDTO.getPeriodMonth());
				lifeProposal.setSaleChannelType(SaleChannelType.AGENT);

				if (branchOptional.isPresent()) {
					lifeProposal.setBranch(branchOptional.get());
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

				String proposalNo = customId.getNextId("STUDENT_LIFE_PROPOSAL_NO", null);
				lifeProposal.setStartDate(studentLifeProposalDTO.getStartDate());
				lifeProposal.setEndDate(studentLifeProposalDTO.getEndDate());
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

			StudentLifeProposalInsuredPersonDTO dto = (StudentLifeProposalInsuredPersonDTO) proposalInsuredPersonDTO;

			Optional<Product> productOptional = productService.findById(studentLifeProductId);
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipId());
			Optional<GradeInfo> gradeInfoOptional = gradeInfoServie.findById(dto.getGrateInfoId());
			Optional<School> schoolOptional = schoolService.findById(dto.getSchoolId());

			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());
			residentAddress.setTownship(townshipOptional.get());

			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());

			ProposalInsuredPerson insuredPerson = new ProposalInsuredPerson();
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
			insuredPerson.setAge(dto.getAge());
			insuredPerson.setGender(Gender.valueOf(dto.getGender()));
			insuredPerson.setResidentAddress(residentAddress);
			insuredPerson.setName(name);
			insuredPerson.setParentName(dto.getParentName());
			insuredPerson.setParentIdType(IdType.valueOf(dto.getParentIdType()));
			insuredPerson.setParentIdNo(dto.getParentName());
			insuredPerson.setParentDOB(dto.getParentDOB());

			if (relationshipOptional.isPresent()) {
				insuredPerson.setRelationship(relationshipOptional.get());
			}
			if (gradeInfoOptional.isPresent()) {
				insuredPerson.setGradeInfo(gradeInfoOptional.get());
			}
			if (schoolOptional.isPresent()) {
				insuredPerson.setSchool(schoolOptional.get());
			}
			if (productOptional.isPresent()) {
				insuredPerson.setProduct(productOptional.get());
			}

			String insPersonCodeNo = customId.getNextId("LIFE_INSUREDPERSON_CODENO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);

			insuredPerson.getProduct().getKeyFactorList().forEach(keyfactor -> {
				insuredPerson.getKeyFactorValueList()
						.add(lifeProposalService.createKeyFactorValue(keyfactor, insuredPerson, dto));
			});

			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public <T> InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(T insuredPersonBeneficiariesDto,
			ProposalInsuredPerson insuredPerson) {
		// TODO Auto-generated method stub
		return null;
	}

}
