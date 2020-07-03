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
import org.tat.fni.api.common.RiskyOccupation;
import org.tat.fni.api.domain.IPremiumCalculatorService;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.PremiumCalData;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.services.Interfaces.ILifeProposalService;
import org.tat.fni.api.exception.ErrorCode;
import org.tat.fni.api.exception.SystemException;

@Service
public class LifeProposalService implements ILifeProposalService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "PremiumCalculatorService")
	private IPremiumCalculatorService premiumCalculatorService;
	
	private int term = 0;
	private String paymentTypeID = "";
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> InsuredPersonKeyFactorValue createKeyFactorValue
	(KeyFactor keyfactor, ProposalInsuredPerson insuredPerson, T dto) {
		
		InsuredPersonKeyFactorValue insuredPersonKeyFactorValue = new InsuredPersonKeyFactorValue();
		insuredPersonKeyFactorValue.setKeyFactor(keyfactor);
		insuredPersonKeyFactorValue.setProposalInsuredPerson(insuredPerson);
		
		keyfactor = insuredPersonKeyFactorValue.getKeyFactor();

		// TODO get values from key_factor checker properties instead
		if (keyfactor.getValue().equals("Sum Insured")) {
			insuredPersonKeyFactorValue.setValue(insuredPerson.getApprovedSumInsured() + "");
		} else if (keyfactor.getValue().equals("Age")) {
			insuredPersonKeyFactorValue.setValue(insuredPerson.getAge() + "");
		} else if (keyfactor.getValue().equals("Term")) {
			if(term >= 0) {
				insuredPersonKeyFactorValue.setValue(term + "");
			}
		}else if (keyfactor.getValue().equals("PaymentType")){
			insuredPersonKeyFactorValue.setValue(paymentTypeID);
		}else if (keyfactor.getValue().equals("RiskyOccupation")) {
			if(insuredPerson.getRiskyOccupation() == null) {
				insuredPersonKeyFactorValue.setValue(RiskyOccupation.NO + "");
			}else {
				insuredPersonKeyFactorValue.setValue(RiskyOccupation.YES + "");
			}
		}else if (keyfactor.getValue().equals("Pound")) {
			insuredPersonKeyFactorValue.setValue(insuredPerson.getWeight() + "");
		}else if(keyfactor.getValue().equals("Dangerous Occupation")) {
			insuredPersonKeyFactorValue.setValue(
					insuredPerson.getRiskyOccupation() == null ? 
							0 + "" : insuredPerson.getRiskyOccupation().getExtraRate() + "");
		}
		
		return insuredPersonKeyFactorValue;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public LifeProposal calculatePremium(LifeProposal lifeProposal) {
		Double premium;
		Double premiumRate;
		double proposedSI;
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			proposedSI = pv.getProposedSumInsured();
			/* set approved SI (after approved, edit SI from enquire) */
			if (pv.isApproved()) {
				pv.setApprovedSumInsured(proposedSI);
			}
			Map<KeyFactor, String> keyfatorValueMap = new HashMap<KeyFactor, String>();
			for (InsuredPersonKeyFactorValue insukf : pv.getKeyFactorValueList()) {
				keyfatorValueMap.put(insukf.getKeyFactor(), insukf.getValue());
			}

			boolean isSportMan = product.getProductContent().getName().equals("SPORTMAN");
			boolean isSnakeBite = product.getProductContent().getName().equals("SNAKE BITE");
			boolean isStudentLife = product.getProductContent().getName().equals("STUDENT LIFE");

			if (isSportMan || isSnakeBite) {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product,
						new PremiumCalData(null, null, null, (double) pv.getUnit()));
				pv.setProposedSumInsured(pv.getUnit() * product.getSumInsuredPerUnit());
			} else {
				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
				pv.setPremiumRate(premiumRate);
				premium = premiumCalculatorService.calulatePremium(premiumRate, product,
						new PremiumCalData(null, proposedSI, null, null));
			}

//			if (isSportMan(product) || isSnakeBite(product)) {
//				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
//				pv.setPremiumRate(premiumRate);
//				premium = premiumCalculatorService.calulatePremium(premiumRate, product,
//						new PremiumCalData(null, null, null, (double) pv.getUnit()));
//				pv.setProposedSumInsured(pv.getUnit() * product.getSumInsuredPerUnit());
//			} else {
//				premiumRate = premiumCalculatorService.findPremiumRate(keyfatorValueMap, product);
//				pv.setPremiumRate(premiumRate);
//				premium = premiumCalculatorService.calulatePremium(premiumRate, product,
//						new PremiumCalData(null, proposedSI, null, null));
//			}

			if (isStudentLife) {
				switch (lifeProposal.getPaymentType().getMonth()) {
				case 1:
					pv.setProposedPremium(premium * 12);
					break;
				case 6:
					pv.setProposedPremium(premium * 2);
					break;
				case 3:
					pv.setProposedPremium(premium * 4);
					break;
				default:
					pv.setProposedPremium(premium);
				}
			} else {
				pv.setProposedPremium(premium);
			}

//			if (KeyFactorChecker.isStudentLife(product.getId())) {
//				// ratePremium = premium;
//				// premium = (ratePremium *
//				// lifeProposal.getPaymentType().getMonth());
//				switch (lifeProposal.getPaymentType().getMonth()) {
//				case 1:
//					pv.setProposedPremium(premium * 12);
//					break;
//				case 6:
//					pv.setProposedPremium(premium * 2);
//					break;
//				case 3:
//					pv.setProposedPremium(premium * 4);
//					break;
//				default:
//					pv.setProposedPremium(premium);
//				}
//				// pv.setProposedPremium(premium);
//			} else {
//				pv.setProposedPremium(premium);
//			}

			if (premium == null || premium < 0) {
				throw new SystemException(ErrorCode.NO_PREMIUM_RATE, keyfatorValueMap, "There is no premiumn.");
			}

//			List<InsuredPersonAddon> insuredPersonAddOnList = pv.getInsuredPersonAddOnList();
//			if (insuredPersonAddOnList != null && !insuredPersonAddOnList.isEmpty()) {
//				for (InsuredPersonAddon insuredPersonAddOn : insuredPersonAddOnList) {
//					double addOnPremium = 0.0;
//					double addOnPremiumRate = 0.0;
//					Map<KeyFactor, String> addOnKeyfatorValueMap = new HashMap<KeyFactor, String>();
//					if (insuredPersonAddOn.getAddOn().isBaseOnKeyFactor()) {
//						for (KeyFactor kf : insuredPersonAddOn.getAddOn().getKeyFactorList()) {
//							innerLoop: for (InsuredPersonKeyFactorValue ipKf : pv.getKeyFactorValueList()) {
//								if (kf.equals(ipKf.getKeyFactor())) {
//									addOnKeyfatorValueMap.put(kf, ipKf.getValue());
//									break innerLoop;
//								}
//							}
//							if (KeyFactorChecker.isGender(kf)) {
//								addOnKeyfatorValueMap.put(kf, pv.getGender().getLabel());
//							}
//						}
//					}
//					addOnPremium = premiumCalculatorService.calculatePremium(addOnKeyfatorValueMap,
//							insuredPersonAddOn.getAddOn(),
//							new PremiumCalData(insuredPersonAddOn.getProposedSumInsured(), proposedSI,
//									pv.getProposedPremium(), null));
//					addOnPremiumRate = premiumCalculatorService.findPremiumRate(addOnKeyfatorValueMap,
//							insuredPersonAddOn.getAddOn());
//					insuredPersonAddOn.setPremiumRate(addOnPremiumRate);
//					insuredPersonAddOn.setProposedPremium(addOnPremium);
//				}
//			}
		}
		return lifeProposal;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void calculateTermPremium(LifeProposal lifeProposal) {
		
		int paymentType = lifeProposal.getPaymentType().getMonth();
		Product product = lifeProposal.getProposalInsuredPersonList().get(0).getProduct();

		boolean isStudentLife = lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getProductContent()
				.getName().equals("STUDENT LIFE");

//		boolean isStudentLife = KeyFactorChecker
//				.isStudentLife(lifeProposal.getProposalInsuredPersonList().get(0).getProduct().getId());

		int paymentTerm = 0;
		double premium = 0, termPremium = 0, addOnPremium = 0;
		for (ProposalInsuredPerson pv : lifeProposal.getProposalInsuredPersonList()) {
			premium = pv.getProposedPremium();
			if (paymentType > 0) {
				if (isStudentLife) {
					paymentTerm = (lifeProposal.getPeriodOfYears() - 3) * 12 / paymentType;
				} else
					paymentTerm = lifeProposal.getPeriodMonth() / paymentType;// lifeProposal.getPeriodOfYear()*12

				termPremium = (paymentType * premium) / 12;
				pv.setBasicTermPremium(termPremium);
			} else {
				// *** Calculation for Lump Sum ***
//				if (KeyFactorChecker
//						.isPersonalAccident(lifeProposal.getProposalInsuredPersonList().get(0).getProduct()))
//					termPremium = (premium / 12) * lifeProposal.getPeriodMonth();
				if(product.getProductContent().getName().equals("PERSONAL ACCIDENT"))
					termPremium = (premium / 12) * lifeProposal.getPeriodMonth();
				else
					termPremium = (lifeProposal.getPeriodOfYears() * premium);
				pv.setBasicTermPremium(termPremium);
			}
			lifeProposal.setPaymentTerm(paymentTerm);

//			addOnPremium = pv.getAddOnPremium();
//			if (paymentType > 0) {
//				termPremium = (paymentType * addOnPremium) / 12;
//				pv.setAddOnTermPremium(termPremium);
//			} else {
//				// *** Calculation for Lump Sum AddOn Premium***
//				termPremium = (lifeProposal.getPeriodMonth() * addOnPremium);
//				pv.setAddOnTermPremium(termPremium);
//			}
		}

	}

	@Override
	public void setPeriodMonthForKeyFacterValue(int periodMonth, String paymentTypeId) {
		
		this.term = periodMonth;
		this.paymentTypeID = paymentTypeId;
		
	}

}
