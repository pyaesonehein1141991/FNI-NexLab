package org.tat.fni.api.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name = TableName.MEDICALCLAIM_PROPOSAL)
@TableGenerator(name = "MEDICALCLAIMPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALCLAIMPROPOSAL_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalClaimProposal implements Serializable {

	private static final long serialVersionUID = -8792731555519276762L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALCLAIMPROPOSAL_GEN")
	private String id;

	private String claimReportNo;

	@Column(name = "CLAIMREQUESTID")
	private String claimRequestId;

	@Column(name = "SUBMITTEDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@Column(name = "HOSPITALIZED")
	private boolean hospitalized;

	@Column(name = "DEATH")
	private boolean death;

	@Column(name = "OPERATION")
	private boolean operation;

	@Column(name = "MEDICATION")
	private boolean medication;

	private String policyNo;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "POLICYINSUREDPERSON", referencedColumnName = "ID")
//	private MedicalPolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICALCLAIM_SURVEY_ID", referencedColumnName = "ID")
	private MedicalClaimSurvey medicalClaimSurvey;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medicalClaimProposal", orphanRemoval = true)
	private List<MedicalClaim> medicalClaimList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medicalClaimProposal", orphanRemoval = true)
	private List<MedicalClaimAttachment> medicalClaimAttachmentList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "medicalClaimProposal", orphanRemoval = true)
	private List<MedicalClaimBeneficiary> medicalClaimBeneficiariesList;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public MedicalClaimProposal() {

	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public List<MedicalClaimAttachment> getMedicalClaimAttachmentList() {
		if (medicalClaimAttachmentList == null) {
			medicalClaimAttachmentList = new ArrayList<MedicalClaimAttachment>();
		}
		return medicalClaimAttachmentList;
	}

	public void setMedicalClaimAttachmentList(List<MedicalClaimAttachment> medicalClaimAttachmentList) {
		this.medicalClaimAttachmentList = medicalClaimAttachmentList;
	}

	public List<MedicalClaimBeneficiary> getMedicalClaimBeneficiariesList() {
		return medicalClaimBeneficiariesList;
	}

	public void setMedicalClaimBeneficiariesList(List<MedicalClaimBeneficiary> medicalClaimBeneficiariesList) {
		this.medicalClaimBeneficiariesList = medicalClaimBeneficiariesList;
	}

	public void addClaimBeneficiary(MedicalClaimBeneficiary medicalClaimBeneficiary) {
		if (this.medicalClaimBeneficiariesList == null) {
			this.medicalClaimBeneficiariesList = new ArrayList<MedicalClaimBeneficiary>();
		}
		medicalClaimBeneficiary.setMedicalClaimProposal(this);
		this.medicalClaimBeneficiariesList.add(medicalClaimBeneficiary);
	}

	public void addClaimAttachment(MedicalClaimAttachment claimAttachment) {
		if (this.medicalClaimAttachmentList == null) {
			this.medicalClaimAttachmentList = new ArrayList<MedicalClaimAttachment>();
		}
		claimAttachment.setMedicalClaimProposal(this);
		this.medicalClaimAttachmentList.add(claimAttachment);
	}

	public List<MedicalClaim> getMedicalClaimList() {
		return medicalClaimList;
	}

	public void setMedicalClaimList(List<MedicalClaim> medicalClaimList) {
		this.medicalClaimList = medicalClaimList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isHospitalized() {
		return hospitalized;
	}

	public void setHospitalized(boolean hospitalized) {
		this.hospitalized = hospitalized;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	public boolean isMedication() {
		return medication;
	}

	public void setMedication(boolean medication) {
		this.medication = medication;
	}

	public void addMedicalClaim(MedicalClaim medicalClaim) {
		if (medicalClaimList == null) {
			medicalClaimList = new ArrayList<MedicalClaim>();
		}
		medicalClaim.setMedicalClaimProposal(this);
		medicalClaimList.add(medicalClaim);
	}

	public MedicalClaimSurvey getMedicalClaimSurvey() {
		return medicalClaimSurvey;
	}

	public void setMedicalClaimSurvey(MedicalClaimSurvey medicalClaimSurvey) {
		this.medicalClaimSurvey = medicalClaimSurvey;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getClaimReportNo() {
		return claimReportNo;
	}

	public void setClaimReportNo(String claimReportNo) {
		this.claimReportNo = claimReportNo;
	}

//	public MedicalPolicyInsuredPerson getPolicyInsuredPerson() {
//		return policyInsuredPerson;
//	}
//
//	public void setPolicyInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
//		this.policyInsuredPerson = policyInsuredPerson;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimReportNo == null) ? 0 : claimReportNo.hashCode());
		result = prime * result + ((claimRequestId == null) ? 0 : claimRequestId.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + (hospitalized ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalClaimSurvey == null) ? 0 : medicalClaimSurvey.hashCode());
//		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + (medication ? 1231 : 1237);
		result = prime * result + (operation ? 1231 : 1237);
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalClaimProposal other = (MedicalClaimProposal) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimReportNo == null) {
			if (other.claimReportNo != null)
				return false;
		} else if (!claimReportNo.equals(other.claimReportNo))
			return false;
//		if (policyInsuredPerson == null) {
//			if (other.policyInsuredPerson != null)
//				return false;
//		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
//			return false;
		if (claimRequestId == null) {
			if (other.claimRequestId != null)
				return false;
		} else if (!claimRequestId.equals(other.claimRequestId))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (death != other.death)
			return false;
		if (hospitalized != other.hospitalized)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalClaimSurvey == null) {
			if (other.medicalClaimSurvey != null)
				return false;
		} else if (!medicalClaimSurvey.equals(other.medicalClaimSurvey))
			return false;

		if (medication != other.medication)
			return false;
		if (operation != other.operation)
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
