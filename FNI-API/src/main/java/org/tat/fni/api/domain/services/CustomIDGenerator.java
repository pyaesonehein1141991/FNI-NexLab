package org.tat.fni.api.domain.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;
import org.tat.fni.api.common.CustomIDGeneratorException;
import org.tat.fni.api.common.ICustomIDGenerator;
import org.tat.fni.api.common.IDConfigLoader;
import org.tat.fni.api.common.IDGen;
import org.tat.fni.api.common.IDGenDAOInf;
import org.tat.fni.api.common.IUserProcessService;
import org.tat.fni.api.common.SystemConstants;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.User;
import org.tat.fni.api.exception.DAOException;

@Service("CustomIDGenerator")
public class CustomIDGenerator implements ICustomIDGenerator {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M-yyyy");

	@Resource(name = "ID_CONFIG")
	private Properties properties;

	@Resource(name = "IDGenDAO")
	private IDGenDAOInf idGenDAO;

	@Resource(name = "IDConfigLoader")
	private IDConfigLoader idConfigLoader;

	@Resource(name = "UserProcessService")
	private IUserProcessService userProcessService;

	public String getNextId(String key, String productCode) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatId(idGenDAO.getNextId(genName), productCode);

		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	public String getNextId(String key, String productCode, Branch branch) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatId(idGenDAO.getNextId(genName, branch), productCode);
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	/* This method is only for AutoRenewal Process */
	public String getNextIdForAutoRenewal(String key) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatIdForAutoRenewal(idGenDAO.getIDGenForAutoRenewal(genName), null);
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	/* This method is only for AutoRenewal Process */
	private String formatIdForAutoRenewal(IDGen idGen, String productCode) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();
		String branchCode = null;
		if (idConfigLoader.isCentralizedSystem()) {

			branchCode = idConfigLoader.getBranchCode();
		}
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		suffix = suffix == null ? "" : "/" + suffix;
		productCode = productCode == null ? "" : productCode;
		// TODO need to validate isDateBased
		id = prefix + productCode + "/" + getDateString() + "/" + id + "/" + branchCode + suffix;
		return id;
	}

	private String formatId(IDGen idGen, String productCode) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();
		String branchCode = null;
		if (idConfigLoader.isCentralizedSystem()) {
			branchCode = userProcessService.getLoginUser().getBranch().getPreFix();
		} else {
			branchCode = idConfigLoader.getBranchCode();
		}
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		if (suffix == null) {
			suffix = "";
		}
		if (productCode == null) {
			productCode = "";
		}
		// TODO need to validate isDateBased
		id = SystemConstants.FNI + "-" + branchCode + "/" + prefix + "/" + id + "/" + getDateString() + suffix;

		return id;
	}

	/** For FNI ****/
	private String formatCustomNo(IDGen idGen) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();
		String branchCode = null;
		if (idConfigLoader.isCentralizedSystem()) {
			branchCode = userProcessService.getLoginUser().getBranch().getPreFix();
		} else {
			branchCode = idConfigLoader.getBranchCode();
		}
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		if (suffix == null) {
			suffix = "";
		}

		// TODO need to validate isDateBased
		id = SystemConstants.FNI + "-" + branchCode + "/" + prefix + "/" + id + "/" + getDateString() + suffix;
		return id;
	}

	private String formatIdWithBranchCode(IDGen idGen, String productCode, Branch branch) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();

		boolean isDateBased = idGen.isDateBased();
		// use passed branch instead of login branch
		// String branchCode = null;
		// if (idConfigLoader.isCentralizedSystem()) {
		// branchCode =
		// userProcessService.getLoginUser().getBranch().getBranchCode();
		// } else {
		// branchCode = idConfigLoader.getBranchCode();
		// }
		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		if (suffix == null) {
			suffix = "";
		}
		if (productCode == null) {
			productCode = "";
		}
		if (isDateBased) {
			id = prefix + productCode + "/" + getDateString() + "/" + id + "/" + branch.getBranchCode() + suffix;
		} else {
			id = prefix + productCode + "/" + id + "/" + branch.getBranchCode() + suffix;
		}
		return id;
	}

	/* This method is only for AutoRenewal Process */
	public String getPrefixForAutoRenewal(Class cla) {
		String prefix = idConfigLoader.getFormat(cla.getName());
		return prefix;
	}

	public String getPrefix(Class cla) {
		return getPrefixStr(cla, null);
	}

	// public String getPrefix(Class cla, User user) {
	// return getPrefixStr(cla, user);
	// }

	private String getPrefixStr(Class cla, User user) {
		String branchCode = null;
		if (idConfigLoader.isCentralizedSystem()) {
			branchCode = user == null ? userProcessService.getLoginUser().getBranch().getBranchCode() : user.getBranch().getBranchCode();
		} else {
			branchCode = idConfigLoader.getBranchCode();
		}
		String prefix = idConfigLoader.getFormat(cla.getName());
		return prefix + branchCode;
	}

	private String getDateString() {
		return simpleDateFormat.format(new Date());
	}

	public static void main(String args[]) {
		CustomIDGenerator customIDGenerator = new CustomIDGenerator();
		customIDGenerator.getNextId("ss", "445");
	}

	public IDGen getIDGen(String key) throws CustomIDGeneratorException {
		IDGen idGen = null;
		try {
			String genName = (String) properties.getProperty(key);
			idGen = idGenDAO.getIDGen(genName);
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to Find IDGen", e);
		}
		return idGen;
	}

	public IDGen updateIDGen(IDGen idGen) throws CustomIDGeneratorException {
		try {
			idGen = idGenDAO.updateIDGen(idGen);
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to Update IDGen", e);
		}
		return idGen;
	}

	public String getNextIdWithBranchCode(String key, String productCode, Branch branch) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatIdWithBranchCode(idGenDAO.getNextId(genName, branch), productCode, branch);
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	public String getCustomNextId(String key, String productId) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatCustomNo(idGenDAO.getCustomNextNo(genName, productId));
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	public String getCustomNextId(String key) throws CustomIDGeneratorException {
		String id = null;
		try {
			String genName = (String) properties.getProperty(key);
			id = formatVoucherCustomNo(idGenDAO.getCustomNextNo(genName, null));
		} catch (DAOException e) {
			throw new CustomIDGeneratorException(e.getErrorCode(), "Failed to generate a ID", e);
		}
		return id;
	}

	private String formatVoucherCustomNo(IDGen idGen) {
		String id = idGen.getMaxValue() + "";
		String prefix = idGen.getPrefix();
		String suffix = idGen.getSuffix();
		int maxLength = idGen.getLength();
		String branchCode = null;

		branchCode = idConfigLoader.getBranchCode();

		int idLength = id.length();
		for (; (maxLength - idLength) > 0; idLength++) {
			id = '0' + id;
		}
		if (suffix == null) {
			suffix = "";
		}

		// TODO need to validate isDateBased
		id = prefix + "/" + getDateString() + "/" + id + "/" + branchCode;
		return id;
	}

}
