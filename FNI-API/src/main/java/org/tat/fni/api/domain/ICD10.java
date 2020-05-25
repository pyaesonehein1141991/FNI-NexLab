package org.tat.fni.api.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;


@Entity
@Table(name = TableName.ICD10)
@TableGenerator(name = "ICD10_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "ICD10_GEN", allocationSize = 10)
@NamedQueries(value = {
    @NamedQuery(name = "ICD10.findAll", query = "SELECT c FROM ICD10 c ORDER BY c.code ASC"),
    @NamedQuery(name = "ICD10.findById", query = "SELECT c FROM ICD10 c WHERE c.id = :id")})
@EntityListeners(IDInterceptor.class)
public class ICD10 {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "ICD10_GEN")
  private String id;
  private String code;
  private String description;
  @Version
  private int version;

  @Embedded
  private UserRecorder recorder;

  public ICD10() {}

  public ICD10(String id, String code, String description) {
    this.id = id;
    this.code = code;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
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
    result = prime * result + ((code == null) ? 0 : code.hashCode());
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    ICD10 other = (ICD10) obj;
    if (code == null) {
      if (other.code != null)
        return false;
    } else if (!code.equals(other.code))
      return false;
    if (recorder == null) {
      if (other.recorder != null)
        return false;
    } else if (!recorder.equals(other.recorder))
      return false;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (version != other.version)
      return false;
    return true;
  }

}
