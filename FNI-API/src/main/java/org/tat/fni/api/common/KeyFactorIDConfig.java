package org.tat.fni.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.tat.fni.api.common.emumdata.PolicyReferenceType;
import org.tat.fni.api.common.emumdata.ReferenceType;
import org.tat.fni.api.domain.Product;
import org.tat.fni.api.exception.ErrorCode;
import org.tat.fni.api.exception.SystemException;



public class KeyFactorIDConfig {
	/* key factor */
	private static String SUM_INSURED = "SUM_INSURED";

	/* branch */
	private static String YANGON_BRANCH = "YANGON_BRANCH";

	/* Currency */
	private static String USD_CUR = "USD_CUR";
	private static String KYT_CUR = "KYT_CUR";

	/* product */
	private static String PUBLIC_LIFE = "PUBLIC_LIFE";
	private static String STUDENT_LIFE = "STUDENT_LIFE";
	private static String GROUP_LIFE = "GROUP_LIFE";
	private static String PERSONAL_ACCIDENT_KYT = "PERSONAL_ACCIDENT_KYT";
	private static String PERSONAL_ACCIDENT_USD = "PERSONAL_ACCIDENT_USD";
	private static String SNAKE_BITE = "SNAKE_BITE";
	private static String SPORT_MAN = "SPORT_MAN";
	private static String FARMER = "FARMER";
	private static String SHORT_TERM_ENDOWMNENT = "SHORT_TERM_ENDOWMNENT";
	private static String INDIVIDUAL_HEALTH_INSURANCE = "INDIVIDUAL_HEALTH_INSURANCE";
	private static String GROUP_HEALTH_INSURANCE = "GROUP_HEALTH_INSURANCE";
	private static String MICRO_HEALTH_INSURANCE = "MICRO_HEALTH_INSURANCE";
	private static String INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE = "INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE";
	private static String GROUP_CRITICAL_ILLNESS_INSURANCE = "GROUP_CRITICAL_ILLNESS_INSURANCE";
	private static String EDUCATION_LIFE = "EDUCATION_LIFE";

	/* product content */
	private static String OPERATION_MED_ADDON_1 = "OPERATION_MED_ADDON_1";
	private static String CLINICAL_MED_ADDON_2 = "CLINICAL_MED_ADDON_2";

	/* co insurance company */
	private static String MI = "MI";

	/* relationship */
	private static String SELF_RELATIONSHIP = "SELF_RELATIONSHIP";

	/* transaction type */
	private static String CSCREDIT = "CSCREDIT";
	private static String CSDEBIT = "CSDEBIT";
	private static String TRDEBIT = "TRDEBIT";
	private static String TRCREDIT = "TRCREDIT";

	/* sale point */
	private static String HO_SALEPOINT = "HO";
	private static String MDY_SALEPOINT = "MDY";
	private static String MWA_SALEPOINT = "MWA";
	private static String SCG_SALEPOINT = "SCG";
	private static String KLY_SALEPOINT = "KLY";
	private static String MKN_SALEPOINT = "MKN";
	private static String PTN_SALEPOINT = "PTN";
	private static String MYK_SALEPOINT = "MYK";
	private static String NOK_SALEPOINT = "NOK";
	private static String BTT_SALEPOINT = "BTT";
	private static String MGW_SALEPOINT = "MGW";
	private static String MLM_SALEPOINT = "MLM";
	private static String PYA_SALEPOINT = "PYA";
	private static String MYG_SALEPOINT = "MYG";
	private static String NPT_SALEPOINT = "NPT";

	private static String IS_RECEIVABLE_SP = "IS_RECEIVABLE_SP";

	private static Properties idConfig;

	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("keyfactor-id-config.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load keyfactor-id-config.properties");
		}
	}

	public static String getUSDCurrencyId() {
		return idConfig.getProperty(USD_CUR);
	}

	public static String getKYTCurrencyId() {
		return idConfig.getProperty(KYT_CUR);
	}

	public static String getOperationAndMis() {
		return idConfig.getProperty(OPERATION_MED_ADDON_1);
	}

	public static String getClinical() {
		return idConfig.getProperty(CLINICAL_MED_ADDON_2);
	}

	public static String getPublicLifeId() {
		return idConfig.getProperty(PUBLIC_LIFE);
	}

	public static String getStudentLifeId() {
		return idConfig.getProperty(STUDENT_LIFE);
	}

	public static String getGroupLifeId() {
		return idConfig.getProperty(GROUP_LIFE);
	}

	public static String getFarmerId() {
		return idConfig.getProperty(FARMER);
	}

	public static String getShortEndowLifeId() {
		return idConfig.getProperty(SHORT_TERM_ENDOWMNENT);
	}

	public static String getPersonalAccidentMMKId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_KYT);
	}

	public static String getPersonalAccidentUSDId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_USD);
	}

	public static String getSnakeBikeId() {
		return idConfig.getProperty(SNAKE_BITE);
	}

	public static String getSportManId() {
		return idConfig.getProperty(SPORT_MAN);
	}

	public static String getYangonBranchId() {
		return idConfig.getProperty(YANGON_BRANCH);
	}

	public static String getEducationLifeId() {
		return idConfig.getProperty(EDUCATION_LIFE);
	}

	public static boolean isReceivableSalPoint() {
		return idConfig.getProperty(IS_RECEIVABLE_SP).equalsIgnoreCase("TRUE");
	}

	public static boolean isMYANMA_INSURANCE(String coCompanyId) {
		if (coCompanyId.trim().equals(idConfig.getProperty(MI))) {
			return true;
		}
		return false;
	}

	public static boolean isSumInsured(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(SUM_INSURED))) {
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

	public static boolean isShortTermEndowment(String id) {
		if (id.trim().equals(idConfig.getProperty(SHORT_TERM_ENDOWMNENT).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSnakeBite(String id) {
		if (id.trim().equals(idConfig.getProperty(SNAKE_BITE).trim())) {
			return true;
		}
		return false;
	}

	public static String getMedicalProductId() {
		return idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim();
	}

	public static String getSumInsuredId() {
		return idConfig.getProperty(SUM_INSURED);
	}

	public static boolean isFarmer(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(FARMER).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentKTY(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(PERSONAL_ACCIDENT_KYT).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentUSD(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(PERSONAL_ACCIDENT_USD).trim())) {
			return true;
		}
		return false;
	}

	// NEW MEDICAL
	public static boolean isIndividualHealthInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealthInsurancae(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isMicroHealthInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupCriticalIllnessInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualCriticalIllnessInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSelfRelationship(String relationshipId) {
		if (relationshipId.trim().equals(idConfig.getProperty(SELF_RELATIONSHIP).trim())) {
			return true;
		}
		return false;
	}

	public static String getMicroHealthInsurance() {
		return idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim();
	}

	public static String getIndividualHealthInsuranceId() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim();
	}

	public static String getGroupHealthInsuranceId() {
		return idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim();
	}

	public static String getIndividualCriticalIllness_Id() {
		return idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim();
	}

	public static String getGroupCriticalIllness_Id() {
		return idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim();
	}

	public static String getCSCredit() {
		return idConfig.getProperty(CSCREDIT);
	}

	public static String getCSDebit() {
		return idConfig.getProperty(CSDEBIT);
	}

	public static String getTRCredit() {
		return idConfig.getProperty(TRCREDIT);
	}

	public static String getTRDebit() {
		return idConfig.getProperty(TRDEBIT);
	}

	public static String getHOSalePoint() {
		return idConfig.getProperty(HO_SALEPOINT);
	}

	public static String getMDYSalePoint() {
		return idConfig.getProperty(MDY_SALEPOINT);
	}

	public static String getMWASalePoint() {
		return idConfig.getProperty(MWA_SALEPOINT);
	}

	public static String getSCGSalePoint() {
		return idConfig.getProperty(SCG_SALEPOINT);
	}

	public static String getKLYSalePoint() {
		return idConfig.getProperty(KLY_SALEPOINT);
	}

	public static String getMKNSalePoint() {
		return idConfig.getProperty(MKN_SALEPOINT);
	}

	public static String getPTNSalePoint() {
		return idConfig.getProperty(PTN_SALEPOINT);
	}

	public static String getMYKSalePoint() {
		return idConfig.getProperty(MYK_SALEPOINT);
	}

	public static String getNOKSalePoint() {
		return idConfig.getProperty(NOK_SALEPOINT);
	}

	public static String getBTTSalePoint() {
		return idConfig.getProperty(BTT_SALEPOINT);
	}

	public static String getMGWSalePoint() {
		return idConfig.getProperty(MGW_SALEPOINT);
	}

	public static String getMLMSalePoint() {
		return idConfig.getProperty(MLM_SALEPOINT);
	}

	public static String getPYASalePoint() {
		return idConfig.getProperty(PYA_SALEPOINT);
	}

	public static String getMYGSalePoint() {
		return idConfig.getProperty(MYG_SALEPOINT);
	}

	public static String getNPTSalePoint() {
		return idConfig.getProperty(NPT_SALEPOINT);
	}

	public static List<String> getIdByReferenceType(ReferenceType referenceType) {
		switch (referenceType) {
			case CRITICAL_ILLNESS:
				return Arrays.asList(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE), idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE));
			case HEALTH:
				return Arrays.asList(idConfig.getProperty(GROUP_HEALTH_INSURANCE), idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE));
			case MICRO_HEALTH:
				return Arrays.asList(idConfig.getProperty(MICRO_HEALTH_INSURANCE));
			default:
				return null;
		}
	}

	public static PolicyReferenceType getMedicalPolicyReferenceType(Product product) {
		if (isGroupHealthInsurancae(product) || isIndividualHealthInsurance(product)) {
			return PolicyReferenceType.HEALTH_POLICY;
		} else if (isMicroHealthInsurance(product)) {
			return PolicyReferenceType.MICRO_HEALTH_POLICY;
		} else if (isGroupCriticalIllnessInsurance(product) || isIndividualCriticalIllnessInsurance(product)) {
			return PolicyReferenceType.CRITICAL_ILLNESS_POLICY;
		}
		return null;
	}
}
