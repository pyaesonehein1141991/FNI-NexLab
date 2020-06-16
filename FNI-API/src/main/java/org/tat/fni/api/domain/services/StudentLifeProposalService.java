package org.tat.fni.api.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.School;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.dto.proposalDTO.ProposalLifeDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifeDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifeProposalInsuredPersonDTO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class StudentLifeProposalService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;

	@Autowired
	private LifePolicyService commonLifeProposalService;

	@Autowired
	private PaymentTypeService paymentTypeService;

	@Autowired
	private AgentService agentService;

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
	private ICustomIdGenerator customId;

	public List<LifeProposal> createStudentLifeProposalDTOToProposal(StudentLifeDTO studentLifeProposalDTO) {
		try {

			List<LifeProposal> studentLifeProposalList = convertStudentLifeProposalDTOToProposal(
					studentLifeProposalDTO);
			lifeProposalRepo.saveAll(studentLifeProposalList);

			String id = DateUtils.formattedSqlDate(new Date()).concat(studentLifeProposalList.get(0).getProposalNo());
			String referenceNo = studentLifeProposalList.get(0).getId();
			String referenceType = "FARMER";
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

	private List<LifeProposal> convertStudentLifeProposalDTOToProposal(StudentLifeDTO studentLifeProposalDTO) {

		Optional<Branch> branchOptional = branchService.findById(studentLifeProposalDTO.getBranchId());
		Optional<Customer> customerOptional = customerService.findById(studentLifeProposalDTO.getCustomerId());
		Optional<PaymentType> paymentTypeOptional = paymentTypeService
				.findById(studentLifeProposalDTO.getPaymentTypeId());
		Optional<Agent> agentOptional = agentService.findById(studentLifeProposalDTO.getAgentId());
		Optional<SalesPoints> salePointOptional = salePointService.findById(studentLifeProposalDTO.getSalesPointsId());

		List<LifeProposal> lifeProposalList = new ArrayList<>();

		try {
			studentLifeProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {

				LifeProposal lifeProposal = new LifeProposal();

				lifeProposal.getProposalInsuredPersonList().add(createInsuredPersonForStudent(insuredPerson));

				lifeProposal.setComplete(true);
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
				if (customerOptional.isPresent()) {
					lifeProposal.setCustomer(customerOptional.get());
				}

				String proposalNo = customId.getNextId("STUDENT_LIFE_PROPOSAL_NO", null);
				lifeProposal.setStartDate(studentLifeProposalDTO.getStartDate());
				lifeProposal.setEndDate(studentLifeProposalDTO.getEndDate());
				lifeProposal.setProposalNo(proposalNo);

				lifeProposalList.add(lifeProposal);

			});

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

		return lifeProposalList;
	}

	private ProposalInsuredPerson createInsuredPersonForStudent(StudentLifeProposalInsuredPersonDTO dto) {
		try {

			Optional<Township> townshipOptional = townShipService.findById(dto.getResidentTownshipId());
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
			insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
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

			String insPersonCodeNo = customId.getNextId("LIFE_INSUREDPERSON_CODENO", null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);

			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

//	public List<LifePolicy> retrievePolicyInfo(ProposalLifeDTO proposalDto) {
//
//		return commonLifeProposalService.retrieveLifePolicyList(proposalDto);
//
//	}

}
