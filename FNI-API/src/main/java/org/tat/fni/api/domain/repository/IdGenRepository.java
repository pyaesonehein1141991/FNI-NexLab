package org.tat.fni.api.domain.repository;

import org.tat.fni.api.common.IDGen;

public interface IdGenRepository {
  public IDGen getNextId(String generatedItem, String branchID);

}
