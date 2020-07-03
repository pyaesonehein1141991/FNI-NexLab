package org.tat.fni.api.domain.services.ProposalServices;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.common.utils.Utils;
import org.tat.fni.api.domain.IPremiumCalculatorService;
import org.tat.fni.api.domain.MedicalKeyFactorValue;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonAddOn;
import org.tat.fni.api.domain.PremiumCalData;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProposalService;
import org.tat.fni.api.exception.SystemException;

@Service
public class MedicalProposalService implements IMedicalProposalService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;
	
	private int term = 0;
	private String paymentTypeID = "";

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> MedicalKeyFactorValue createKeyFactorValue(KeyFactor keyfactor,
			MedicalProposalInsuredPerson insuredPerson, T dto) {
		
		MedicalKeyFactorValue medicalKeyFactorValue = new MedicalKeyFactorValue();
		medicalKeyFactorValue.setKeyFactor(keyfactor);
		
		keyfactor = medicalKeyFactorValue.getKeyFactor();

		// TODO get values from keyfactor checker properties instead
		if (keyfactor.getValue().equals("Sum Insured")) {
//			medicalKeyFactorValue.setValue(insuredPerson.getApprovedSumInsured() + "");
		} else if (keyfactor.getValue().equals("Age")) {
			medicalKeyFactorValue.setValue(insuredPerson.getAge() + "");
		} else if (keyfactor.getValue().equals("Term")) {
			if(term >= 0) {
				medicalKeyFactorValue.setValue(term + "");
			}
		}else if (keyfactor.getValue().equals("PaymentType")){
			medicalKeyFactorValue.setValue(paymentTypeID);
		}else if (keyfactor.getValue().equals("RiskyOccupation")) {
//			if(insuredPerson.getRiskyOccupation() == null) {
//				medicalKeyFactorValue.setValue(RiskyOccupation.NO + "");
//			}else {
//				medicalKeyFactorValue.setValue(RiskyOccupation.YES + "");
//			}
		}else if (keyfactor.getValue().equals("Pound")) {
//			medicalKeyFactorValue.setValue(insuredPerson.getWeight() + "");
		}else if(keyfactor.getValue().equals("Dangerous Occupation")) {
//			medicalKeyFactorValue.setValue(
//					insuredPerson.getRiskyOccupation() == null ? 
//							0 + "" : insuredPerson.getRiskyOccupation().getExtraRate() + "");
		}
		
		return medicalKeyFactorValue;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public MedicalProposal calculatePremium(MedicalProposal medicalProposal) {
		
		double premium, addOnPremium, riskSIperUnit;
		int paymentTerm = 0;
		int paymentType = medicalProposal.getPaymentType().getMonth();
		int periodOfMonth = medicalProposal.getPeriodMonth();
		/* if not lumpSum payment */
		if (paymentType > 0) {
			paymentTerm = periodOfMonth / paymentType;
			if ((periodOfMonth % paymentType) > 0) {
				paymentTerm = paymentTerm + 1;
			}
		} else {
			/* lumpSum payment */
			paymentTerm = 1;
		}
		medicalProposal.setPaymentTerm(paymentTerm);
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			try {
				Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
				for (MedicalKeyFactorValue keyfactor : person.getKeyFactorValueList()) {
					keyfatorValueMap.put(keyfactor.getKeyFactor(), keyfactor.getValue());
				}
				premium = premiumCalculatorService.calculatePremium(keyfatorValueMap, person.getProduct(), new PremiumCalData(null, null, null, (double) person.getUnit()));
				person.setPremium(premium * paymentTerm);

				riskSIperUnit = person.getProduct().getSumInsuredPerUnit();
				person.setSumInsured(person.getUnit() * riskSIperUnit);

				for (MedicalProposalInsuredPersonAddOn insuredPersonAddOn : person.getInsuredPersonAddOnList()) {
					try {
						Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
						for (MedicalKeyFactorValue kfv : insuredPersonAddOn.getKeyFactorValueList()) {
							addOnKeyfatorValueMap.put(kfv.getKeyFactor(), kfv.getValue());
						}
						addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap, insuredPersonAddOn.getAddOn(),
								new PremiumCalData(null, null, person.getPremium(), (double) insuredPersonAddOn.getUnit()));
						insuredPersonAddOn.setPremium(addOnPremium * paymentTerm);
						riskSIperUnit = insuredPersonAddOn.getAddOn().getSumInsuredPerUnit();
						insuredPersonAddOn.setSumInsured(insuredPersonAddOn.getUnit() * riskSIperUnit);
					} catch (SystemException e) {
						if (e.getSource() == null)
							e.setSource("Insured Person - " + person.getFullName() + ", AddOn - " + insuredPersonAddOn.getAddOn().getName());
						throw e;
					}
				}
			} catch (SystemException e) {
				if (e.getSource() == null)
					e.setSource("Insured Person - " + person.getFullName() + ", Product - " + person.getProduct().getName());
				throw e;
			}
		}
		return medicalProposal;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void calculateTermPremium(MedicalProposal medicalProposal) {
		
		double premium = 0.0, addOnPremium = 0.0, termPremium = 0.0, addOnTermPremium = 0.0;
		int paymentTerm = medicalProposal.getPaymentTerm();
		for (MedicalProposalInsuredPerson person : medicalProposal.getMedicalProposalInsuredPersonList()) {
			premium = person.getPremium();
			addOnPremium = person.getAddOnPremium();

			termPremium = Utils.divide(premium, paymentTerm);
			addOnTermPremium = Utils.divide(addOnPremium, paymentTerm);
			person.setBasicTermPremium(termPremium);
			person.setAddOnTermPremium(addOnTermPremium);
		}
		
	}
	
	@Override
	public void setPeriodMonthForKeyFacterValue(int periodMonth, String paymentTypeId) {
		
		this.term = periodMonth;
		this.paymentTypeID = paymentTypeId;
		
	}

}
