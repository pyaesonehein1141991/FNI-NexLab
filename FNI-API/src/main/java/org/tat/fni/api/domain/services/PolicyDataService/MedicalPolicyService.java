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
import org.tat.fni.api.domain.MedicalPolicy;
import org.tat.fni.api.domain.MedicalPolicyInsuredPerson;
import org.tat.fni.api.domain.repository.MedicalPolicyRepository;
import org.tat.fni.api.domain.repository.MedicalProposalRepository;
import org.tat.fni.api.domain.services.Interfaces.IPolicyDataService;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.BillCollectionData;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyData;
import org.tat.fni.api.dto.retrieveDTO.policyData.RemainingDate;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service
public class MedicalPolicyService implements IPolicyDataService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MedicalProposalRepository medicalProposalRepo;

	@Autowired
	private MedicalPolicyRepository medicalPolicyRepo;

	// == declare class variable ==
	private List<MedicalPolicy> medicalPolicyList;
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

	// Getting medical policy
	public PolicyData getPolicyData(String proposalNo) {

		try {
			// Get policy list
			MedicalPolicy policy = retrieveMedicalPolicyList(proposalNo).isEmpty() ? null : retrieveMedicalPolicyList(proposalNo).get(0);

			// Instantiate policyData model
			PolicyData policyData = null;

			if (policy != null) {

				// Get product name from insuredPersonList
				List<MedicalPolicyInsuredPerson> insuredPersonList = retrievePolicyInsuredPersonList(proposalNo);
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
			medicalPolicyList = retrieveMedicalPolicyList(proposalNo);

			if (!medicalPolicyList.isEmpty()) {

				medicalPolicyList.forEach(policy -> {
					billCollectionData = BillCollectionData.builder().lastPaymentTerm(policy.getLastPaymentTerm()).coverDate(policy.getCoverageDate())
							.totalPaymentTerm(policy.getPeriodMonth()).remainingDateList(getRemainingDates(policy)).build();
				});

			}
			return medicalPolicyList.isEmpty() ? null : billCollectionData;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}

	}

	// Getting remaining date of policy payment
	private List<RemainingDate> getRemainingDates(MedicalPolicy lifePolicy) {

		int month = medicalPolicyList.get(0).getPaymentType().getMonth();
		Date policyStartDate = (medicalPolicyList.get(0).getActivedPolicyStartDate());
		Date policyEndDate = (medicalPolicyList.get(0).getActivedPolicyEndDate());
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
			String medicalProposalId = medicalProposalRepo.getProposalId(proposalNo);
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
	private List<MedicalPolicy> retrieveMedicalPolicyList(String proposalNo) {

		try {

			medicalPolicyList = medicalPolicyRepo.getPolicyList(getProposalIdFromRepo(proposalNo));

			return medicalPolicyList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	// Getting policy insured person list
	private List<MedicalPolicyInsuredPerson> retrievePolicyInsuredPersonList(String proposalNo) {

		try {

			List<MedicalPolicyInsuredPerson> insuredPersonList = retrieveMedicalPolicyList(proposalNo).isEmpty() ? Collections.emptyList()
					: retrieveMedicalPolicyList(proposalNo).get(0).getPolicyInsuredPersonList();

			return insuredPersonList;

		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

}
