package org.tat.fni.api.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import org.tat.fni.api.common.AddOn;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.InsuredPersonAddOnDTO;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;

@Entity
@Table(name = TableName.INSUREDPERSONADDON)
@TableGenerator(name = "INSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "INSUREDPERSONADDON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "InsuredPersonAddon.findAll", query = "SELECT v FROM InsuredPersonAddon v "),
		@NamedQuery(name = "InsuredPersonAddon.findById", query = "SELECT v FROM InsuredPersonAddon v WHERE v.id = :id") })
@EntityListeners(IDInterceptor.class)
public class InsuredPersonAddon {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "INSUREDPERSONADDON_GEN")
	private String id;

	private double proposedPremium;
	private double proposedSumInsured;
	private double approvedPremium;
	private double approvedSumInsured;
	private double premiumRate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPROPOSALINSUREDPERSONID", referencedColumnName = "ID")
	private ProposalInsuredPerson proposalInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public InsuredPersonAddon() {
	}

	public InsuredPersonAddon(AddOn addOn, double proposedSumInsured) {
		this.addOn = addOn;
		this.proposedSumInsured = proposedSumInsured;
	}

//	public InsuredPersonAddon(PolicyInsuredPersonAddon policyInsPersonaddOn) {
//		this.addOn = policyInsPersonaddOn.getAddOn();
//		this.proposedPremium = policyInsPersonaddOn.getPremium();
//		this.proposedSumInsured = policyInsPersonaddOn.getSumInsured();
//		this.approvedPremium = policyInsPersonaddOn.getPremium();
//		this.approvedSumInsured = policyInsPersonaddOn.getSumInsured();
//	}

	public InsuredPersonAddon(InsuredPersonAddOnDTO dto) {
		this.addOn = dto.getAddOn();
		this.proposedSumInsured = dto.getAddOnSumInsured();
		this.proposedPremium = dto.getPremium();
		this.approvedPremium = dto.getApprovedPremium();
		this.approvedSumInsured = dto.getApprovedSumInsured();
		if (dto.isExistEntity()) {
			this.id = dto.getTempId();
			this.version = dto.getVersion();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public double getProposedPremium() {
		return proposedPremium;
	}

	public void setProposedPremium(double proposedPremium) {
		this.proposedPremium = proposedPremium;
	}

	public double getProposedSumInsured() {
		return proposedSumInsured;
	}

	public void setProposedSumInsured(double proposedSumInsured) {
		this.proposedSumInsured = proposedSumInsured;
	}

	public double getApprovedPremium() {
		return approvedPremium;
	}

	public void setApprovedPremium(double approvedPremium) {
		this.approvedPremium = approvedPremium;
	}

	public double getApprovedSumInsured() {
		return approvedSumInsured;
	}

	public void setApprovedSumInsured(double approvedSumInsured) {
		this.approvedSumInsured = approvedSumInsured;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public ProposalInsuredPerson getProposalInsuredPerson() {
		return proposalInsuredPerson;
	}

	public void setProposalInsuredPerson(ProposalInsuredPerson proposalInsuredPerson) {
		this.proposalInsuredPerson = proposalInsuredPerson;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		long temp;
		temp = Double.doubleToLongBits(approvedPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(approvedSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(premiumRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((proposalInsuredPerson == null) ? 0 : proposalInsuredPerson.hashCode());
		temp = Double.doubleToLongBits(proposedPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(proposedSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		InsuredPersonAddon other = (InsuredPersonAddon) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (Double.doubleToLongBits(approvedPremium) != Double.doubleToLongBits(other.approvedPremium))
			return false;
		if (Double.doubleToLongBits(approvedSumInsured) != Double.doubleToLongBits(other.approvedSumInsured))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(premiumRate) != Double.doubleToLongBits(other.premiumRate))
			return false;
		if (proposalInsuredPerson == null) {
			if (other.proposalInsuredPerson != null)
				return false;
		} else if (!proposalInsuredPerson.equals(other.proposalInsuredPerson))
			return false;
		if (Double.doubleToLongBits(proposedPremium) != Double.doubleToLongBits(other.proposedPremium))
			return false;
		if (Double.doubleToLongBits(proposedSumInsured) != Double.doubleToLongBits(other.proposedSumInsured))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
