package org.tat.fni.api.controller.groupLifeController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.services.GroupLifeProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.groupLifeDTO.GroupLifeDTO;
import org.tat.fni.api.dto.proposalResponseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/groupLife")
@Api(tags = "groupLife")
public class GroupLifeController {

	@Autowired
	private GroupLifeProposalService groupLifeProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${GroupLifeController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit Group Life Proposal") @Valid @RequestBody GroupLifeDTO groupLifeDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		GroupLifeDTO dto = mapper.map(groupLifeDTO, GroupLifeDTO.class);

		// create group life proposal
		proposallist = groupLifeProposalService.createGroupLifeProposalDTOToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO groupLifeResponsedto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getProposedPremium()).build();
			responseList.add(groupLifeResponsedto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
