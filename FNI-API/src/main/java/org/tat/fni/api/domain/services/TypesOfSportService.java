package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.TypesOfSport;
import org.tat.fni.api.domain.repository.TypeofSportRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class TypesOfSportService {

  @Autowired
  private TypeofSportRepository typeofsportRepository;

  public List<TypesOfSport> findAll() {
    return typeofsportRepository.findAll();
  }

  public List<Object[]> findAllNativeObject() {
    return typeofsportRepository.findAllNativeObject();
  }

  public List<Object> findAllColumnName() {
    return typeofsportRepository.findAllColumnName();
  }

  @Transactional
  public Optional<TypesOfSport> findById(String id) throws DAOException {
    if (!StringUtils.isBlank(id)) {
      if (typeofsportRepository.findById(id).isPresent()) {
        return typeofsportRepository.findById(id);
      } else {
        throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND,
            id + " not found in Occupation");
      }
    } else {
      return Optional.empty();
    }

  }


}
