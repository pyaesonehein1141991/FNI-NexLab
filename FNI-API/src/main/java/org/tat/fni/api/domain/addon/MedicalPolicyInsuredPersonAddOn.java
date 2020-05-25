package org.tat.fni.api.domain.addon;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.Version;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.domain.MedicalKeyFactorValue;
import org.tat.fni.api.domain.MedicalPolicyKeyFactorValue;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonAddOn;



@Entity
@Table(name = TableName.MEDICALPOLICYINSUREDPERSONADDON)
@TableGenerator(name = "MPOLINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MPOLINSUREDPERSONADDON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPersonAddOn {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSUREDPERSONADDON_GEN")
  private String id;

  private int unit;
  private double sumInsured;
  private double premium;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ADDONID", referencedColumnName = "ID")
  private AddOn addOn;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
  private List<MedicalPolicyKeyFactorValue> medicalKeyFactorValueList;

  @Embedded
  private UserRecorder recorder;

  @Version
  private int version;

  public MedicalPolicyInsuredPersonAddOn() {

  }

  public MedicalPolicyInsuredPersonAddOn(MedicalProposalInsuredPersonAddOn insuredPersonAddOn) {
    this.addOn = insuredPersonAddOn.getAddOn();
    this.unit = insuredPersonAddOn.getUnit();
    this.sumInsured = insuredPersonAddOn.getSumInsured();
    this.premium = insuredPersonAddOn.getPremium();
    for (MedicalKeyFactorValue kfv : insuredPersonAddOn.getKeyFactorValueList()) {
      addKeyFactorValue(new MedicalPolicyKeyFactorValue(kfv));
    }
  }

  public int getUnit() {
    return unit;
  }

  public void setUnit(int unit) {
    this.unit = unit;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public double getPremium() {
    return premium;
  }

  public void setPremium(double premium) {
    this.premium = premium;
  }

  public AddOn getAddOn() {
    return addOn;
  }

  public void setAddOn(AddOn addOn) {
    this.addOn = addOn;
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

  public List<MedicalPolicyKeyFactorValue> getMedicalKeyFactorValueList() {
    if (this.medicalKeyFactorValueList == null)
      this.medicalKeyFactorValueList = new ArrayList<MedicalPolicyKeyFactorValue>();
    return this.medicalKeyFactorValueList;
  }

  public void setMedicalKeyFactorValueList(
      List<MedicalPolicyKeyFactorValue> medicalKeyFactorValueList) {
    this.medicalKeyFactorValueList = medicalKeyFactorValueList;
  }

  public void addKeyFactorValue(MedicalPolicyKeyFactorValue kfv) {
    getMedicalKeyFactorValueList().add(kfv);
  }

  public double getSumInsured() {
    return sumInsured;
  }

  public void setSumInsured(double sumInsured) {
    this.sumInsured = sumInsured;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    long temp;
    temp = Double.doubleToLongBits(premium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + unit;
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
    MedicalPolicyInsuredPersonAddOn other = (MedicalPolicyInsuredPersonAddOn) obj;
    if (addOn == null) {
      if (other.addOn != null)
        return false;
    } else if (!addOn.equals(other.addOn))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
      return false;
    if (unit != other.unit)
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
