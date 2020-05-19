/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.common;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.tat.fni.api.common.emumdata.IdType;
import org.tat.fni.api.domain.Industry;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.RelationShip;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class FamilyInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String initialId;
	private String idNo;
	@Transient
	private String stateCode;
	@Transient
	private String townshipCode;
	@Transient
	private String idConditionType;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEOFBIRTH")
	private Date dateOfBirth;

	@Embedded
	private Name name;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INDUSTRYID", referencedColumnName = "ID")
	private Industry industry;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;
	@Transient
	private String tempId;

	public FamilyInfo() {
		tempId = System.nanoTime() + "";
	}

	public FamilyInfo(String initialId, String idNo, IdType idType, Date dateOfBirth, Name name, RelationShip relationShip, Industry industry, Occupation occupation) {
		this.initialId = initialId;
		this.idNo = idNo;
		this.idType = idType;
		this.dateOfBirth = dateOfBirth;
		this.name = name;
		this.idType = idType;
		this.relationShip = relationShip;
		this.industry = industry;
		this.occupation = occupation;
	}

	public Name getName() {
		if (this.name == null) {
			this.name = new Name();
		}
		return this.name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Occupation getOccupation() {
		return this.occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Industry getIndustry() {
		return this.industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public RelationShip getRelationShip() {
		if (relationShip == null) {
			return new RelationShip();
		} else {
			return this.relationShip;
		}
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public String getFullName() {
		return name.getFullName();
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}