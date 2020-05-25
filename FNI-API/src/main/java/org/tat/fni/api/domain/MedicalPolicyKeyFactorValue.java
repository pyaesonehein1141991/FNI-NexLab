package org.tat.fni.api.domain;

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
import javax.persistence.Version;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name = TableName.MEDICALPOLICYKEYFACTORVALUE)
@TableGenerator(name = "MPOLINSUREDPERSONKEYFACTOR_GEN", table = "ID_GEN",
    pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
    pkColumnValue = "MPOLINSUREDPERSONKEYFACTOR_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyKeyFactorValue {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSUREDPERSONKEYFACTOR_GEN")
  private String id;

  private String value;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "KEYFACTORID", referencedColumnName = "ID")
  private KeyFactor keyFactor;

  @Embedded
  private UserRecorder recorder;
  @Version
  private int version;

  public MedicalPolicyKeyFactorValue() {
    super();
  }

  public MedicalPolicyKeyFactorValue(MedicalKeyFactorValue keyFactorValue) {
    this.value = keyFactorValue.getValue();
    this.keyFactor = keyFactorValue.getKeyFactor();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public KeyFactor getKeyFactor() {
    return keyFactor;
  }

  public void setKeyFactor(KeyFactor keyFactor) {
    this.keyFactor = keyFactor;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
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
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((keyFactor == null) ? 0 : keyFactor.hashCode());
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    MedicalPolicyKeyFactorValue other = (MedicalPolicyKeyFactorValue) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (keyFactor == null) {
      if (other.keyFactor != null)
        return false;
    } else if (!keyFactor.equals(other.keyFactor))
      return false;
    if (recorder == null) {
      if (other.recorder != null)
        return false;
    } else if (!recorder.equals(other.recorder))
      return false;
    if (value == null) {
      if (other.value != null)
        return false;
    } else if (!value.equals(other.value))
      return false;
    if (version != other.version)
      return false;
    return true;
  }
}
