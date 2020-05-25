/**
 * 
 */
package org.tat.fni.api.domain;

import java.util.Date;
import java.util.List;
import org.tat.fni.api.common.emumdata.InsuranceType;
import org.tat.fni.api.common.emumdata.ProposalType;
import org.tat.fni.api.common.interfaces.IInsuredItem;


/**
 * The contract which defines the basic behaviors that all Policy implementations are obliged to
 * comply.
 * 
 * @author ACN
 * @since 1.0.0
 * @date 2013/05/13
 * 
 */
public interface IPolicy {
  /**
   * The accessor to retrieve the unique identification number of the Policy
   * 
   * @return the unique identification number of the Policy
   */
  public String getId();

  /**
   * The accessor to retrieve the policy number of the policy instance.
   * 
   * @return the policy number
   */
  public String getPolicyNo();

  /**
   * TODO: TBC with the project stake holders
   * 
   * @return the total amount of discount
   */
  public double getTotalDiscountAmount();

  /**
   * Accessor to retrieve the premium amount of the policy instance.
   * 
   * @return the premium amount
   */
  public double getPremium();

  /**
   * TODO: TBC with the project stake holders
   * 
   * @return the addon premium
   */
  public double getAddOnPremium();

  /**
   * Accessor to retrieve the total amount of the premium.
   * 
   * @return the total amount of the premium
   */
  public double getTotalPremium();

  /**
   * Accessor to retrieve the total amount of the term premium.
   * 
   * @return the total amount of the term premium
   */
  public double getTotalTermPremium();

  /**
   * Accessor to retrieve the total insured amount.
   * 
   * @return the toal insured amount
   */
  public double getTotalSumInsured();

  /**
   * Accessor to retrieve the total unit amount.
   * 
   * @return the total unit amount
   */
  public int getTotalUnit();

  /**
   * Accessor to retrieve the status whether this policy instance is applied with Co-insurance.
   * 
   * @return the Co-insurance appliance status
   */
  public boolean isCoinsuranceApplied();

  /**
   * Accessor to retrieve the date when this policy comes into effect.
   * 
   * @return the policy's effective date
   */
  public Date getCommenmanceDate();

  /**
   * Accessor to retrieve the agent who is responsible for this policy.
   * 
   * @return An instance of the {@link Agent} associated
   */
  public Agent getAgent();

  /**
   * Accessor to retrieve the agent Commission for this policy.
   * 
   * @return The Commission of Agent For this policy.
   */
  public double getAgentCommission();

  /**
   * Accessor to retrieve the agent Commission for this policy.
   * 
   * @return The Commission of Agent For this policy.
   */
  public double getRenewalAgentCommission();

  /**
   * Accessor to retrieve the type of payment applied in this policy.
   * 
   * @return An instance of {@link PaymentType} representing the type of payment
   */
  public PaymentType getPaymentType();

  /**
   * TODO: TBC with the project stake holders
   * 
   * @return An instance of {@link Organization} associated
   */
  public Organization getOrganization();

  /**
   * TODO: TBC with the project stake holders
   * 
   * @return An instance of {@link Branch} associated
   */
  public Branch getBranch();

  /**
   * Accessor to retrieve the customer to whom this policy belongs to.
   * 
   * @return the {@link Customer} to whom this policy belongs to
   */
  public Customer getCustomer();

  /**
   * Accessor to retrieve the name of customer to whom this policy belongs to.
   * 
   * @return the name of customer to whom this policy belongs to
   */
  public String getCustomerName();

  /**
   * Accessor to retrieve the Id of customer to whom this policy belongs to.
   * 
   * @return the Id of customer to whom this policy belongs to
   */
  public String getCustomerId();

  /**
   * Accessor to retrieve the address of customer to whom this policy belongs to.
   * 
   * @return the address of customer to whom this policy belongs to
   */
  public String getCustomerAddress();

  /**
   * Accessor to retrieve the category of the product this policy belongs to.
   * 
   * @return the {@link ProductGroup} this policy belongs to
   */
  public ProductGroup getProductGroup();

  /**
   * TODO: TBC with the project stake holders
   * 
   * @return An instance of {@link User}
   */
  // public User getApprovedBy();

  /**
   * Accessor to retrieve the list of insured item details.
   * 
   * @return the list of insured item details
   */
  public List<IInsuredItem> getInsuredItems();

  /**
   * Internal usage of the underlying technologies, not a business related behavior.
   * 
   * @return version number
   */
  public int getVersion();

  /**
   * Tnsurance type to decide what kind of policy. eg : Motor, Fire or Life
   * 
   * @return InsuranceType
   */
  public InsuranceType getInsuranceType();

  public ProposalType getProposalType();

  public Date getActivedPolicyStartDate();

  public Date getActivedPolicyEndDate();

  public SalesPoints getSalesPoints();
}
