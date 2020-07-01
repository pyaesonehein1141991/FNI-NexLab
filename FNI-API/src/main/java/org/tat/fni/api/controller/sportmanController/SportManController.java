package org.tat.fni.api.controller.sportmanController;

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
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataLifeDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;
import org.tat.fni.api.dto.sportManDTO.SportManDTO;
import org.tat.fni.api.dto.sportManDTO.SportManPolicyDataDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sportman")
@Api(tags = "Sport Man")
public class SportManController {

	@Resource(name = "sportManProposalService")
	private ILifeProductsProposalService lifeProposalService;

	@Autowired
	private LifePolicyService lifePolicyService;
	
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${SportManController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit SportMan Proposal") @Valid @RequestBody SportManDTO sportManDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();

		SportManDTO sportmandto = mapper.map(sportManDTO, SportManDTO.class);

		// create sport man proposal
		proposallist = lifeProposalService.createDtoToProposal(sportmandto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO dto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo()).proposedPremium(proposal.getPremium())
					.build();
			responseList.add(dto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}
	
	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), 
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${SportManController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(
			@ApiParam("Proposal Number") @Valid @RequestBody SportManPolicyDataDTO policyDto) {
		
		List<ResponseDataLifeDTO> responseList = new ArrayList<ResponseDataLifeDTO>();

		PolicyDataCriteria dto = mapper.map(policyDto, PolicyDataCriteria.class);

		// Get response data list of policy infomation
		responseList = lifePolicyService.getResponseData(dto);

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
