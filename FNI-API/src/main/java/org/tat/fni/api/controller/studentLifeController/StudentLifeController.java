package org.tat.fni.api.controller.studentLifeController;

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
import org.tat.fni.api.domain.services.PolicyDataService.LifePolicyService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.policyDataDTO.PolicyDataCriteria;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifeDTO;
import org.tat.fni.api.dto.studentLifeDTO.StudentLifePolicyDataDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/studentlife")
@Api(tags = "Student-Life")
public class StudentLifeController {

	@Resource(name = "studentLifeProposalService")
	private ILifeProductsProposalService studentLifeProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${StudentLifeController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit Student Life Proposal") @Valid @RequestBody StudentLifeDTO studentLifeDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		StudentLifeDTO dto = mapper.map(studentLifeDTO, StudentLifeDTO.class);

		// create student life proposal
		proposallist = studentLifeProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO studentLifeResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getProposedPremium()).build();
			responseList.add(studentLifeResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
