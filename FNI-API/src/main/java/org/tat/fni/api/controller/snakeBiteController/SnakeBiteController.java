package org.tat.fni.api.controller.snakeBiteController;

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
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/snakeBite")
@Api(tags = "Snake Bite")
public class SnakeBiteController {

	@Resource(name = "snakeBiteProposalService")
	private ILifeProductsProposalService snakeBiteProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${SnakeBiteController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit Snake Bite Life Proposal") @Valid @RequestBody SnakeBiteDTO snakeBiteDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		SnakeBiteDTO dto = mapper.map(snakeBiteDTO, SnakeBiteDTO.class);

		// create snake bite proposal
		proposallist = snakeBiteProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO snakeBiteResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getProposedPremium()).build();
			responseList.add(snakeBiteResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;

	}

}
