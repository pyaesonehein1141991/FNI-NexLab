package org.tat.fni.api.common;

import org.tat.fni.api.domain.Product;

/**
 * The contract which defines the basic behaviors that all insured item
 * implementations are obliged to comply.
 * 
 * @author ACN
 * @since 1.0.0
 * @date 2013/05/20
 *
 */
public interface IInsuredItem {

	/**
	 * Accessor to retrieve the unique ID of the insured item.
	 * 
	 * @return the unique ID of the insured item
	 */
	public String getId();

	/**
	 * Accessor to retrieve the total insured amount.
	 * 
	 * @return the total insured amount
	 */
	public double getSumInsured();

	/**
	 * 
	 * @return
	 */
	public double getAddOnSumInsure();

	/**
	 * Accessor to retrieve the premium amount.
	 * 
	 * @return the premium amount
	 */
	public double getPremium();

	/**
	 * Accessor to retrieve the add-on premium amount.
	 * 
	 * @return the add-on premium amount
	 */
	public double getAddOnPremium();

	/**
	 * Accessor to retrieve the total premium amount.
	 * 
	 * @return the total premium amount
	 */
	public double getTotalPremium();

	/**
	 * Accessor to retrieve the basic premium term.
	 * 
	 * @return the basic premium term
	 */
	public double getBasicTermPremium();

	/**
	 * Accessor to retrieve the add-on premium term.
	 * 
	 * @return the add-on premium term
	 */
	public double getAddOnTermPremium();

	/**
	 * Accessor to retrieve the product which this insured item belongs to.
	 * 
	 * @return the {@link Product} instance which this insured item belongs to
	 */
	public Product getProduct();

	/**
	 * Internal usage of the underlying technologies, not a business related
	 * behavior.
	 * 
	 * @return version number
	 */
	public int getVersion();

}
