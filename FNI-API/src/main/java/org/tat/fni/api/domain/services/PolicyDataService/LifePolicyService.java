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
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifepolicy.PolicyInsuredPerson;
import org.tat.fni.api.domain.lifepolicy.PolicyInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.repository.LifePolicyRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.Interfaces.IPolicyDataService;
import org.tat.fni.api.domain.services.Interfaces.IPolicyService;
import org.tat.fni.api.dto.policyDataDTO.PolicyDataCriteria;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataLifeDTO;
import org.tat.fni.api.dto.retrieveDTO.NameDto;
import org.tat.fni.api.dto.retrieveDTO.policyData.AgentData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BeneficiaryLifeData;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.CustomerData;
import org.tat.fni.api.dto.retrieveDTO.policyData.InsuredPersonLifeData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class LifePolicyService implements IPolicyDataService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifeProposalRepository lifeProposalRepo;
	
	@Autowired
	private LifePolicyRepository lifePolicyRepo;

	// == declare class variable ==
	private List<LifePolicy> lifePolicyList;

	public ResponseDataLifeDTO getResponseData(String proposalNo) {

		try {

			// Get approval status based on proposal id
			boolean isApprove = getApprove(proposalNo);

			ResponseDataLifeDTO responseDataDTO = ResponseDataLifeDTO.builder().proposalNo(proposalNo)
					.isApprove(isApprove).policyData(isApprove ? getPolicyData(proposalNo) : null)
					.customer(getCustomerData(proposalNo)).agent(getAgentData(proposalNo))
					.insuredPersonDataList(isApprove ? getInsuredPersonData(proposalNo) : null)
					.beneficiaryDataList(isApprove ? getBeneficiaryData(proposalNo) : null)
					.billCollectionDataList(isApprove ? getBillCollectionData(proposalNo) : null).build();

			return responseDataDTO;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting life policy
	public PolicyData getPolicyData(String proposalNo) {

		try {
			// Get policy list
			LifePolicy policy = retrieveLifePolicyList(proposalNo).isEmpty() ? null
					: retrieveLifePolicyList(proposalNo).get(0);

			// Instantiate policyData model
			PolicyData policyData = null;

			if (policy != null) {

				// Get product name from insuredPersonList
				List<PolicyInsuredPerson> insuredPersonList = retrievePolicyInsuredPersonList(proposalNo);
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
	public CustomerData getCustomerData(String proposalNo) {

		try {

			// Get policy list
			lifePolicyList = retrieveLifePolicyList(proposalNo);

			// Get proposal list if policy is empty
			Optional<LifeProposal> lifeProposalListOpt = lifeProposalRepo
					.findById(getProposalIdFromRepo(proposalNo));
			List<LifeProposal> lifeProposalList = lifePolicyList.isEmpty()
					? Collections.singletonList(lifeProposalListOpt.get())
					: Collections.emptyList();

			Customer customer = lifePolicyList.isEmpty() ? lifeProposalList.get(0).getCustomer()
					: lifePolicyList.get(0).getCustomer();

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
	public AgentData getAgentData(String proposalNo) {

		try {

			// Get policy list
			lifePolicyList = retrieveLifePolicyList(proposalNo);

			// Get proposal list if policy is empty
			Optional<LifeProposal> lifeProposalListOpt = lifeProposalRepo
					.findById(getProposalIdFromRepo(proposalNo));
			List<LifeProposal> lifeProposalList = lifePolicyList.isEmpty()
					? Collections.singletonList(lifeProposalListOpt.get())
					: Collections.emptyList();

			Agent agent = lifePolicyList.isEmpty() ? lifeProposalList.get(0).getAgent()
					: lifePolicyList.get(0).getAgent();

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
	public List<InsuredPersonLifeData> getInsuredPersonData(String proposalNo) {

		try {

			List<InsuredPersonLifeData> insuredPersonDataList = new ArrayList<InsuredPersonLifeData>();

			// Get insured person list
			List<PolicyInsuredPerson> policyInsuredPersonList = retrievePolicyInsuredPersonList(proposalNo);

			if (!policyInsuredPersonList.isEmpty()) {

				// Get name obj
				Name nameObj = policyInsuredPersonList.get(0).getName();

				// Get insured person name
				NameDto insuredPersonName = nameObj == null ? null
						: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
								.lastName(nameObj.getLastName()).build();

				policyInsuredPersonList.forEach(person -> {
					InsuredPersonLifeData insuredPersonData = InsuredPersonLifeData.builder()
							.proposedSumInsured(person.getSumInsured()).proposedPremium(person.getPremium())
							.idNo(person.getIdNo()).fatherName(person.getFatherName())
							.dateOfBirth(person.getDateOfBirth()).gender(person.getGender())
							.address(person.getResidentAddress() == null ? null
									: person.getResidentAddress().getTownship().getFullTownShip())
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
	public List<BeneficiaryLifeData> getBeneficiaryData(String proposalNo) {

		try {

			List<BeneficiaryLifeData> beneficiaryDataList = new ArrayList<BeneficiaryLifeData>();

			// Get beneficiary list
			List<PolicyInsuredPersonBeneficiaries> beneficiaryList = retrievePolicyInsuredPersonList(proposalNo)
					.isEmpty() ? Collections.emptyList()
							: retrievePolicyInsuredPersonList(proposalNo).get(0)
									.getPolicyInsuredPersonBeneficiariesList();

			if (!beneficiaryList.isEmpty()) {

				// Get name obj
				Name nameObj = beneficiaryList.get(0).getName();

				// Get insured beneficiary person name
				NameDto beneName = nameObj == null ? null
						: NameDto.builder().firstName(nameObj.getFirstName()).middleName(nameObj.getMiddleName())
								.lastName(nameObj.getLastName()).build();

				beneficiaryList.forEach(beneficiary -> {
					BeneficiaryLifeData beneficiaryData = BeneficiaryLifeData.builder().name(beneName)
							.dateOfBirth(beneficiary.getDateOfBirth()).phoneNo(beneficiary.getPhone())
							.idNo(beneficiary.getIdNo()).relationship(beneficiary.getRelationshipName())
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
	public List<BillCollectionData> getBillCollectionData(String proposalNo) {

		try {

			List<BillCollectionData> billCollectionDataList = new ArrayList<BillCollectionData>();

			// Get policy list
			lifePolicyList = retrieveLifePolicyList(proposalNo);

			if (!lifePolicyList.isEmpty()) {

				List<String> remainingDateList = getRemainingDates(proposalNo);

				lifePolicyList.forEach(policy -> {
					BillCollectionData billCollectionData = BillCollectionData.builder()
							.lastPaymentTerm(policy.getLastPaymentTerm()).coverageDate(policy.getCoverageDate())
							.totalPaymentTerm(policy.getTotalPaymentTimes()).remainingDateList(remainingDateList)
							.agentCommission(policy.getAgentCommission())
							.termPremium(policy.getPolicyInsuredPersonList().get(0).getBasicTermPremium()).build();

					billCollectionDataList.add(billCollectionData);
				});

			}
			return lifePolicyList.isEmpty() ? null : billCollectionDataList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}
	
	// Getting remaining date of policy payment
		private List<String> getRemainingDates(String proposalNo) {

			// Get policy list
			lifePolicyList = retrieveLifePolicyList(proposalNo);

			int month = lifePolicyList.get(0).getPaymentType().getMonth();
			Date coverageDate = (lifePolicyList.get(0).getCoverageDate());
			Date policyEndDate = (lifePolicyList.get(0).getActivedPolicyEndDate());

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
		private boolean getApprove(String proposalNo) {

			String proposalId = getProposalIdFromRepo(proposalNo);
			return getApprovalStatusFromRepo(proposalId);
		}

		public String getProposalIdFromRepo(String proposalNo) {

			try {
				// get proposal id based on proposal number
				String lifeProposalId = lifeProposalRepo.getProposalId(proposalNo);
				return lifeProposalId;
			} catch (DAOException e) {
				throw new SystemException(e.getErrorCode(), e.getMessage());
			}
		}

		private boolean getApprovalStatusFromRepo(String proposalId) {

			try {
				// Get approve status
				List<Boolean> isApprove = lifeProposalRepo.getApprovalStatus(proposalId);
				return isApprove.isEmpty() ? false : isApprove.get(0);
			} catch (DAOException e) {
				throw new SystemException(e.getErrorCode(), e.getMessage());
			}
		}

		// Getting life policy list
		private List<LifePolicy> retrieveLifePolicyList(String proposalNo) {

			try {

				lifePolicyList = lifePolicyRepo.getPolicyList(getProposalIdFromRepo(proposalNo));

				return lifePolicyList;

			} catch (DAOException e) {
				throw new SystemException(e.getErrorCode(), e.getMessage());
			}
		}

		// Getting policy insured person list
		private List<PolicyInsuredPerson> retrievePolicyInsuredPersonList(String proposalNo) {

			try {

				List<PolicyInsuredPerson> insuredPersonList = retrieveLifePolicyList(proposalNo).isEmpty()
						? Collections.emptyList()
						: retrieveLifePolicyList(proposalNo).get(0).getPolicyInsuredPersonList();

				return insuredPersonList;

			} catch (DAOException e) {
				throw new SystemException(e.getErrorCode(), e.getMessage());
			}
		}

}
