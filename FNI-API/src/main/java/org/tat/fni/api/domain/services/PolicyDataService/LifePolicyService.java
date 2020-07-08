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
import org.tat.fni.api.dto.retrieveDTO.policyData.RemainingDate;
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
	private BillCollectionData billCollectionData;

	public ResponseDataDTO getResponseData(String proposalNo) {

		try {

			// Get approval status based on proposal id
			boolean isApprove = getApprove(proposalNo);

			ResponseDataDTO responseDataDTO = ResponseDataDTO.builder().proposalNo(proposalNo).isApprove(isApprove).policyData(isApprove ? getPolicyData(proposalNo) : null)
					.billCollectionData(isApprove ? getBillCollectionData(proposalNo) : null).build();

			return responseDataDTO;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting life policy
	public PolicyData getPolicyData(String proposalNo) {

		try {
			// Get policy list
			LifePolicy policy = retrieveLifePolicyList(proposalNo).isEmpty() ? null : retrieveLifePolicyList(proposalNo).get(0);

			// Instantiate policyData model
			PolicyData policyData = null;

			if (policy != null) {

				// Get product name from insuredPersonList
				List<PolicyInsuredPerson> insuredPersonList = retrievePolicyInsuredPersonList(proposalNo);
				String productName = insuredPersonList.get(0).getProduct() == null ? null : insuredPersonList.get(0).getProduct().getProductContent().getName();

				policyData = PolicyData.builder().policyStartDate(policy.getActivedPolicyStartDate()).policyEndDate(policy.getActivedPolicyEndDate())
						.periodMonth(policy.getPeriodMonth()).commenmanceDate(policy.getCommenmanceDate()).policyNo(policy.getPolicyNo())
						.saleChannelType(policy.getSaleChannelType()).paymentType(policy.getPaymentType() == null ? null : policy.getPaymentType().getDescription())
						.salesPoints(policy.getSalesPoints() == null ? null : policy.getSalesPoints().getName()).coverageDate(policy.getCoverageDate()).productName(productName)
						.build();

			}
			return policyData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting bill collection data
	public BillCollectionData getBillCollectionData(String proposalNo) {

		try {

			// Get policy list
			lifePolicyList = retrieveLifePolicyList(proposalNo);

			if (!lifePolicyList.isEmpty()) {

				lifePolicyList.forEach(policy -> {
					billCollectionData = BillCollectionData.builder().lastPaymentTerm(policy.getLastPaymentTerm()).coverDate(policy.getCoverageDate())
							.totalPaymentTerm(policy.getTotalPaymentTimes()).remainingDateList(getRemainingDates(policy)).build();

				});

			}
			return lifePolicyList.isEmpty() ? null : billCollectionData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting remaining date of policy payment
	private List<RemainingDate> getRemainingDates(LifePolicy lifePolicy) {

		int month = lifePolicy.getPaymentType().getMonth();
		Date policyStartDate = (lifePolicy.getActivedPolicyStartDate());
		Date policyEndDate = (lifePolicy.getActivedPolicyEndDate());
		int paidTimes = lifePolicy.getLastPaymentTerm();
		
		LocalDate start = new java.sql.Date(policyStartDate.getTime()).toLocalDate();
		LocalDate end = new java.sql.Date(policyEndDate.getTime()).toLocalDate();

		List<RemainingDate> remainingDates = new ArrayList<RemainingDate>();
		int i = 1;
		
		while (!start.isAfter(end)) {
			RemainingDate remainingDate = RemainingDate.builder().termPremium(lifePolicy.getPolicyInsuredPersonList().get(0).getBasicTermPremium())
					.agentCommission(lifePolicy.getAgentCommission()).date(start.toString())
					.paid(!(i > paidTimes)).build();
			remainingDates.add(remainingDate);
			if (month == 0) break;
			start = start.plusMonths(month);
			i++;
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

			List<PolicyInsuredPerson> insuredPersonList = retrieveLifePolicyList(proposalNo).isEmpty() ? Collections.emptyList()
					: retrieveLifePolicyList(proposalNo).get(0).getPolicyInsuredPersonList();

			return insuredPersonList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

}
