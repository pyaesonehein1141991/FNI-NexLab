package org.tat.fni.api.domain.services;

import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonGuardian;
import org.tat.fni.api.domain.repository.GuardianRepository;
import org.tat.fni.api.exception.DAOException;
import org.tat.fni.api.exception.ErrorCode;

@Service
public class GuardainService {
  @Autowired
  private GuardianRepository guardianRepository;

  public List<MedicalProposalInsuredPersonGuardian> findAll() {
    return guardianRepository.findAll();
  }

  public List<Object[]> findAllNativeObject() {
    return guardianRepository.findAllNativeObject();
  }

  public List<Object> findAllColumnName() {
    return guardianRepository.findAllColumnName();
  }

  @Transactional
  public Optional<MedicalProposalInsuredPersonGuardian> findById(String id) throws DAOException {
    if (!StringUtils.isBlank(id)) {
      if (guardianRepository.findById(id).isPresent()) {
        return guardianRepository.findById(id);
      } else {
        throw new DAOException(ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND,
            id + " not found in bank");
      }
    } else {
      return Optional.empty();
    }

  }


}
