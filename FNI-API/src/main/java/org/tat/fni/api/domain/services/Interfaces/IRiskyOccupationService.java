package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;
import java.util.Optional;

import org.tat.fni.api.domain.RiskyOccupation;

public interface IRiskyOccupationService {

	public void addNewRiskyOccupation(RiskyOccupation riskyOccupation);

	public void updateRiskyOccupation(RiskyOccupation riskyOccupation);

	public void deleteRiskyOccupation(RiskyOccupation riskyOccupation);

	public Optional<RiskyOccupation> findRiskyOccupationById(String id);

	public List<RiskyOccupation> findAllRiskyOccupation();
}
