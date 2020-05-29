package org.tat.fni.api.domain.repository;

import javax.persistence.PersistenceException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.TaskMessage;
import org.tat.fni.api.domain.WorkFlow;
import org.tat.fni.api.exception.DAOException;

@Repository("WorkFlowDAO")
public class WorkFlowDAO extends BasicDAO implements IWorkFlowDAO {

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void insert(WorkFlow workflow) throws DAOException {
    try {
      em.persist(workflow);
      em.persist(new TaskMessage(workflow));
      em.flush();
    } catch (PersistenceException pe) {
      throw translate("Failed to insert WorkFlow", pe);
    }
  }


}
