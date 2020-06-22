package org.tat.fni.api.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name = TableName.MEDICALHISTORY)
@TableGenerator(name = "MEDICALHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALHISTORY_GEN", allocationSize = 10)
@NamedQueries(value = {
    @NamedQuery(name = "MedicalHistory.findAll", query = "SELECT l FROM MedicalHistory l "),
    @NamedQuery(name = "MedicalHistory.findById",
        query = "SELECT l FROM MedicalHistory l WHERE l.id = :id")})
@EntityListeners(IDInterceptor.class)
public class MedicalHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALHISTORY_GEN")
  private String id;
  private String causeOfHospitalization;
  private String result;
  private String medicalOfficer;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ICD10_ID", referencedColumnName = "ID")
  private ICD10 icd10;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "HOSPITALID", referencedColumnName = "ID")
  private Hospital hospital;

  @Embedded
  private UserRecorder recorder;

  @Version
  private int version;

  public MedicalHistory() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCauseOfHospitalization() {
    return causeOfHospitalization;
  }

  public void setCauseOfHospitalization(String causeOfHospitalization) {
    this.causeOfHospitalization = causeOfHospitalization;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getMedicalOfficer() {
    return medicalOfficer;
  }

  public void setMedicalOfficer(String medicalOfficer) {
    this.medicalOfficer = medicalOfficer;
  }

  public ICD10 getIcd10() {
    return icd10;
  }

  public void setIcd10(ICD10 icd10) {
    this.icd10 = icd10;
  }

  public Hospital getHospital() {
    return hospital;
  }

  public void setHospital(Hospital hospital) {
    this.hospital = hospital;
  }

  public UserRecorder getRecorder() {
    return recorder;
  }

  public void setRecorder(UserRecorder recorder) {
    this.recorder = recorder;
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
    result =
        prime * result + ((causeOfHospitalization == null) ? 0 : causeOfHospitalization.hashCode());
    result = prime * result + ((hospital == null) ? 0 : hospital.hashCode());
    result = prime * result + ((icd10 == null) ? 0 : icd10.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((medicalOfficer == null) ? 0 : medicalOfficer.hashCode());
    result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
    MedicalHistory other = (MedicalHistory) obj;
    if (causeOfHospitalization == null) {
      if (other.causeOfHospitalization != null)
        return false;
    } else if (!causeOfHospitalization.equals(other.causeOfHospitalization))
      return false;
    if (hospital == null) {
      if (other.hospital != null)
        return false;
    } else if (!hospital.equals(other.hospital))
      return false;
    if (icd10 == null) {
      if (other.icd10 != null)
        return false;
    } else if (!icd10.equals(other.icd10))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (medicalOfficer == null) {
      if (other.medicalOfficer != null)
        return false;
    } else if (!medicalOfficer.equals(other.medicalOfficer))
      return false;
    if (result == null) {
      if (other.result != null)
        return false;
    } else if (!result.equals(other.result))
      return false;
    if (recorder == null) {
      if (other.recorder != null)
        return false;
    } else if (!recorder.equals(other.recorder))
      return false;
    if (version != other.version)
      return false;
    return true;
  }

}
