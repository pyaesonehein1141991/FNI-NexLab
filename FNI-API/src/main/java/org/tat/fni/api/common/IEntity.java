package org.tat.fni.api.common;

/**
 * The contract which defines the basic behaviors that all Entities are obliged
 * to comply.
 * 
 * @author Ace Plus
 * @since 1.0.0
 * @date 2013/06/02
 */
public interface IEntity {

	/**
	 * Accessor to obtain the unique identification of the <code>Entity</code>
	 * 
	 * @return the unique identification of the <code>Entity</code>
	 */
	public String getId();

	/**
	 * Mutator to specify the unique identification of the <code>Entity</code>
	 * 
	 * @param id
	 *            the unique identification of the <code>Entity</code>
	 */
	public void setId(String id);

	/**
	 * Accessor to retrieve the version number which is the internal usage of
	 * the underlying technologies, not a business related behavior.
	 * 
	 * @return version number
	 */
	public int getVersion();

	/**
	 * Mutator to specify the version number which is the internal usage of the
	 * underlying technologies, not a business related behavior.
	 * 
	 * @param version
	 *            the version number
	 */
	public void setVersion(int version);
}
