package org.tat.fni.api.controller.personalaccidentController;

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
import org.tat.fni.api.dto.personalAccidentDTO.PersonalAccidentDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/personal-accident")
@Api(tags = "Personal Accident")
public class PersonalAccidentController {

	@Resource(name = "personalaccidentProposalService")
	private ILifeProductsProposalService lifeProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submit-proposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${PersonalAccidentController.submitproposal}")
	public ResponseDTO<Object> submitproposal(
			@ApiParam("Submit PersonalAccident Proposal") @Valid @RequestBody PersonalAccidentDTO personalAccidentDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();

		PersonalAccidentDTO dto = mapper.map(personalAccidentDTO, PersonalAccidentDTO.class);

		// create PersonalAccident proposal
		proposallist = lifeProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO personalAccidentResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId())
					.proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getPremium()).build();
			responseList.add(personalAccidentResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}
}
