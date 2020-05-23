/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.exception;

public class ErrorCode {
	/* Common DB */
	public static final String SYSTEM_ERROR = "SYSTEM_ERROR";
	public static final String DAO_RUNTIME_ERROR = "DAO_RUNTIME_ERROR";
	public static final String NO_SQL_ERROR_CODE_CONFIG = "NO_SQL_ERROR_CODE_CONFIG";
	public static final String JOB_IS_ALREADY_RUNNING = "JOB_IS_ALREADY_RUNNING";

	/* Business Logic Error Code */
	public static final String NO_PREMIUM_RATE = "NO_PREMIUM_RATE";
	public static final String MEDICAL_AGE_KEYFACTOR_NOTMATCH = "MEDICAL_AGE_KEYFACTOR_NOTMATCH";
	public static final String AUTHENGICATION_FAILED = "AUTHENGICATION_FAILED";
	public static final String OLD_PASSWORD_DOES_NOT_MATCH = "OLD_PASSWORD_DOES_NOT_MATCH";
	public static final String NO_BUILDING_CLASS = "NO_BUILDING_CLASS";
	public static final String NO_SUM_INSURED_KEYFACTOR = "NO_SUM_INSURED_KEYFACTOR";

	/* For interfaceFile import count mismatch */
	public static final String INT_FILE_COUNT_MISMATCH = "INT_FILE_COUNT_MISMATCH";
	public static final String INT_FILE_EXIST = "INT_FILE_EXIST";
	public static final String OVERAL_SUM_NOT_ZERO = "OVERAL_SUM_NOT_ZERO";
	public static final String GROUP_SUM_NOT_ZERO = "GROUP_SUM_NOT_ZERO";
	public static final String ACODE_FETCH_FAILED = "ACODE_FETCH_FAILED";
	public static final String NO_TLF_CONVERSION_FORMAT = "NO_TLF_CONVERSION_FORMAT";
	public static final String NO_INTERFACE_FILE = "NO_INTERFACE_FILE";
	public static final String NO_PARIED_KEY_CALCULATION_METHOD = "NO_PARIED_KEY_CALCULATION_METHOD";

	/* New for duplicate policy */
	public static final String PROPOSAL_ALREADY_CONFIRMED = "PROPOSAL_ALREADY_CONFIRMED";
	
	/**
	 * SYSTEM_ERROR_RESOURCE_NOT_FOUND
	 */
	
	public static final String SYSTEM_ERROR_RESOURCE_NOT_FOUND = "SYSTEM_ERROR_RESOURCE_NOT_FOUND";

}