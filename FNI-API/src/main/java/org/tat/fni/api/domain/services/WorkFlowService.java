package org.tat.fni.api.domain.services;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.WorkFlow;
import org.tat.fni.api.domain.WorkFlowDTO;
import org.tat.fni.api.domain.repository.IWorkFlowDAO;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.SystemException;

@Service("WorkFlowService")
public class WorkFlowService extends BaseService implements IWorkFlowService {

  @Resource(name = "WorkFlowDAO")
  private IWorkFlowDAO workFlowDAO;

  @Transactional(propagation = Propagation.REQUIRED)
  public void addNewWorkFlow(WorkFlowDTO workFlowDTO) {
    try {
      WorkFlow workflow = new WorkFlow(workFlowDTO);
      workFlowDAO.insert(workflow);

    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), "Faield to add a new WorkFlow", e);
    }
  }
}
