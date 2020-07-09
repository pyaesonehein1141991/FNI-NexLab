package org.tat.fni.api.controller.healthController;

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
import org.tat.fni.api.dto.healthInsuranceDTO.GroupHealthInsuranceDTO;
import org.tat.fni.api.dto.healthInsuranceDTO.IndividualHealthInsuranceDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/group-health")
@Api(tags = "Group Health")
public class GroupHealthController {
	
	@Resource(name = "healthProposalService")
	private IMedicalProductsProposalService medicalProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submit-proposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${GroupHealthController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit IndividualHealth Proposal") @Valid @RequestBody GroupHealthInsuranceDTO groupHealthInsuranceDTO) {

		List<MedicalProposal> proposallist = new ArrayList<>();
		IndividualHealthInsuranceDTO dto = mapper.map(groupHealthInsuranceDTO, IndividualHealthInsuranceDTO.class);

		// create individual health proposal
		proposallist = medicalProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO individualHealthResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getTotalPremium()).build();
			responseList.add(individualHealthResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}

}
