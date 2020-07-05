package org.tat.fni.api.controller.shortTermController;

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
import org.tat.fni.api.domain.services.Interfaces.IPolicyService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataLifeDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermEndowmentLifeDTO;
import org.tat.fni.api.dto.shortTermEndowmentLifeDTO.ShortTermPolicyDataDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/shortterm")
@Api(tags = "Short-Term Life Insurance")
public class ShortTermEndowmentLifeController {

	@Resource(name = "shortTermLifeProposalService")
	private ILifeProductsProposalService lifeProposalService;

	@Autowired
	private IPolicyService policyService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${ShortTermEndowmentLifeController.submitproposal}")
	public ResponseDTO<Object> submitproposal(
			@ApiParam("Submit ShortTerm Proposal") @Valid @RequestBody ShortTermEndowmentLifeDTO shortTermEndowmentLifeDto) {

		List<LifeProposal> proposallist = new ArrayList<>();

		ShortTermEndowmentLifeDTO dto = mapper.map(shortTermEndowmentLifeDto, ShortTermEndowmentLifeDTO.class);

		// create shortTermEndowmentlife proposal
		proposallist = lifeProposalService.createDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO shortTermEndowmentResponseDto = ProposalResponseDTO.builder()
					.proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getPremium()).build();

			responseList.add(shortTermEndowmentResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}

	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${ShortTermEndowmentLifeController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(
			@ApiParam("Proposal Number") @Valid @RequestBody ShortTermPolicyDataDTO policyDto) {

		List<ResponseDataLifeDTO> responseList = new ArrayList<ResponseDataLifeDTO>();

		PolicyDataCriteria dto = mapper.map(policyDto, PolicyDataCriteria.class);

		// Get response data list of policy information
		responseList = policyService.getResponseData(dto);

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
