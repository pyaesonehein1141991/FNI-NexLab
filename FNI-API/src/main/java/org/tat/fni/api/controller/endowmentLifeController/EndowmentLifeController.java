package org.tat.fni.api.controller.endowmentLifeController;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.services.Interfaces.ILifeProductsProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.endowmentLifeDTO.EndowmentLifeDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/endowment-life")
@Api(tags = "Endowment Life")
public class EndowmentLifeController {
	
	@Resource(name = "endowmentLifeProposalService")
	private ILifeProductsProposalService endowmentLifeProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submit-proposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${EndowmentLifeController.submitproposal}")
	public ResponseDTO<Object> submitproposal(
			@ApiParam("Submit Endowment Life Proposal") @Valid @RequestBody EndowmentLifeDTO publicLifeDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		EndowmentLifeDTO dto = mapper.map(publicLifeDTO, EndowmentLifeDTO.class);

		// create Farmer proposal
		proposallist = endowmentLifeProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO farmerResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId())
					.proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getProposedPremium()).build();
			responseList.add(farmerResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
