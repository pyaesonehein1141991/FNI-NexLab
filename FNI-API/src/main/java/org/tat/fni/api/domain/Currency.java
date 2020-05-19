/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
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
@Table(name = TableName.CUR)
@TableGenerator(name = "CURRENCY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CURRENCY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "Currency.findAll", query = "SELECT c FROM Currency c ORDER BY c.currencyCode ASC"),
		@NamedQuery(name = "Currency.findByCurrencyCode", query = "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCode"),
		@NamedQuery(name = "Currency.findById", query = "SELECT c FROM Currency c WHERE c.id = :id") })
@EntityListeners(IDInterceptor.class)
public class Currency implements Serializable {
	private static final long serialVersionUID = -6992572646153666363L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CURRENCY_GEN")
	private String id;

	@Column(name = "CUR")
	private String currencyCode;
	private String description;
	private String symbol;
	private String inwordDesp1;
	private String inwordDesp2;
	@Column(name = "HOMEINWORDDESP1")
	private String myanInwordDesp1;
	@Column(name = "HOMEINWORDDESP2")
	private String myanInwordDesp2;
	private Boolean isHomeCur;
	private float m1;
	private float m2;
	private float m3;
	private float m4;
	private float m5;
	private float m6;
	private float m7;
	private float m8;
	private float m9;
	private float m10;
	private float m11;
	private float m12;
	private float m13;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public Currency() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getInwordDesp1() {
		return inwordDesp1;
	}

	public void setInwordDesp1(String inwordDesp1) {
		this.inwordDesp1 = inwordDesp1;
	}

	public String getInwordDesp2() {
		return inwordDesp2;
	}

	public void setInwordDesp2(String inwordDesp2) {
		this.inwordDesp2 = inwordDesp2;
	}

	public Boolean getIsHomeCur() {
		return isHomeCur;
	}

	public void setIsHomeCur(Boolean isHomeCur) {
		this.isHomeCur = isHomeCur;
	}

	public float getM1() {
		return m1;
	}

	public void setM1(float m1) {
		this.m1 = m1;
	}

	public float getM2() {
		return m2;
	}

	public void setM2(float m2) {
		this.m2 = m2;
	}

	public float getM3() {
		return m3;
	}

	public void setM3(float m3) {
		this.m3 = m3;
	}

	public float getM4() {
		return m4;
	}

	public void setM4(float m4) {
		this.m4 = m4;
	}

	public float getM5() {
		return m5;
	}

	public void setM5(float m5) {
		this.m5 = m5;
	}

	public float getM6() {
		return m6;
	}

	public void setM6(float m6) {
		this.m6 = m6;
	}

	public float getM7() {
		return m7;
	}

	public void setM7(float m7) {
		this.m7 = m7;
	}

	public float getM8() {
		return m8;
	}

	public void setM8(float m8) {
		this.m8 = m8;
	}

	public float getM9() {
		return m9;
	}

	public void setM9(float m9) {
		this.m9 = m9;
	}

	public float getM10() {
		return m10;
	}

	public void setM10(float m10) {
		this.m10 = m10;
	}

	public float getM11() {
		return m11;
	}

	public void setM11(float m11) {
		this.m11 = m11;
	}

	public float getM12() {
		return m12;
	}

	public void setM12(float m12) {
		this.m12 = m12;
	}

	public float getM13() {
		return m13;
	}

	public void setM13(float m13) {
		this.m13 = m13;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMyanInwordDesp1() {
		return myanInwordDesp1;
	}

	public void setMyanInwordDesp1(String myanInwordDesp1) {
		this.myanInwordDesp1 = myanInwordDesp1;
	}

	public String getMyanInwordDesp2() {
		return myanInwordDesp2;
	}

	public void setMyanInwordDesp2(String myanInwordDesp2) {
		this.myanInwordDesp2 = myanInwordDesp2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inwordDesp1 == null) ? 0 : inwordDesp1.hashCode());
		result = prime * result + ((inwordDesp2 == null) ? 0 : inwordDesp2.hashCode());
		result = prime * result + ((isHomeCur == null) ? 0 : isHomeCur.hashCode());
		result = prime * result + Float.floatToIntBits(m1);
		result = prime * result + Float.floatToIntBits(m10);
		result = prime * result + Float.floatToIntBits(m11);
		result = prime * result + Float.floatToIntBits(m12);
		result = prime * result + Float.floatToIntBits(m13);
		result = prime * result + Float.floatToIntBits(m2);
		result = prime * result + Float.floatToIntBits(m3);
		result = prime * result + Float.floatToIntBits(m4);
		result = prime * result + Float.floatToIntBits(m5);
		result = prime * result + Float.floatToIntBits(m6);
		result = prime * result + Float.floatToIntBits(m7);
		result = prime * result + Float.floatToIntBits(m8);
		result = prime * result + Float.floatToIntBits(m9);
		result = prime * result + ((myanInwordDesp1 == null) ? 0 : myanInwordDesp1.hashCode());
		result = prime * result + ((myanInwordDesp2 == null) ? 0 : myanInwordDesp2.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		Currency other = (Currency) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
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
		if (inwordDesp1 == null) {
			if (other.inwordDesp1 != null)
				return false;
		} else if (!inwordDesp1.equals(other.inwordDesp1))
			return false;
		if (inwordDesp2 == null) {
			if (other.inwordDesp2 != null)
				return false;
		} else if (!inwordDesp2.equals(other.inwordDesp2))
			return false;
		if (isHomeCur == null) {
			if (other.isHomeCur != null)
				return false;
		} else if (!isHomeCur.equals(other.isHomeCur))
			return false;
		if (Float.floatToIntBits(m1) != Float.floatToIntBits(other.m1))
			return false;
		if (Float.floatToIntBits(m10) != Float.floatToIntBits(other.m10))
			return false;
		if (Float.floatToIntBits(m11) != Float.floatToIntBits(other.m11))
			return false;
		if (Float.floatToIntBits(m12) != Float.floatToIntBits(other.m12))
			return false;
		if (Float.floatToIntBits(m13) != Float.floatToIntBits(other.m13))
			return false;
		if (Float.floatToIntBits(m2) != Float.floatToIntBits(other.m2))
			return false;
		if (Float.floatToIntBits(m3) != Float.floatToIntBits(other.m3))
			return false;
		if (Float.floatToIntBits(m4) != Float.floatToIntBits(other.m4))
			return false;
		if (Float.floatToIntBits(m5) != Float.floatToIntBits(other.m5))
			return false;
		if (Float.floatToIntBits(m6) != Float.floatToIntBits(other.m6))
			return false;
		if (Float.floatToIntBits(m7) != Float.floatToIntBits(other.m7))
			return false;
		if (Float.floatToIntBits(m8) != Float.floatToIntBits(other.m8))
			return false;
		if (Float.floatToIntBits(m9) != Float.floatToIntBits(other.m9))
			return false;
		if (myanInwordDesp1 == null) {
			if (other.myanInwordDesp1 != null)
				return false;
		} else if (!myanInwordDesp1.equals(other.myanInwordDesp1))
			return false;
		if (myanInwordDesp2 == null) {
			if (other.myanInwordDesp2 != null)
				return false;
		} else if (!myanInwordDesp2.equals(other.myanInwordDesp2))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
