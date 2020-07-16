package org.tat.fni.api.domain.services.Interfaces;

import java.util.List;

import org.tat.fni.api.common.KeyFactor;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.InsuredPersonKeyFactorValue;
import org.tat.fni.api.domain.MedicalKeyFactorValue;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.MedicalProposalInsuredPerson;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonAddOn;
import org.tat.fni.api.domain.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.fni.api.domain.ProposalInsuredPerson;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.dto.InsuredPersonAddOnDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.CriticalillnessProposalInsuredPersonBeneficiariesDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.IndividualCriticalIllnessDTO;
import org.tat.fni.api.dto.customerDTO.CustomerDto;

public interface IMedicalProductsProposalService {

	public <T> List<MedicalProposal> createDtoToProposal(T proposalDto);

	public <T> List<MedicalProposal> convertIndividualProposalDTOToProposal(T proposalDto);

	public <T> List<MedicalProposal> convertGroupProposalDTOToProposal(T proposalDto);

	public <T> MedicalProposalInsuredPerson createInsuredPerson(T proposalInsuredPersonDTO, T proposalDto);

	public <T> MedicalProposalInsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
			T insuredPersonBeneficiariesDto);

	public MedicalProposalInsuredPersonAddOn createInsuredPersonAddon(InsuredPersonAddOnDTO addOnDTO,
			MedicalProposalInsuredPerson insuredPerson);
	
	public <T> CustomerDto getCustomerFromInsuredPerson(T proposalInsuredPersonDTO);

}
