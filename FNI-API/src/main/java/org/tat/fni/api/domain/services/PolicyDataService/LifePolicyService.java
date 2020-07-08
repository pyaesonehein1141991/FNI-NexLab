package org.tat.fni.api.domain.services.PolicyDataService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifepolicy.PolicyInsuredPerson;
import org.tat.fni.api.domain.repository.LifePolicyRepository;
import org.tat.fni.api.domain.repository.LifeProposalRepository;
import org.tat.fni.api.domain.services.Interfaces.IPolicyDataService;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
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

	public ResponseDataDTO getResponseData(String proposalNo) {

		try {

			// Get approval status based on proposal id
			boolean isApprove = getApprove(proposalNo);

			ResponseDataDTO responseDataDTO = ResponseDataDTO.builder().proposalNo(proposalNo)
					.isApprove(isApprove).policyData(isApprove ? getPolicyData(proposalNo) : null)
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
