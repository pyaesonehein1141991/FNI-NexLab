package org.tat.fni.api.domain.repository;

import org.tat.fni.api.domain.WorkFlow;
import org.tat.fni.api.exception.DAOException;

public interface IWorkFlowDAO {

  public void insert(WorkFlow workflow) throws DAOException;
}
