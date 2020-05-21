package org.tat.fni.api.common;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.tat.fni.api.domain.Township;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OfficeAddress implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String officeAddress;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFFICETOWNSHIPID", referencedColumnName = "ID")
	private Township township;

}