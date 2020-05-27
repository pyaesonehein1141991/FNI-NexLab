package org.tat.fni.api.controller.snakeBiteController;

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
import org.tat.fni.api.domain.services.SnakeBiteProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerResponseDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteDTO;
import org.tat.fni.api.dto.snakeBiteDTO.SnakeBiteResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/snakeBite")
@Api(tags = "Snake Bite Proposal")
public class snakeBiteController {
	
	@Autowired
	SnakeBiteProposalService snakeBiteProposalService;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
	@ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${SnakeBiteController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit Snake Bite Life Proposal") 
	@Valid @RequestBody SnakeBiteDTO snakeBiteDTO) {
		
		List<LifeProposal> proposallist = new ArrayList<>();
		SnakeBiteDTO dto = mapper.map(snakeBiteDTO, SnakeBiteDTO.class);

		proposallist = snakeBiteProposalService.createSnakeBiteProposalDTOToProposal(dto);

		List<SnakeBiteResponseDTO> responseList = new ArrayList<SnakeBiteResponseDTO>();

		proposallist.forEach(proposal -> {
			SnakeBiteResponseDTO snakeBiteResponseDto = SnakeBiteResponseDTO.builder().proposalID(proposal.getId())
					.proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getProposedPremium()).build();
			responseList.add(snakeBiteResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
		
	}

}
