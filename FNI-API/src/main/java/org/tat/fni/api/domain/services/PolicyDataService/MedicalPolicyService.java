package org.tat.fni.api.domain.services.PolicyDataService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.common.Name;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.MedicalPolicy;
import org.tat.fni.api.domain.MedicalPolicyInsuredPerson;
import org.tat.fni.api.domain.MedicalPolicyInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.repository.MedicalPolicyRepository;
import org.tat.fni.api.domain.repository.MedicalProposalRepository;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataMedicalDTO;
import org.tat.fni.api.dto.retrieveDTO.NameDto;
import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BeneficiaryMedicalData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.InsuredPersonMedicalData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class MedicalPolicyService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;

	@Autowired
	private MedicalPolicyRepository medicalPolicyRepo;
	
	// == declare class variable ==
	List<MedicalPolicy> medicalPolicyList;

	public List<ResponseDataMedicalDTO> getResponseData(PolicyDataCriteria policyDto) {

		try {

			List<ResponseDataMedicalDTO> responseDataList = new ArrayList<ResponseDataMedicalDTO>();

			// Get approval status based on proposal id
			boolean isApprove = getApprove(policyDto);

			ResponseDataMedicalDTO responseDataDTO = ResponseDataMedicalDTO.builder()
					.proposalNo(policyDto.getProposalNo()).isApprove(isApprove)
					.policyData(isApprove ? getPolicyData(policyDto) : null).customer(getCustomerData(policyDto))
					.agent(getAgentData(policyDto))
					.insuredPersonDataList(isApprove ? getInsuredPersonData(policyDto) : null)
					.beneficiaryDataList(isApprove ? getBeneficiaryData(policyDto) : null)
					.billCollectionDataList(isApprove ? getBillCollectionData(policyDto) : null).build();
			responseDataList.add(responseDataDTO);

			return responseDataList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting medical policy
	private PolicyData getPolicyData(PolicyDataCriteria policyDto) {

		try {
			// Get policy list
			MedicalPolicy policy = retrieveMedicalPolicyList(policyDto).isEmpty() ? null
					: retrieveMedicalPolicyList(policyDto).get(0);

			// Instantiate policyData model
			PolicyData policyData = null;

			if (policy != null) {

				// Get product name from insuredPersonList
				List<MedicalPolicyInsuredPerson> insuredPersonList = retrievePolicyInsuredPersonList(policyDto);
				String productName = insuredPersonList.get(0).getProduct() == null ? null
						: insuredPersonList.get(0).getProduct().getProductContent().getName();

				policyData = PolicyData.builder().policyStartDate(policy.getActivedPolicyStartDate())
						.policyEndDate(policy.getActivedPolicyEndDate()).periodMonth(policy.getPeriodMonth())
						.commenmanceDate(policy.getCommenmanceDate()).policyNo(policy.getPolicyNo())
						.saleChannelType(policy.getSaleChannelType())
						.paymentType(policy.getPaymentType() == null ? null : policy.getPaymentType().getDescription())
						.salesPoints(policy.getSalesPoints() == null ? null : policy.getSalesPoints().getName())
						.coverageDate(policy.getCoverageDate()).productName(productName).build();

			}
			return policyData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting customer
	private CustomerData getCustomerData(PolicyDataCriteria policyDto) {

		try {

			// Get policy list
			medicalPolicyList = retrieveMedicalPolicyList(policyDto);

			// Get proposal list if policy is empty
			Optional<MedicalProposal> medicalProposalListOpt = medicalProposalRepo
					.findById(getProposalIdFromRepo(policyDto.getProposalNo()));
			List<MedicalProposal> medicalProposalList = medicalPolicyList.isEmpty()
					? Collections.singletonList(medicalProposalListOpt.get())
					: Collections.emptyList();

			Customer customer = medicalPolicyList.isEmpty() ? medicalProposalList.get(0).getCustomer()
					: medicalPolicyList.get(0).getCustomer();

			// Get name obj
			Name nameObj = customer == null ? null : customer.getName();

			// Get customer name
			NameDto customerName = nameObj == null ? null
					: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
							.lastName(nameObj.getLastName()).build();

			CustomerData customerData = customer == null ? null
					: CustomerData.builder().name(customerName).idNo(customer.getFullIdNo())
							.dateOfBirth(customer.getDateOfBirth())
							.address(customer.getResidentAddress() == null ? null
									: customer.getResidentAddress().getTownship().getFullTownShip())
							.phoneNo(customer.getPhone()).fatherName(customer.getFatherName())
							.gender(customer.getGender())
							.occupation(customer.getOccupation() == null ? null : customer.getOccupation().getName())
							.build();

			return customerData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting agent
	private AgentData getAgentData(PolicyDataCriteria policyDto) {

		try {

			// Get policy list
			medicalPolicyList = retrieveMedicalPolicyList(policyDto);

			// Get proposal list if policy is empty
			Optional<MedicalProposal> medicalProposalListOpt = medicalProposalRepo
					.findById(getProposalIdFromRepo(policyDto.getProposalNo()));
			List<MedicalProposal> medicalProposalList = medicalPolicyList.isEmpty()
					? Collections.singletonList(medicalProposalListOpt.get())
					: Collections.emptyList();

			Agent agent = medicalPolicyList.isEmpty() ? medicalProposalList.get(0).getAgent()
					: medicalPolicyList.get(0).getAgent();

			// Get name obj
			Name nameObj = agent == null ? null : agent.getName();

			// Get customer name
			NameDto agentName = nameObj == null ? null
					: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
							.lastName(nameObj.getLastName()).build();

			AgentData agentData = agent == null ? null
					: AgentData.builder().name(agentName).codeNo(agent.getCodeNo()).fatherName(agent.getFatherName())
							.gender(agent.getGender()).idNo(agent.getFullIdNo()).email(agent.getEmail()).build();

			return agentData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting policy insured person data
	private List<InsuredPersonMedicalData> getInsuredPersonData(PolicyDataCriteria policyDto) {

		try {

			List<InsuredPersonMedicalData> insuredPersonDataList = new ArrayList<InsuredPersonMedicalData>();

			// Get insured person list
			List<MedicalPolicyInsuredPerson> policyInsuredPersonList = retrievePolicyInsuredPersonList(policyDto);

			if (!policyInsuredPersonList.isEmpty()) {

				// Get name obj
				String insuredPersonName = policyInsuredPersonList.get(0).getFullName();

				// Get insured person name
//				NameDto insuredPersonName = nameObj == null ? null
//						: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
//								.lastName(nameObj.getLastName()).build();

				policyInsuredPersonList.forEach(person -> {
					InsuredPersonMedicalData insuredPersonData = InsuredPersonMedicalData.builder()
							.proposedSumInsured(person.getSumInsured()).proposedPremium(person.getPremium())
							.idNo(person.getId()).fatherName(person.getFatherName())
							.dateOfBirth(person.getDateOfBirth()).address(person.getFullAddress())
							.name(insuredPersonName).build();
					insuredPersonDataList.add(insuredPersonData);
				});

			}
			return policyInsuredPersonList.isEmpty() ? null : insuredPersonDataList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting policy insured person beneficiary data
	private List<BeneficiaryMedicalData> getBeneficiaryData(PolicyDataCriteria policyDto) {

		try {

			List<BeneficiaryMedicalData> beneficiaryDataList = new ArrayList<BeneficiaryMedicalData>();

			// Get beneficiary list
			List<MedicalPolicyInsuredPersonBeneficiaries> beneficiaryList = retrievePolicyInsuredPersonList(policyDto)
					.isEmpty() ? Collections.emptyList()
							: retrievePolicyInsuredPersonList(policyDto).get(0)
									.getPolicyInsuredPersonBeneficiariesList();

			if (!beneficiaryList.isEmpty()) {

				// Get name obj
				Name nameObj = beneficiaryList.get(0).getName();

				// Get insured beneficiary person name
				NameDto beneName = nameObj == null ? null
						: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
								.lastName(nameObj.getLastName()).build();

				beneficiaryList.forEach(beneficiary -> {
					BeneficiaryMedicalData beneficiaryData = BeneficiaryMedicalData.builder().name(beneName)
							.dateOfBirth(beneficiary.getDateOfBirth()).idNo(beneficiary.getIdNo())
							.relationship(beneficiary.getRelationship().getName())
							.percentage(beneficiary.getPercentage())
							.address(beneficiary.getResidentAddress() == null ? null
									: beneficiary.getResidentAddress().getTownship().getFullTownShip())
							.build();
					beneficiaryDataList.add(beneficiaryData);
				});

			}
			return beneficiaryList.isEmpty() ? null : beneficiaryDataList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting bill collection data
	private List<BillCollectionData> getBillCollectionData(PolicyDataCriteria policyDto) {

		try {

			List<BillCollectionData> billCollectionDataList = new ArrayList<BillCollectionData>();

			// Get policy list
			medicalPolicyList = retrieveMedicalPolicyList(policyDto);

			if (!medicalPolicyList.isEmpty()) {
				
				List<String> remainingDateList = getRemainingDates(policyDto);

				medicalPolicyList.forEach(policy -> {
					BillCollectionData billCollectionData = BillCollectionData.builder()
							.lastPaymentTerm(policy.getLastPaymentTerm()).coverageDate(policy.getCoverageDate())
							.totalPaymentTerm(policy.getPeriodMonth()).remainingDateList(remainingDateList)
							.agentCommission(policy.getAgentCommission())
							.termPremium(policy.getPolicyInsuredPersonList().get(0).getBasicTermPremium()).build();

					billCollectionDataList.add(billCollectionData);
				});

			}
			return medicalPolicyList.isEmpty() ? null : billCollectionDataList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting remaining date of policy payment
	private List<String> getRemainingDates(PolicyDataCriteria policyDto) {

		// Get policy list
		medicalPolicyList = retrieveMedicalPolicyList(policyDto);

		int month = medicalPolicyList.get(0).getPaymentType().getMonth();
		Date coverageDate = (medicalPolicyList.get(0).getCoverageDate());
		Date policyEndDate = (medicalPolicyList.get(0).getActivedPolicyEndDate());

		LocalDate start = new java.sql.Date(coverageDate.getTime()).toLocalDate();
		LocalDate end = new java.sql.Date(policyEndDate.getTime()).toLocalDate();

		List<String> remainingDates = new ArrayList<String>();

		while (!start.isEqual(end)) {
			start = start.plusMonths(month);
			remainingDates.add(start.toString());
		}

		return remainingDates;
	}

	// Getting approve status based on policy number from dto
	private boolean getApprove(PolicyDataCriteria policyDto) {

		String proposalId = getProposalIdFromRepo(policyDto.getProposalNo());
		return getApprovalStatusFromRepo(proposalId);
	}

	private String getProposalIdFromRepo(String proposalNumber) {

		try {
			// get proposal id based on proposal number
			String medicalProposalId = medicalProposalRepo.getProposalId(proposalNumber);
			return medicalProposalId;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private boolean getApprovalStatusFromRepo(String proposalId) {

		try {
			// Get approve status
			List<Boolean> isApprove = medicalProposalRepo.getApprovalStatus(proposalId);
			return isApprove.isEmpty() ? false : isApprove.get(0);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting medical policy list
	private List<MedicalPolicy> retrieveMedicalPolicyList(PolicyDataCriteria policyDto) {

		try {

			medicalPolicyList = medicalPolicyRepo
					.getPolicyList(getProposalIdFromRepo(policyDto.getProposalNo()));

			return medicalPolicyList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting policy insured person list
	private List<MedicalPolicyInsuredPerson> retrievePolicyInsuredPersonList(PolicyDataCriteria policyDto) {

		try {

			List<MedicalPolicyInsuredPerson> insuredPersonList = retrieveMedicalPolicyList(policyDto).isEmpty()
					? Collections.emptyList()
					: retrieveMedicalPolicyList(policyDto).get(0).getPolicyInsuredPersonList();

			return insuredPersonList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

}
