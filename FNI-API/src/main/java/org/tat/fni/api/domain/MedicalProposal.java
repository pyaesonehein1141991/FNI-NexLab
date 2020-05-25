package org.tat.fni.api.domain;

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
import org.tat.fni.api.common.emumdata.ProposalStatus;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.emumdata.SaleChannelType;
import org.tat.fni.api.common.utils.Utils;


@Entity
@Table(name = TableName.MEDICALPROPOSAL)
@TableGenerator(name = "MEDICALPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME",
    valueColumnName = "GEN_VAL", pkColumnValue = "MEDICALPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = {
    @NamedQuery(name = "MedicalProposal.findAll", query = "SELECT m FROM MedicalProposal m "),
    @NamedQuery(name = "MedicalProposal.updateCompleteStatus",
        query = "UPDATE MedicalProposal  m SET m.complete = :complete WHERE m.id = :id")})
@EntityListeners(IDInterceptor.class)
public class MedicalProposal {
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEDICALPROPOSAL_GEN")
  private String id;
  private String proposalNo;

  private boolean complete;

  private int periodMonth;
  private int paymentTerm;
  private double specialDiscount;
  private double totalNcbAmount;

  @Temporal(TemporalType.DATE)
  private Date startDate;

  @Temporal(TemporalType.DATE)
  private Date endDate;

  @Enumerated(EnumType.STRING)
  private HealthType healthType;

  @Enumerated(EnumType.STRING)
  private ProposalType proposalType;

  @Enumerated(EnumType.STRING)
  private ProposalStatus proposalStatus;

  @Enumerated(EnumType.STRING)
  private SaleChannelType saleChannelType;

  @Enumerated(EnumType.STRING)
  private CustomerType customerType;

  @Column(name = "RATE")
  private double rate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date submittedDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
  private Branch branch;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SALESPOINTSID", referencedColumnName = "ID")
  private SalesPoints salesPoints;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
  private Customer customer;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
  private Organization organization;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAYMENTTYPEID", referencedColumnName = "ID")
  private PaymentType paymentType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "AGENTID", referencedColumnName = "ID")
  private Agent agent;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "OLDMEDICALPOLICYID", referencedColumnName = "ID")
  private MedicalPolicy oldMedicalPolicy;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPROPOSALID", referencedColumnName = "ID")
  private List<MedicalProposalInsuredPerson> medicalProposalInsuredPersonList;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPROPOSALID", referencedColumnName = "ID")
  private List<MedicalHistory> medicalHistoryList;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICALPROPOSALID", referencedColumnName = "ID")
  private List<MedicalProposalAttachment> attachmentList;

  @Embedded
  private UserRecorder recorder;

  @Version
  private int version;

  public MedicalProposal() {}

  public MedicalProposal(MedicalPolicy policy) {
    this.branch = policy.getBranch();
    this.salesPoints = policy.getSalesPoints();
    this.customer = policy.getCustomer();
    this.paymentType = policy.getPaymentType();
    this.agent = policy.getAgent();
    this.oldMedicalPolicy = policy;
    this.proposalType = policy.getMedicalProposal().getProposalType();
    this.customerType = policy.getCustomerType();
    this.organization = policy.getOrganization();
    this.paymentTerm = policy.getPaymentTerm();
    this.periodMonth = policy.getPeriodMonth();
    this.startDate = policy.getActivedPolicyStartDate();
    this.endDate = policy.getActivedPolicyEndDate();
    this.rate = policy.getRate();
    this.saleChannelType = policy.getSaleChannelType();
    this.healthType = policy.getHealthType();
    this.totalNcbAmount = policy.getTotalNcbAmount();

    /*
     * for (MedicalPolicyInsuredPerson policyInsuredPerson : policy.getPolicyInsuredPersonList()) {
     * addInsuredPerson(new MedicalProposalInsuredPerson(policyInsuredPerson)); }
     */
    for (MedicalPolicyAttachment policyAttachment : policy.getAttachmentList()) {
      addAttachment(new MedicalProposalAttachment(policyAttachment));
    }
  }

  public void addInsuredPerson(MedicalProposalInsuredPerson proposalInsuredPerson) {
    if (!getMedicalProposalInsuredPersonList().contains(proposalInsuredPerson)) {
      getMedicalProposalInsuredPersonList().add(proposalInsuredPerson);
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getProposalNo() {
    return proposalNo;
  }

  public void setProposalNo(String proposalNo) {
    this.proposalNo = proposalNo;
  }

  public ProposalType getProposalType() {
    return proposalType;
  }

  public void setProposalType(ProposalType proposalType) {
    this.proposalType = proposalType;
  }

  public Branch getBranch() {
    return branch;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public Customer getCustomer() {
    return customer;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public Agent getAgent() {
    return agent;
  }

  public int getVersion() {
    return version;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public void setCustomerType(CustomerType customerType) {
    this.customerType = customerType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }

  public void setAgent(Agent agent) {
    this.agent = agent;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Date getSubmittedDate() {
    return submittedDate;
  }

  public List<MedicalProposalAttachment> getAttachmentList() {
    if (attachmentList == null) {
      attachmentList = new ArrayList<MedicalProposalAttachment>();
    }
    return attachmentList;
  }

  public void setSubmittedDate(Date submittedDate) {
    this.submittedDate = submittedDate;
  }

  public void setAttachmentList(List<MedicalProposalAttachment> attachmentList) {
    this.attachmentList = attachmentList;
  }

  public HealthType getHealthType() {
    return healthType;
  }

  public void setHealthType(HealthType healthType) {
    this.healthType = healthType;
  }

  public List<MedicalProposalInsuredPerson> getMedicalProposalInsuredPersonList() {
    if (this.medicalProposalInsuredPersonList == null) {
      this.medicalProposalInsuredPersonList = new ArrayList<MedicalProposalInsuredPerson>();
    }
    return medicalProposalInsuredPersonList;
  }

  public void setMedicalProposalInsuredPersonList(
      List<MedicalProposalInsuredPerson> medicalProposalInsuredPersonList) {
    this.medicalProposalInsuredPersonList = medicalProposalInsuredPersonList;
  }

  public void addAttachment(MedicalProposalAttachment attachment) {
    getAttachmentList().add(attachment);
  }

  public void addMedicalProposalInsuredPerson(
      MedicalProposalInsuredPerson medicalProposalInsuredPerson) {
    if (medicalProposalInsuredPersonList == null) {
      medicalProposalInsuredPersonList = new ArrayList<MedicalProposalInsuredPerson>();
    }
    medicalProposalInsuredPersonList.add(medicalProposalInsuredPerson);
  }

  public UserRecorder getRecorder() {
    return recorder;
  }

  public SaleChannelType getSaleChannelType() {
    return saleChannelType;
  }

  public void setSaleChannelType(SaleChannelType saleChannelType) {
    this.saleChannelType = saleChannelType;
  }

  public void setRecorder(UserRecorder recorder) {
    this.recorder = recorder;
  }

  public MedicalPolicy getOldMedicalPolicy() {
    return oldMedicalPolicy;
  }

  public void setOldMedicalPolicy(MedicalPolicy oldMedicalPolicy) {
    this.oldMedicalPolicy = oldMedicalPolicy;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }

  public Integer getPeriodMonth() {
    return periodMonth;
  }

  public void setPeriodMonth(Integer periodMonth) {
    this.periodMonth = periodMonth;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public int getPaymentTerm() {
    return paymentTerm;
  }

  public void setPaymentTerm(int paymentTerm) {
    this.paymentTerm = paymentTerm;
  }

  public double getSpecialDiscount() {
    return specialDiscount;
  }

  public void setSpecialDiscount(double specialDiscount) {
    this.specialDiscount = specialDiscount;
  }

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }

  public ProposalStatus getProposalStatus() {
    return proposalStatus;
  }

  public void setProposalStatus(ProposalStatus proposalStatus) {
    this.proposalStatus = proposalStatus;
  }

  public boolean isComplete() {
    return complete;
  }

  public void setComplete(boolean complete) {
    this.complete = complete;
  }

  public SalesPoints getSalesPoints() {
    return salesPoints;
  }

  public double getTotalNcbAmount() {
    return totalNcbAmount;
  }

  public void setTotalNcbAmount(double totalNcbAmount) {
    this.totalNcbAmount = totalNcbAmount;
  }

  public String getSalePointName() {
    if (salesPoints != null)
      return salesPoints.getName();
    else
      return "";
  }

  public void setSalesPoints(SalesPoints salesPoints) {
    this.salesPoints = salesPoints;
  }

  public List<MedicalHistory> getMedicalHistoryList() {
    if (medicalHistoryList == null) {
      medicalHistoryList = new ArrayList<MedicalHistory>();
    }
    return medicalHistoryList;
  }

  public void setMedicalHistoryList(List<MedicalHistory> medicalHistoryList) {
    this.medicalHistoryList = medicalHistoryList;
  }

  /*****************************
   * System Generated Method
   *****************************/

  public void addMedicalHistory(MedicalHistory medicalHistory) {
    getMedicalHistoryList().add(medicalHistory);
  }

  public String getSalePerson() {
    if (agent != null) {
      return agent.getFullName();
    }
    return "";
  }

  public String getInsuredPersonName() {
    return medicalProposalInsuredPersonList.get(0).getFullName();
  }

  public int getTotalBasicUnit() {
    int unit = 0;
    for (MedicalProposalInsuredPerson person : getMedicalProposalInsuredPersonList()) {
      unit += person.getUnit();
    }
    return unit;
  }

  public int getTotalAddOnUnit() {
    int unit = 0;
    for (MedicalProposalInsuredPerson person : getMedicalProposalInsuredPersonList()) {
      unit += person.getTotalAddOnUnit();
    }
    return unit;
  }

  public int getTotalUnit() {
    return getTotalBasicUnit() + getTotalAddOnUnit();
  }

  public double getBasicPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson mp : getMedicalProposalInsuredPersonList()) {
      premium += mp.getPremium();
    }
    return premium;
  }

  public double getTotalPremium() {
    return getBasicPremium() + getAddOnPremium();
  }

  public double getAddOnPremium() {
    double premium = 0.0;
    for (MedicalProposalInsuredPerson pi : medicalProposalInsuredPersonList) {
      premium = Utils.getTwoDecimalPoint(premium + pi.getAddOnPremium());
    }
    return premium;
  }

  public String getCustomerName() {
    if (customer != null) {
      return customer.getFullName();
    } else if (organization != null) {
      return organization.getName();
    }
    return null;
  }

  public String getCustomerAddress() {
    if (customer != null) {
      return customer.getFullAddress();
    }
    if (organization != null) {
      return organization.getFullAddress();
    }
    return null;
  }

  public String getAgentNLiscenseNo() {
    if (agent != null) {
      return agent.getFullName() + " [" + agent.getLiscenseNo() + "]";
    } else
      return "N/A [0]";
  }

  public String getAgentName() {
    if (agent != null)
      return agent.getFullName();
    else
      return "N/A";
  }

  public String getAgentLiscenseNo() {
    if (agent != null)
      return "[" + agent.getLiscenseNo() + "]";
    else
      return "[0]";
  }
  /* System Auto Calculate Process */

  public double getTotalTermPremium() {
    return Utils.getTwoDecimalPoint(getTotalBasicTermPremium() + getTotalAddOnTermPremium());
  }

  public double getTotalBasicTermPremium() {
    double termPermium = 0.0;
    for (MedicalProposalInsuredPerson pv : medicalProposalInsuredPersonList) {
      termPermium = Utils.getTwoDecimalPoint(termPermium + pv.getBasicTermPremium());
    }
    return termPermium;
  }

  public double getTotalAddOnTermPremium() {
    double termPermium = 0.0;
    for (MedicalProposalInsuredPerson pv : medicalProposalInsuredPersonList) {
      termPermium = Utils.getTwoDecimalPoint(termPermium + pv.getAddOnTermPremium());
    }
    return termPermium;
  }

  public int getPeriodYears() {
    return periodMonth / 12;
  }

  public double getTotalSumInsured() {
    double totalSumInsured = 0.0;
    for (MedicalProposalInsuredPerson person : getMedicalProposalInsuredPersonList()) {
      totalSumInsured += person.getTotalSumInsured();
    }
    return totalSumInsured;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agent == null) ? 0 : agent.hashCode());
    result = prime * result + ((branch == null) ? 0 : branch.hashCode());
    result = prime * result + (complete ? 1231 : 1237);
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
    result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
    result = prime * result + ((healthType == null) ? 0 : healthType.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((oldMedicalPolicy == null) ? 0 : oldMedicalPolicy.hashCode());
    result = prime * result + ((organization == null) ? 0 : organization.hashCode());
    result = prime * result + paymentTerm;
    result = prime * result + ((paymentType == null) ? 0 : paymentType.hashCode());
    result = prime * result + periodMonth;
    result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
    result = prime * result + ((proposalStatus == null) ? 0 : proposalStatus.hashCode());
    result = prime * result + ((proposalType == null) ? 0 : proposalType.hashCode());
    long temp;
    temp = Double.doubleToLongBits(rate);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
    result = prime * result + ((saleChannelType == null) ? 0 : saleChannelType.hashCode());
    result = prime * result + ((salesPoints == null) ? 0 : salesPoints.hashCode());
    temp = Double.doubleToLongBits(specialDiscount);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
    result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
    temp = Double.doubleToLongBits(totalNcbAmount);
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
    MedicalProposal other = (MedicalProposal) obj;
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
    if (complete != other.complete)
      return false;
    if (customer == null) {
      if (other.customer != null)
        return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (customerType != other.customerType)
      return false;
    if (endDate == null) {
      if (other.endDate != null)
        return false;
    } else if (!endDate.equals(other.endDate))
      return false;
    if (healthType != other.healthType)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (oldMedicalPolicy == null) {
      if (other.oldMedicalPolicy != null)
        return false;
    } else if (!oldMedicalPolicy.equals(other.oldMedicalPolicy))
      return false;
    if (organization == null) {
      if (other.organization != null)
        return false;
    } else if (!organization.equals(other.organization))
      return false;
    if (paymentTerm != other.paymentTerm)
      return false;
    if (paymentType == null) {
      if (other.paymentType != null)
        return false;
    } else if (!paymentType.equals(other.paymentType))
      return false;
    if (periodMonth != other.periodMonth)
      return false;
    if (proposalNo == null) {
      if (other.proposalNo != null)
        return false;
    } else if (!proposalNo.equals(other.proposalNo))
      return false;
    if (proposalStatus != other.proposalStatus)
      return false;
    if (proposalType != other.proposalType)
      return false;
    if (Double.doubleToLongBits(rate) != Double.doubleToLongBits(other.rate))
      return false;
    if (recorder == null) {
      if (other.recorder != null)
        return false;
    } else if (!recorder.equals(other.recorder))
      return false;
    if (saleChannelType != other.saleChannelType)
      return false;
    if (salesPoints == null) {
      if (other.salesPoints != null)
        return false;
    } else if (!salesPoints.equals(other.salesPoints))
      return false;
    if (Double.doubleToLongBits(specialDiscount) != Double.doubleToLongBits(other.specialDiscount))
      return false;
    if (startDate == null) {
      if (other.startDate != null)
        return false;
    } else if (!startDate.equals(other.startDate))
      return false;
    if (submittedDate == null) {
      if (other.submittedDate != null)
        return false;
    } else if (!submittedDate.equals(other.submittedDate))
      return false;
    if (Double.doubleToLongBits(totalNcbAmount) != Double.doubleToLongBits(other.totalNcbAmount))
      return false;
    if (version != other.version)
      return false;
    return true;
  }

}
