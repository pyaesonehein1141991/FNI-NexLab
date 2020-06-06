package org.tat.fni.api.common.interfaces;

import java.util.Date;

import org.tat.fni.api.domain.Currency;

public interface IProposal {

	public String getId();

	public Currency getCurrency();

	public String getUserType();

	public Date getStartDate();

	public Date getEndDate();

}
