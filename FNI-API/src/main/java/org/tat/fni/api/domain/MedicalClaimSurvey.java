package org.tat.fni.api.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.Hospital;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;

@Entity
@Table(name = TableName.MEDICALCLAIMSURVEY)
@TableGenerator(name = "MEDICALCLAIMSURVEY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALCLAIMSURVEY_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalClaimSurvey {
	@Column(name = "BOARDORNOTID")
	private boolean boardOrNot;
	@Version
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALCLAIMSURVEY_GEN")
	private String id;
	private String medicalOfficerName;
	private String rankAndQualification;
	private String address;
	private String remark;
	@Temporal(TemporalType.DATE)
	private Date surveyDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TOWNSHIPID", referencedColumnName = "ID")
	private Township township;

	@OneToOne
	@JoinColumn(name = "MEDICALPLACEID", referencedColumnName = "ID")
	private Hospital hospitalPlace;

	@Embedded
	private UserRecorder recorder;

	public MedicalClaimSurvey() {
	}

	public boolean isBoardOrNot() {
		return boardOrNot;
	}

	public int getVersion() {
		return version;
	}

	public String getId() {
		return id;
	}

	public String getMedicalOfficerName() {
		return medicalOfficerName;
	}

	public String getRankAndQualification() {
		return rankAndQualification;
	}

	public String getAddress() {
		return address;
	}

	public String getRemark() {
		return remark;
	}

	public Date getSurveyDate() {
		return surveyDate;
	}

	public Township getTownship() {
		return township;
	}

	public Hospital getHospital() {
		return hospitalPlace;
	}

	public void setBoardOrNot(boolean boardOrNot) {
		this.boardOrNot = boardOrNot;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMedicalOfficerName(String medicalOfficerName) {
		this.medicalOfficerName = medicalOfficerName;
	}

	public void setRankAndQualification(String rankAndQualification) {
		this.rankAndQualification = rankAndQualification;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	public void setTownship(Township township) {
		this.township = township;
	}

	public void setHospital(Hospital hospitalPlace) {
		this.hospitalPlace = hospitalPlace;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (boardOrNot ? 1231 : 1237);
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalOfficerName == null) ? 0 : medicalOfficerName.hashCode());
		result = prime * result + ((hospitalPlace == null) ? 0 : hospitalPlace.hashCode());
		result = prime * result + ((rankAndQualification == null) ? 0 : rankAndQualification.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((surveyDate == null) ? 0 : surveyDate.hashCode());
		result = prime * result + ((township == null) ? 0 : township.hashCode());
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
		MedicalClaimSurvey other = (MedicalClaimSurvey) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (boardOrNot != other.boardOrNot)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalOfficerName == null) {
			if (other.medicalOfficerName != null)
				return false;
		} else if (!medicalOfficerName.equals(other.medicalOfficerName))
			return false;
		if (hospitalPlace == null) {
			if (other.hospitalPlace != null)
				return false;
		} else if (!hospitalPlace.equals(other.hospitalPlace))
			return false;
		if (rankAndQualification == null) {
			if (other.rankAndQualification != null)
				return false;
		} else if (!rankAndQualification.equals(other.rankAndQualification))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (surveyDate == null) {
			if (other.surveyDate != null)
				return false;
		} else if (!surveyDate.equals(other.surveyDate))
			return false;
		if (township == null) {
			if (other.township != null)
				return false;
		} else if (!township.equals(other.township))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
