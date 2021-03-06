package org.tat.fni.api.controller.microHealthController;

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
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.services.Interfaces.IMedicalProductsProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/microHealth")
@Api(tags = "Micro Health")
public class MicroHealthController {

	@Resource(name = "microHealthProposalService")
	private IMedicalProductsProposalService medicalProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${MicroHealthController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit MicroHealth Proposal") @Valid @RequestBody MicroHealthDTO microHealthDTO) {

		List<MedicalProposal> proposallist = new ArrayList<>();
		MicroHealthDTO dto = mapper.map(microHealthDTO, MicroHealthDTO.class);

		// create micro health proposal
		proposallist = medicalProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO microHealthResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getTotalPremium()).build();
			responseList.add(microHealthResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}

}
