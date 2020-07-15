package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.InsuredPersonBeneficiaries;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.dto.endowmentLifeDTO.EndowmentLifeProposalInsuredPersonDTO;

public interface ILifeProductsProposalService {

	public <T> List<LifeProposal> createDtoToProposal(T proposalDto);

	public <T> List<LifeProposal> convertProposalDTOToProposal(T proposalDto);

	public <T> ProposalInsuredPerson createInsuredPerson(T proposalInsuredPersonDTO);

	public <T> InsuredPersonBeneficiaries createInsuredPersonBeneficiareis(T insuredPersonBeneficiariesDto,
			ProposalInsuredPerson insuredPerson);

}
