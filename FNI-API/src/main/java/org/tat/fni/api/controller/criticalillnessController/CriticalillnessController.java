package org.tat.fni.api.controller.criticalillnessController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.services.CriticalillnessProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.IndividualCriticalIllnessDTO;
import org.tat.fni.api.dto.proposalResponseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/criticalillness")
@Api(tags = "criticalillness")
public class CriticalillnessController {

	@Autowired
	private CriticalillnessProposalService medicalProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${CriticalillnessController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit CriticalillnessController Proposal") @Valid @RequestBody IndividualCriticalIllnessDTO criticalillnessDTO) {

		List<MedicalProposal> proposallist = new ArrayList<>();
		IndividualCriticalIllnessDTO dto = mapper.map(criticalillnessDTO, IndividualCriticalIllnessDTO.class);

		// create Individual Critical illness proposal
		proposallist = medicalProposalService.criticalillnessDTOToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO individualCriticalIllnessResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getTotalPremium()).build();
			responseList.add(individualCriticalIllnessResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}

}
