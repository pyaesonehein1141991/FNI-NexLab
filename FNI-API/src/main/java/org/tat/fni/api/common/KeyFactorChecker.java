package org.tat.fni.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.tat.fni.api.domain.Product;

public class KeyFactorChecker {

	private static String SUM_INSURED = "SUM_INSURED";
	private static String AGE = "AGE";
	private static String TERM = "TERM";
	private static String POUND = "POUND";
	private static String MEDICAL_AGE = "MEDICAL_AGE";
	private static String DANGEROUS_OCCUPATION = "DANGEROUS_OCCUPATION";
	private static String PAYMENTTYPE = "PAYMENTTYPE";
	private static String GENDER = "GENDER";
	private static String RISKYOCCUPATION = "RISKYOCCUPATION";
	private static String PAID_TERM = "PAIDTERM";
	private static String AGE_FROM_TO = "AGE_FROM_TO";

	private static String POLICY_PERIOD = "POLICY_PERIOD";
	private static String PAYMENT_YEAR = "PAYMENT_YEAR";
	private static String LIFE_SURRENDER_PRODUCT = "LIFE_SURRENDER_PRODUCT";
	private static String LIFE_PAIDUP_PRODUCT = "LIFE_PAIDUP_PRODUCT";
	private static String LUMPSUM = "LUMPSUM";

	private static String PUBLIC_LIFE = "PUBLIC_LIFE";
	private static String PUBLIC_TERM_LIFE = "PUBLIC_TERM_LIFE";
	private static String GROUP_LIFE = "GROUP_LIFE";
	private static String SPORT_MAN = "SPORT_MAN";
	private static String SHORT_TERM_ENDOWMNENT = "SHORT_TERM_ENDOWMNENT";
	private static String PERSONAL_ACCIDENT_KYT = "PERSONAL_ACCIDENT_KYT";
	private static String PERSONAL_ACCIDENT_USD = "PERSONAL_ACCIDENT_USD";
	private static String FARMER = "FARMER";
	private static String SNAKE_BITE = "SNAKE_BITE";
	private static String INDIVIDUAL_HEALTH_INSURANCE = "INDIVIDUAL_HEALTH_INSURANCE";
	private static String GROUP_HEALTH_INSURANCE = "GROUP_HEALTH_INSURANCE";
	private static String MICRO_HEALTH_INSURANCE = "MICRO_HEALTH_INSURANCE";
	private static String INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE = "INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE";
	private static String GROUP_CRITICAL_ILLNESS_INSURANCE = "GROUP_CRITICAL_ILLNESS_INSURANCE";
	private static String SHORTTERM_SURRENDER_PRODUCT = "SHORTTERM_SURRENDER_PRODUCT";

	private static String STUDENT_LIFE = "STUDENT_LIFE";
	private static String SON = "SON";
	private static String DAUGHTER = "DAUGHTER";

	private static Properties idConfig;

	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/keyfactor-id-config.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load keyfactor-id-config.properties");
		}
	}

	public static boolean isSumInsured(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(SUM_INSURED).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPublicLife(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PUBLIC_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupLife(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(GROUP_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSportMan(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(SPORT_MAN).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isTerm(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(TERM).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isAge(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(AGE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPound(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(POUND).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isDangerousOccupation(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(DANGEROUS_OCCUPATION).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isShortTermEndowment(String productId) {
		if (productId.trim().equals(idConfig.getProperty(SHORT_TERM_ENDOWMNENT).trim())) {
			return true;
		}
		return false;
	}

	public static String getGroupLifeID() {
		return idConfig.getProperty(GROUP_LIFE);
	}

	public static String getFarmerId() {
		return idConfig.getProperty(FARMER);
	}

	public static String getPublicLifeId() {
		return idConfig.getProperty(PUBLIC_LIFE);
	}

	public static String getStudentLifeID() {
		return idConfig.getProperty(STUDENT_LIFE);
	}

	public static String getShortTermEndowmentId() {
		return idConfig.getProperty(SHORT_TERM_ENDOWMNENT);
	}

	public static String getPublicTermLifeId() {
		return idConfig.getProperty(PUBLIC_TERM_LIFE);
	}

	public static String getSportManId() {
		return idConfig.getProperty(SPORT_MAN);
	}

	public static String getMicroHealthId() {
		return idConfig.getProperty(MICRO_HEALTH_INSURANCE);
	}

	public static String getIndividualHealththId() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE);
	}

	public static String getGroupHealththId() {
		return idConfig.getProperty(GROUP_HEALTH_INSURANCE);
	}

	public static String getIndividualCriticalIllnessId() {
		return idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE);
	}

	public static String getGroupCriticalIllnessId() {
		return idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE);
	}

	public static String getSnakeBiteId() {
		return idConfig.getProperty(SNAKE_BITE);
	}

	public static String getPersonalAccidentKytId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_KYT);
	}

	public static String getPersonalAccidentUsdId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_USD);
	}

	public static String getSonfromRelationShipTable() {
		return idConfig.getProperty(SON);
	}

	public static String getDaughterfromRelationShipTable() {
		return idConfig.getProperty(DAUGHTER);
	}

	public static boolean isMedicalAge(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(MEDICAL_AGE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPaymentYear(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(PAID_TERM).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPolicyPeriod(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(TERM).trim())) {
			return true;
		}
		return false;
	}

	public static String getLifeSurrenderProductId() {
		return idConfig.getProperty(LIFE_SURRENDER_PRODUCT);
	}

	public static String getShortTermSurrenderProductId() {
		return idConfig.getProperty(SHORTTERM_SURRENDER_PRODUCT);
	}

	public static String getLifePaidUpProductId() {
		return idConfig.getProperty(LIFE_PAIDUP_PRODUCT);
	}

	public static String getPaymentTypeLumpsumId() {
		return idConfig.getProperty(LUMPSUM);
	}

	public static boolean isPaymentType(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(PAYMENTTYPE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGender(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(GENDER).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isFarmer(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(FARMER).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccident(Product product) {
		
		if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_KYT).trim())) {
			return true;
		} else if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_USD).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentKYT(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_KYT).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentUSD(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_USD).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isStudentLife(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(STUDENT_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSnakeBite(String productId) {
		if (productId.trim().equals(idConfig.getProperty(SNAKE_BITE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isHealth(String productId) {
		return isIndividualHealth(productId) || isGroupHealth(productId);
	}

	public static boolean isIndividualHealth(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealth(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isMicroHealth(String productId) {
		if (productId.trim().equals(idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isCriticalIllness(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupCriticalIllness(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isStudentLife(String productId) {
		if (productId.trim().equals(idConfig.getProperty(STUDENT_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPublicTermLife(String productId) {
		if (productId.trim().equals(idConfig.getProperty(PUBLIC_TERM_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isRiskyOccupation(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(RISKYOCCUPATION).trim())) {
			return true;
		}
		return false;
	}

	public static List<String> getSurrenderProductId() {
		return Arrays.asList(idConfig.getProperty(LIFE_SURRENDER_PRODUCT), idConfig.getProperty(SHORTTERM_SURRENDER_PRODUCT));
	}

}
