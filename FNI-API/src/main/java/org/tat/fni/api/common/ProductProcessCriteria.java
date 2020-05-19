package org.tat.fni.api.common;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.tat.fni.api.common.emumdata.StudentAgeType;

@Embeddable
public class ProductProcessCriteria {
	@Enumerated(value = EnumType.STRING)
	private StudentAgeType studentAgeType;
	private int minAge;
	private int maxAge;
	private double sumInsured;

	public StudentAgeType getStudentAgeType() {
		return studentAgeType;
	}

	public void setStudentAgeType(StudentAgeType studentAgeType) {
		this.studentAgeType = studentAgeType;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

}
