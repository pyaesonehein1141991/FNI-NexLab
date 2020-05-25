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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
import org.tat.fni.api.common.emumdata.ClaimStatus;
import org.tat.fni.api.common.interfaces.IInsuredItem;
import org.tat.fni.api.domain.addon.MedicalPolicyInsuredPersonAddOn;


@Entity
@Table(name = TableName.MEDICALPOLICYINSUREDPERSON)
@TableGenerator(name = "MPOLINSP_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MPOLINSP_GEN", allocationSize = 10)
@NamedQueries(value = {
    @NamedQuery(name = "MedicalPolicyInsuredPerson.findAll",
        query = "SELECT s FROM MedicalPolicyInsuredPerson s "),
    @NamedQuery(name = "MedicalPolicyInsuredPerson.findById",
        query = "SELECT s FROM MedicalPolicyInsuredPerson s WHERE s.id = :id"),
    @NamedQuery(name = "MedicalPolicyInsuredPerson.updateClaimStatus",
        query = "UPDATE MedicalPolicyInsuredPerson p SET p.claimStatus = :claimStatus WHERE p.id = :id")})
@EntityListeners(IDInterceptor.class)
public class MedicalPolicyInsuredPerson implements IInsuredItem, Serializable {
  private static final long serialVersionUID = -2214779925313647081L;
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MPOLINSP_GEN")
  private String id;

  private boolean actived;
  private boolean death;

  @Column(name = "HOSPITALDAYCOUNT")
  private int hosp_day_count;
  private int age;
  private int unit;

  private double sumInsured;
  private double premium;

  private double basicTermPremium;
  private double addonTermPremium;

  private double operationAmount;
  private double disabilityAmount;

  @Column(name = "INPERSONCODENO")
  private String insPersonCodeNo;

  @Column(name = "INPERSONGROUPCODENO")
  private String inPersonGroupCodeNo;

  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  private ClaimStatus claimStatus;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
  private RelationShip relationship;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
  private Product product;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
  private Customer customer;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPOLICYINSUREDPERSONGUARDIANID", referencedColumnName = "ID")
  private MedicalPolicyInsuredPersonGuardian guardian;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "MEDICALPOLICYINSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalPolicyInsuredPersonAttachment> attachmentList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
  private List<MedicalPolicyKeyFactorValue> keyFactorValueList;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
  private List<MedicalPolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList;

  @Version
  private int version;

  @Embedded
  private UserRecorder recorder;

  public MedicalPolicyInsuredPerson() {}

  public MedicalPolicyInsuredPerson(MedicalProposalInsuredPerson insuredPerson) {
    this.dateOfBirth = insuredPerson.getCustomer().getDateOfBirth();
    this.customer = insuredPerson.getCustomer();
    this.product = insuredPerson.getProduct();
    this.premium = insuredPerson.getPremium();
    this.relationship = insuredPerson.getRelationship();
    this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
    this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();
    this.age = insuredPerson.getAge();
    this.unit = insuredPerson.getUnit();
    this.sumInsured = insuredPerson.getSumInsured();
    this.basicTermPremium = insuredPerson.getBasicTermPremium();
    this.addonTermPremium = insuredPerson.getAddOnTermPremium();

    if (insuredPerson.getGuardian() != null) {
      this.guardian = new MedicalPolicyInsuredPersonGuardian(insuredPerson.getGuardian());
    }
    for (MedicalProposalInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
      addAttachment(new MedicalPolicyInsuredPersonAttachment(attachment));
    }

    for (MedicalProposalInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
      addAttachment(new MedicalPolicyInsuredPersonAttachment(attachment));
    }

    for (MedicalKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
      addKeyFactorValue(new MedicalPolicyKeyFactorValue(keyFactorValue));
    }

    for (MedicalProposalInsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson
        .getInsuredPersonBeneficiariesList()) {
      addInsuredPersonBeneficiaries(
          new MedicalPolicyInsuredPersonBeneficiaries(insuredPersonBeneficiaries));
    }

    for (MedicalPersonHistoryRecord record : insuredPerson.getMedicalPersonHistoryRecordList()) {
      addHistoryRecord(new MedicalPersonHistoryRecord(record));
    }

    for (MedicalProposalInsuredPersonAddOn addOn : insuredPerson.getInsuredPersonAddOnList()) {
      addInsuredPersonAddOn(new MedicalPolicyInsuredPersonAddOn(addOn));
    }
  }

  public double getOperationAmount() {
    return operationAmount;
  }

  public void setOperationAmount(double operationAmount) {
    this.operationAmount = operationAmount;
  }

  public double getDisabilityAmount() {
    return disabilityAmount;
  }

  public void setDisabilityAmount(double disabilityAmount) {
    this.disabilityAmount = disabilityAmount;
  }

  public int getHosp_day_count() {
    return hosp_day_count;
  }

  public void setHosp_day_count(int hosp_day_count) {
    this.hosp_day_count = hosp_day_count;
  }

  public boolean isActived() {
    return actived;
  }

  public void setActived(boolean actived) {
    this.actived = actived;
  }

  public String getFullName() {
    return customer.getFullName();
  }

  public MedicalPolicyInsuredPersonGuardian getGuardian() {
    return guardian;
  }

  public void setGuardian(MedicalPolicyInsuredPersonGuardian guardian) {
    this.guardian = guardian;
  }

  public boolean isDeath() {
    return death;
  }

  public void setDeath(boolean death) {
    this.death = death;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getInsPersonCodeNo() {
    return insPersonCodeNo;
  }

  public void setInsPersonCodeNo(String insPersonCodeNo) {
    this.insPersonCodeNo = insPersonCodeNo;
  }

  public String getInPersonGroupCodeNo() {
    return inPersonGroupCodeNo;
  }

  public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
    this.inPersonGroupCodeNo = inPersonGroupCodeNo;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public int getUnit() {
    return unit;
  }

  public void setUnit(int unit) {
    this.unit = unit;
  }

  public ClaimStatus getClaimStatus() {
    return claimStatus;
  }

  public void setClaimStatus(ClaimStatus claimStatus) {
    this.claimStatus = claimStatus;
  }

  public double getPremium() {
    return premium;
  }

  public void setPremium(double premium) {
    this.premium = premium;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public RelationShip getRelationship() {
    return relationship;
  }

  public void setRelationship(RelationShip relationship) {
    this.relationship = relationship;
  }

  public List<MedicalPolicyInsuredPersonAttachment> getAttachmentList() {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalPolicyInsuredPersonAttachment>();
    }
    return attachmentList;
  }

  public void setAttachmentList(List<MedicalPolicyInsuredPersonAttachment> attachmentList) {
    this.attachmentList = attachmentList;
  }

  public List<MedicalPolicyInsuredPersonAddOn> getPolicyInsuredPersonAddOnList() {
    if (policyInsuredPersonAddOnList == null) {
      policyInsuredPersonAddOnList = new ArrayList<MedicalPolicyInsuredPersonAddOn>();
    }
    return policyInsuredPersonAddOnList;
  }

  public void setPolicyInsuredPersonAddOnList(
      List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList) {
    this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
  }

  public List<MedicalPolicyKeyFactorValue> getKeyFactorValueList() {
    if (keyFactorValueList == null)
      keyFactorValueList = new ArrayList<MedicalPolicyKeyFactorValue>();
    return keyFactorValueList;
  }

  public void setKeyFactorValueList(List<MedicalPolicyKeyFactorValue> keyFactorValueList) {
    this.keyFactorValueList = keyFactorValueList;
  }

  public List<MedicalPolicyInsuredPersonBeneficiaries> getPolicyInsuredPersonBeneficiariesList() {
    if (this.policyInsuredPersonBeneficiariesList == null) {
      this.policyInsuredPersonBeneficiariesList =
          new ArrayList<MedicalPolicyInsuredPersonBeneficiaries>();
    }
    return this.policyInsuredPersonBeneficiariesList;

  }

  public void setPolicyInsuredPersonBeneficiariesList(
      List<MedicalPolicyInsuredPersonBeneficiaries> policyInsuredPersonBeneficiariesList) {
    this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public List<MedicalPersonHistoryRecord> getMedicalPersonHistoryRecordList() {
    if (medicalPersonHistoryRecordList == null) {
      medicalPersonHistoryRecordList = new ArrayList<MedicalPersonHistoryRecord>();
    }
    return medicalPersonHistoryRecordList;
  }

  public void setMedicalPersonHistoryRecordList(
      List<MedicalPersonHistoryRecord> medicalPersonHistoryRecordList) {
    this.medicalPersonHistoryRecordList = medicalPersonHistoryRecordList;
  }

  public void addHistoryRecord(MedicalPersonHistoryRecord record) {
    getMedicalPersonHistoryRecordList().add(record);
  }

  public void addAttachment(MedicalPolicyInsuredPersonAttachment attachment) {
    getAttachmentList().add(attachment);
  }

  public void addInsuredPersonAddOn(MedicalPolicyInsuredPersonAddOn policyInsuredPersonAddOn) {
    getPolicyInsuredPersonAddOnList().add(policyInsuredPersonAddOn);
  }

  public void addKeyFactorValue(MedicalPolicyKeyFactorValue keyFactorValue) {
    getKeyFactorValueList().add(keyFactorValue);
  }

  public void addInsuredPersonBeneficiaries(
      MedicalPolicyInsuredPersonBeneficiaries policyInsuredPersonBeneficiaries) {
    getPolicyInsuredPersonBeneficiariesList().add(policyInsuredPersonBeneficiaries);
  }

  public double getSumInsured() {
    return sumInsured;
  }

  public void setSumInsured(double sumInsured) {
    this.sumInsured = sumInsured;
  }

  public double getBasicTermPremium() {
    return basicTermPremium;
  }

  public void setBasicTermPremium(double basicTermPremium) {
    this.basicTermPremium = basicTermPremium;
  }

  public double getAddonTermPremium() {
    return addonTermPremium;
  }

  public void setAddonTermPremium(double addonTermPremium) {
    this.addonTermPremium = addonTermPremium;
  }

  public UserRecorder getRecorder() {
    return recorder;
  }

  public void setRecorder(UserRecorder recorder) {
    this.recorder = recorder;
  }

  /***************************
   * System Generated Method
   ***************************/
  public String getFatherName() {
    return this.customer.getFatherNameForView();
  }

  public String getFullIdNo() {
    return this.customer.getFullIdNoForView();
  }

  public Occupation getOccupation() {
    return this.customer.getOccupation();
  }

  public String getOccupationName() {
    if (getOccupation() != null)
      return getOccupation().getName();
    else
      return "-";
  }

  public String getFullAddress() {
    return this.customer.getFullAddress();
  }

  public String getGuardionName() {
    if (this.guardian != null) {
      return this.guardian.getCustomer().getFullName();
    } else {
      return "";
    }
  }

  public String getGuardionNRC() {
    if (this.guardian != null) {
      return this.guardian.getCustomer().getFullIdNo();
    } else {
      return "";
    }
  }

  public String getGuardionRelation() {
    if (this.guardian != null) {
      return this.guardian.getRelationship().getName();
    } else {
      return "";
    }
  }

  public int getTotalUnit() {
    int result = 0;
    for (MedicalPolicyInsuredPersonAddOn addOn : getPolicyInsuredPersonAddOnList()) {
      result += addOn.getUnit();
    }
    return result + getUnit();
  }

  public double getTotalPremium() {
    double result = getAddOnPremium() + getPremium();
    return result;
  }

  public double getAddOnPremium() {
    double premium = 0.0;
    for (MedicalPolicyInsuredPersonAddOn addOn : getPolicyInsuredPersonAddOnList()) {
      premium += addOn.getPremium();
    }
    return premium;
  }

  public double getTotalTermPremium() {
    return getBasicTermPremium() + getAddonTermPremium();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (actived ? 1231 : 1237);
    long temp;
    result = prime * result + age;
    temp = Double.doubleToLongBits(addonTermPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(basicTermPremium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
    result = prime * result + (death ? 1231 : 1237);
    temp = Double.doubleToLongBits(disabilityAmount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
    result = prime * result + hosp_day_count;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
    result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
    temp = Double.doubleToLongBits(operationAmount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(premium);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((product == null) ? 0 : product.hashCode());
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
    result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
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
    MedicalPolicyInsuredPerson other = (MedicalPolicyInsuredPerson) obj;
    if (actived != other.actived)
      return false;
    if (age != other.age)
      return false;
    if (Double.doubleToLongBits(addonTermPremium) != Double
        .doubleToLongBits(other.addonTermPremium))
      return false;
    if (Double.doubleToLongBits(basicTermPremium) != Double
        .doubleToLongBits(other.basicTermPremium))
      return false;
    if (claimStatus != other.claimStatus)
      return false;
    if (customer == null) {
      if (other.customer != null)
        return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (dateOfBirth == null) {
      if (other.dateOfBirth != null)
        return false;
    } else if (!dateOfBirth.equals(other.dateOfBirth))
      return false;
    if (death != other.death)
      return false;
    if (Double.doubleToLongBits(disabilityAmount) != Double
        .doubleToLongBits(other.disabilityAmount))
      return false;
    if (guardian == null) {
      if (other.guardian != null)
        return false;
    } else if (!guardian.equals(other.guardian))
      return false;
    if (hosp_day_count != other.hosp_day_count)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (inPersonGroupCodeNo == null) {
      if (other.inPersonGroupCodeNo != null)
        return false;
    } else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
      return false;
    if (insPersonCodeNo == null) {
      if (other.insPersonCodeNo != null)
        return false;
    } else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
      return false;
    if (Double.doubleToLongBits(operationAmount) != Double.doubleToLongBits(other.operationAmount))
      return false;
    if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
      return false;
    if (product == null) {
      if (other.product != null)
        return false;
    } else if (!product.equals(other.product))
      return false;
    if (recorder == null) {
      if (other.recorder != null)
        return false;
    } else if (!recorder.equals(other.recorder))
      return false;
    if (relationship == null) {
      if (other.relationship != null)
        return false;
    } else if (!relationship.equals(other.relationship))
      return false;
    if (unit != other.unit)
      return false;
    if (version != other.version)
      return false;
    return true;
  }

  @Override
  public double getAddOnSumInsure() {
    return 0;
  }

  @Override
  public double getAddOnTermPremium() {
    return addonTermPremium;
  }

}
