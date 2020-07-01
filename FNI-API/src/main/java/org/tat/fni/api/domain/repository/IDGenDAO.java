package org.tat.fni.api.domain.repository;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.common.IDGen;
import org.tat.fni.api.common.interfaces.IDGenDAOInf;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.exception.DAOException;

@Repository("IDGenDAO")
public class IDGenDAO extends BasicDAO implements IDGenDAOInf {

	// TODO FIXME PSH
	// @Resource(name = "IDConfigLoader")
	// @Autowired
	// protected IDConfigLoader configLoader;

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getNextId(String generateItem, boolean isIgnoreBranch) throws DAOException {
		IDGen idGen = null;
		String branchCode = "";
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem");
			if (!isIgnoreBranch) {
				query.append(" AND g.branch.id IN (SELECT b.id FROM Branch b Where b.branchCode = :branchCode)");
			} else {
				query.append(" AND g.branch IS NULL ");
			}
			Query selectQuery = em.createQuery(query.toString());
			selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			selectQuery.setHint("javax.persistence.lock.timeout", 30000);
			// TODO FIXME PSH
			// if (configLoader.isCentralizedSystem()) {
			// branchCode =
			// userProcessService.getLoginUser().getBranch().getBranchCode();
			// } else {
			// branchCode = configLoader.getBranchCode();
			// }
			selectQuery.setParameter("generateItem", generateItem);
			if (!isIgnoreBranch) {
				selectQuery.setParameter("branchCode", branchCode);
			}

			idGen = (IDGen) selectQuery.getSingleResult();

			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			em.flush();
		} catch (NoResultException e) {
			throw translate("There is no Result " + generateItem, e);
		} catch (PersistenceException pe) {
			throw translate("Failed to update ID Generation for " + generateItem, pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getNextId(String generateItem) throws DAOException {
		IDGen idGen = null;
		String branchCode = "";
		try {
			Query selectQuery = em.createQuery(
					"SELECT g FROM IDGen g WHERE g.generateItem = :generateItem AND g.branch.id IN " + " (SELECT b.id FROM Branch b Where b.branchCode = :branchCode)");
			selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			selectQuery.setHint("javax.persistence.lock.timeout", 30000);
			// TODO FIXME PSH
			// if (configLoader.isCentralizedSystem()) {
			// branchCode =
			// userProcessService.getLoginUser().getBranch().getBranchCode();
			// } else {
			// branchCode = configLoader.getBranchCode();
			// }
			// TODO FIXME PSH Change Branch Code
			branchCode = "001";
			selectQuery.setParameter("generateItem", generateItem);
			selectQuery.setParameter("branchCode", branchCode);
			idGen = (IDGen) selectQuery.getSingleResult();
			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ID Generation for " + generateItem, pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getNextId(String generateItem, Branch branch) throws DAOException {
		IDGen idGen = null;
		try {
			Query selectQuery = em
					.createQuery("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem AND g.branch.id IN (SELECT b.id FROM Branch b Where b.branchCode = :branchCode)");
			selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			selectQuery.setHint("javax.persistence.lock.timeout", 30000);
			selectQuery.setParameter("generateItem", generateItem);
			selectQuery.setParameter("branchCode", branch.getBranchCode());
			idGen = (IDGen) selectQuery.getSingleResult();
			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update ID Generation for " + generateItem, pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getIDGen(String generateItem) throws DAOException {
		IDGen idGen = null;
		try {
			Query selectQuery = em
					.createQuery("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem AND g.branch.id IN (SELECT b.id FROM Branch b Where b.branchCode = :branchCode)");
			selectQuery.setParameter("generateItem", generateItem);
			// TODO FIXME PSH Branch code will be generate from idconfig
			selectQuery.setParameter("branchCode", "001");
			idGen = (IDGen) selectQuery.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Max Value for " + generateItem, pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getIDGenForAutoRenewal(String generateItem) throws DAOException {
		IDGen idGen = null;
		try {
			Query selectQuery = em.createQuery("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem");
			selectQuery.setParameter("generateItem", generateItem);
			idGen = (IDGen) selectQuery.getSingleResult();
			idGen.setMaxValue(idGen.getMaxValue() + 1);
			idGen = em.merge(idGen);
			em.flush();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Max Value for " + generateItem, pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen updateIDGen(IDGen idGen) throws DAOException {
		try {
			idGen = em.merge(idGen);
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to update IDGen ", pe);
		}
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getCustomNextNo(String generateItem, String productId) {
		IDGen idGen = null;
		StringBuffer queryBffer = new StringBuffer();
		queryBffer.append("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem ");
		if (productId != null) {
			queryBffer.append("AND :product MEMBER OF g.productList ");
		}
		Query selectQuery = em.createQuery(queryBffer.toString());
		selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		selectQuery.setHint("javax.persistence.lock.timeout", 30000);
		selectQuery.setParameter("generateItem", generateItem);
		if (productId != null) {
			selectQuery.setParameter("product", productId);
		}
		idGen = (IDGen) selectQuery.getSingleResult();
		idGen.setMaxValue(idGen.getMaxValue() + 1);
		idGen = em.merge(idGen);
		em.flush();
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getCustomNextNoByBranchId(String generateItem, String branchId) {
		IDGen idGen = null;
		StringBuffer queryBffer = new StringBuffer();
		queryBffer.append("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem ");
		if (branchId != null) {
			queryBffer.append("AND g.branch.id=:branchId ");
		}
		Query selectQuery = em.createQuery(queryBffer.toString());
		selectQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		selectQuery.setHint("javax.persistence.lock.timeout", 30000);
		selectQuery.setParameter("generateItem", generateItem);
		if (branchId != null) {
			selectQuery.setParameter("branchId", branchId);
		}
		idGen = (IDGen) selectQuery.getSingleResult();
		idGen.setMaxValue(idGen.getMaxValue() + 1);
		idGen = em.merge(idGen);
		em.flush();
		return idGen;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public IDGen getIDGen(String generateItem, boolean isIgnoreBranch) throws DAOException {
		IDGen idGen = null;
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT g FROM IDGen g WHERE g.generateItem = :generateItem");

			// if (!isIgnoreBranch) {
			// query.append(" AND g.branch.id IN (SELECT b.id FROM Branch b
			// Where b.branchCode = :branchCode)");
			// } else {
			// query.append(" AND g.branch IS NULL ");
			// }
			query.append(" AND g.branch IS NULL ");
			Query selectQuery = em.createQuery(query.toString());
			selectQuery.setParameter("generateItem", generateItem);
			// if (!isIgnoreBranch) {
			// selectQuery.setParameter("branchCode",
			// configLoader.getBranchCode());
			// }
			idGen = (IDGen) selectQuery.getSingleResult();
			em.flush();
		} catch (PersistenceException pe) {
			throw translate("Failed to find Max Value for " + generateItem, pe);
		}
		return idGen;
	}

	@Override
	public IDGen getNextId(String generateItem, Branch branch, boolean isIgnoreBranch) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
}
