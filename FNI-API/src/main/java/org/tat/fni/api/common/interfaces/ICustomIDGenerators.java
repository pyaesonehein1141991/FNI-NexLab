package org.tat.fni.api.common.interfaces;

import org.tat.fni.api.common.CustomIDGeneratorException;
import org.tat.fni.api.common.IDGen;
import org.tat.fni.api.domain.Branch;

public interface ICustomIDGenerators {
	public String getNextId(String key, String productCode) throws CustomIDGeneratorException;

	public String getNextId(String key, String productCode, Branch branch) throws CustomIDGeneratorException;

	public String getCustomNextId(String key, String productId) throws CustomIDGeneratorException;

	public String getPrefix(Class cla) throws CustomIDGeneratorException;
	//
	// public String getPrefix(Class cla, User user) throws
	// CustomIDGeneratorException;

	public IDGen getIDGen(String key) throws CustomIDGeneratorException;

	public IDGen updateIDGen(IDGen idGen) throws CustomIDGeneratorException;

	public String getNextIdForAutoRenewal(String key);

	public String getPrefixForAutoRenewal(Class cla);

	public String getNextIdWithBranchCode(String key, String productCode, Branch branch) throws CustomIDGeneratorException;

	public String getCustomNextId(String key) throws CustomIDGeneratorException;

}
