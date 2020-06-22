package org.tat.fni.api.controller.criticalillnessController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.services.CriticalillnessProposalService;
import org.tat.fni.api.domain.services.PolicyDataService.MedicalPolicyService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.CriticalIllnessPolicyDataDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.GroupCriticalIllnessDTO;
import org.tat.fni.api.dto.criticalIllnessDTO.IndividualCriticalIllnessDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataMedicalDTO;
import org.tat.fni.api.dto.retrieveDTO.policyData.PolicyDataCriteria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/groupCriticalillness")
@Api(tags = "Group Criticalillness")
public class GroupCriticalIllnessController {

	@Autowired
	private CriticalillnessProposalService medicalProposalService;

	@Autowired
	private MedicalPolicyService medicalPolicyService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${GroupCriticalIllnessController.submitproposal}")
	public ResponseDTO<Object> submitproposal(
			@ApiParam("Submit CriticalillnessController Proposal") @Valid @RequestBody GroupCriticalIllnessDTO criticalillnessDTO) {

		List<MedicalProposal> proposallist = new ArrayList<>();
		GroupCriticalIllnessDTO dto = mapper.map(criticalillnessDTO, GroupCriticalIllnessDTO.class);

		// create Individual Critical illness proposal
		proposallist = medicalProposalService.criticalillnessDTOToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO individualCriticalIllnessResponseDto = ProposalResponseDTO.builder()
					.proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getTotalPremium()).build();
			responseList.add(individualCriticalIllnessResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}

	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${CriticalillnessController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(
			@ApiParam("Proposal Number") @Valid @RequestBody CriticalIllnessPolicyDataDTO policyDto) {

		List<ResponseDataMedicalDTO> responseList = new ArrayList<ResponseDataMedicalDTO>();

		PolicyDataCriteria dto = mapper.map(policyDto, PolicyDataCriteria.class);

		// Get response data list of policy infomation
		responseList = medicalPolicyService.getResponseData(dto);

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;

	}

}
