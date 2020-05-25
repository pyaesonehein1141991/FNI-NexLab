package org.tat.fni.api.controller.farmerController;

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
import org.tat.fni.api.domain.services.FarmerLifeProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerResponseDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermEndowmentLifeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/farmer")
@Api(tags = "Farmer")
public class FarmerController {
	
	@Autowired
	private FarmerLifeProposalService farmerLifeProposalService;
	
	 @Autowired
	 private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${FarmerController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@Valid @RequestBody FarmerProposalDTO farmerProposalDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		FarmerProposalDTO a =
		        mapper.map(farmerProposalDTO, FarmerProposalDTO.class);

		proposallist = farmerLifeProposalService.createFarmerProposalDTOToProposal(farmerProposalDTO);

		List<FarmerResponseDTO> responseList = new ArrayList<FarmerResponseDTO>();

		proposallist.forEach(proposal -> {
			FarmerResponseDTO dto = FarmerResponseDTO.builder()
					.proposalID(proposal.getId())
					.proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getProposedPremium())
					.build();
			responseList.add(dto);
		});
		
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
