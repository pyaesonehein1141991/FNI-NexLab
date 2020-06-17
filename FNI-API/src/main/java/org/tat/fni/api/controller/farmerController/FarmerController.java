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
import org.tat.fni.api.domain.services.LifePolicyService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.farmerDTO.FarmerPolicyDataDto;
import org.tat.fni.api.dto.farmerDTO.FarmerProposalDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;

@RestController
@RequestMapping("/farmer")
@Api(tags = "farmer")
public class FarmerController {

	@Autowired
	private FarmerLifeProposalService farmerLifeProposalService;

	@Autowired
	private LifePolicyService lifePolicyService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${FarmerController.submitproposal}")
	public ResponseDTO<Object> submitproposal(
			@ApiParam("Submit Farmer Proposal") @Valid @RequestBody FarmerProposalDTO farmerProposalDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();
		FarmerProposalDTO dto = mapper.map(farmerProposalDTO, FarmerProposalDTO.class);

		// create Farmer proposal
		proposallist = farmerLifeProposalService.createFarmerProposalDTOToProposal(dto);

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

	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${FarmerController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(
			@ApiParam("Proposal Number") @Valid @RequestBody FarmerPolicyDataDto policyDto) {

		List<ResponseDataDTO> responseList = new ArrayList<ResponseDataDTO>();

		PolicyDataCriteria dto = mapper.map(policyDto, PolicyDataCriteria.class);

		// Get response data list of policy infomation
		responseList = lifePolicyService.getResponseData(dto);

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
