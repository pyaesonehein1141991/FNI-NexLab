package org.tat.fni.api.domain.services.Interfaces;


import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;

public interface ILifeProposalService {
	
	public <T> InsuredPersonKeyFactorValue createKeyFactorValue
	(KeyFactor keyfactor, ProposalInsuredPerson insuredPerson, T dto);
	
	public void setPeriodMonthForKeyFacterValue(int periodMonth, String paymentTypeId);
	
	public LifeProposal calculatePremium(LifeProposal lifeProposal);
	
	public void calculateTermPremium(LifeProposal lifeProposal);

}
