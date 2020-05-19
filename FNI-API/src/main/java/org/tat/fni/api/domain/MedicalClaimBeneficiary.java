package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.ClaimStatus;


@Entity
@Table(name = TableName.MEDICALCLAIM_BENEFICIARY)
@TableGenerator(name = "MEDICALCLAIMBENEFICIARY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALCLAIMBENEFICIARY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MedicalClaimBeneficiary.findAll", query = "SELECT c FROM MedicalClaimBeneficiary c "),
		@NamedQuery(name = "MedicalClaimBeneficiary.findById", query = "SELECT c FROM MedicalClaimBeneficiary c WHERE c.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MedicalClaimBeneficiary implements Serializable {

	private static final long serialVersionUID = -5709073898104132452L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALCLAIMBENEFICIARY_GEN")
	private String id;

	private String beneficiaryNo;

	@Column(name = "NAME")
	private String name;

	private String nrc;

	private boolean death;

	private float percentage;

	@Column(name = "CLAIMAMOUNT")
	private double claimAmount;

	@Column(name = "HOSP_CLAIMAMOUNT")
	private double hospClaimAmount;

	@Column(name = "OPER_CLAIMAMOUNT")
	private double operClaimAmount;

	@Column(name = "MEDI_CLAIMAMOUNT")
	private double mediClaimAmount;

	@Column(name = "DEATH_CLAIMAMOUNT")
	private double deathClaimAmount;

	@Column(name = "DEFICITPREMIUM")
	private double deficitPremium;

	@Column(name = "BENEFICIARYROLE")
	private String beneficiaryRole;

	@Column(name = "NOHOSPDAYS")
	private int noOfHospDays;

	@Column(name = "UNIT")
	private int unit;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CLAIMSTATUS")
	private ClaimStatus claimStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTID", referencedColumnName = "ID")
	private Payment payment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICALCLAIMPROPOSALID", referencedColumnName = "ID")
	private MedicalClaimProposal medicalClaimProposal;

//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "MEDICALPOLICYINSUREDPERSON_ID", referencedColumnName = "ID")
//	private MedicalPolicyInsuredPerson medicalPolicyInsuredPerson;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "MEDICALPOLICY_BENEFICIARY_ID", referencedColumnName = "ID")
//	private MedicalPolicyInsuredPersonBeneficiaries medicalPolicyInsuredPersonBeneficiaries;
//
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "MEDICALPOLICYGUARDIAN_ID", referencedColumnName = "ID")
//	private MedicalPolicyInsuredPersonGuardian medicalPolicyInsuredPersonGuardian;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIP_ID", referencedColumnName = "ID")
	private RelationShip relationShip;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public MedicalClaimBeneficiary() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public MedicalClaimProposal getMedicalClaimProposal() {
		return medicalClaimProposal;
	}

	public void setMedicalClaimProposal(MedicalClaimProposal medicalClaimProposal) {
		this.medicalClaimProposal = medicalClaimProposal;
	}

//	public MedicalPolicyInsuredPerson getMedicalPolicyInsuredPerson() {
//		return medicalPolicyInsuredPerson;
//	}
//
//	public void setMedicalPolicyInsuredPerson(MedicalPolicyInsuredPerson medicalPolicyInsuredPerson) {
//		this.medicalPolicyInsuredPerson = medicalPolicyInsuredPerson;
//	}
//
//	public MedicalPolicyInsuredPersonGuardian getMedicalPolicyInsuredPersonGuardian() {
//		return medicalPolicyInsuredPersonGuardian;
//	}
//
//	public MedicalPolicyInsuredPersonBeneficiaries getMedicalPolicyInsuredPersonBeneficiaries() {
//		return medicalPolicyInsuredPersonBeneficiaries;
//	}
//
//	public void setMedicalPolicyInsuredPersonBeneficiaries(MedicalPolicyInsuredPersonBeneficiaries medicalPolicyInsuredPersonBeneficiaries) {
//		this.medicalPolicyInsuredPersonBeneficiaries = medicalPolicyInsuredPersonBeneficiaries;
//	}
//
//	public void setMedicalPolicyInsuredPersonGuardian(MedicalPolicyInsuredPersonGuardian medicalPolicyInsuredPersonGuardian) {
//		this.medicalPolicyInsuredPersonGuardian = medicalPolicyInsuredPersonGuardian;
//	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getBeneficiaryRole() {
		return beneficiaryRole;
	}

	public void setBeneficiaryRole(String beneficiaryRole) {
		this.beneficiaryRole = beneficiaryRole;
	}

	public int getNoOfHospDays() {
		return noOfHospDays;
	}

	public void setNoOfHospDays(int noOfHospDays) {
		this.noOfHospDays = noOfHospDays;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getHospClaimAmount() {
		return hospClaimAmount;
	}

	public void setHospClaimAmount(double hospClaimAmount) {
		this.hospClaimAmount = hospClaimAmount;
	}

	public double getOperClaimAmount() {
		return operClaimAmount;
	}

	public void setOperClaimAmount(double operClaimAmount) {
		this.operClaimAmount = operClaimAmount;
	}

	public double getMediClaimAmount() {
		return mediClaimAmount;
	}

	public void setMediClaimAmount(double mediClaimAmount) {
		this.mediClaimAmount = mediClaimAmount;
	}

	public double getDeathClaimAmount() {
		return deathClaimAmount;
	}

	public void setDeathClaimAmount(double deathClaimAmount) {
		this.deathClaimAmount = deathClaimAmount;
	}

	public double getDeficitPremium() {
		return deficitPremium;
	}

	public void setDeficitPremium(double deficitPremium) {
		this.deficitPremium = deficitPremium;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((beneficiaryRole == null) ? 0 : beneficiaryRole.hashCode());
		long temp;
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		temp = Double.doubleToLongBits(deathClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(deficitPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hospClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(mediClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((medicalClaimProposal == null) ? 0 : medicalClaimProposal.hashCode());
//		result = prime * result + ((medicalPolicyInsuredPerson == null) ? 0 : medicalPolicyInsuredPerson.hashCode());
//		result = prime * result + ((medicalPolicyInsuredPersonBeneficiaries == null) ? 0 : medicalPolicyInsuredPersonBeneficiaries.hashCode());
//		result = prime * result + ((medicalPolicyInsuredPersonGuardian == null) ? 0 : medicalPolicyInsuredPersonGuardian.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + noOfHospDays;
		result = prime * result + ((nrc == null) ? 0 : nrc.hashCode());
		temp = Double.doubleToLongBits(operClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((payment == null) ? 0 : payment.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((relationShip == null) ? 0 : relationShip.hashCode());
		result = prime * result + unit;
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
		MedicalClaimBeneficiary other = (MedicalClaimBeneficiary) obj;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (beneficiaryRole == null) {
			if (other.beneficiaryRole != null)
				return false;
		} else if (!beneficiaryRole.equals(other.beneficiaryRole))
			return false;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (death != other.death)
			return false;
		if (Double.doubleToLongBits(deathClaimAmount) != Double.doubleToLongBits(other.deathClaimAmount))
			return false;
		if (Double.doubleToLongBits(deficitPremium) != Double.doubleToLongBits(other.deficitPremium))
			return false;
		if (Double.doubleToLongBits(hospClaimAmount) != Double.doubleToLongBits(other.hospClaimAmount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(mediClaimAmount) != Double.doubleToLongBits(other.mediClaimAmount))
			return false;
		if (medicalClaimProposal == null) {
			if (other.medicalClaimProposal != null)
				return false;
		} else if (!medicalClaimProposal.equals(other.medicalClaimProposal))
			return false;
//		if (medicalPolicyInsuredPerson == null) {
//			if (other.medicalPolicyInsuredPerson != null)
//				return false;
//		} else if (!medicalPolicyInsuredPerson.equals(other.medicalPolicyInsuredPerson))
//			return false;
//		if (medicalPolicyInsuredPersonBeneficiaries == null) {
//			if (other.medicalPolicyInsuredPersonBeneficiaries != null)
//				return false;
//		} else if (!medicalPolicyInsuredPersonBeneficiaries.equals(other.medicalPolicyInsuredPersonBeneficiaries))
//			return false;
//		if (medicalPolicyInsuredPersonGuardian == null) {
//			if (other.medicalPolicyInsuredPersonGuardian != null)
//				return false;
//		} else if (!medicalPolicyInsuredPersonGuardian.equals(other.medicalPolicyInsuredPersonGuardian))
//			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (noOfHospDays != other.noOfHospDays)
			return false;
		if (nrc == null) {
			if (other.nrc != null)
				return false;
		} else if (!nrc.equals(other.nrc))
			return false;
		if (Double.doubleToLongBits(operClaimAmount) != Double.doubleToLongBits(other.operClaimAmount))
			return false;
		if (payment == null) {
			if (other.payment != null)
				return false;
		} else if (!payment.equals(other.payment))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (relationShip == null) {
			if (other.relationShip != null)
				return false;
		} else if (!relationShip.equals(other.relationShip))
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
