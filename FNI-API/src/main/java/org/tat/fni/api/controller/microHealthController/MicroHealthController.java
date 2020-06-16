package org.tat.fni.api.controller.microHealthController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.MedicalPolicy;
import org.tat.fni.api.domain.MedicalProposal;
import org.tat.fni.api.domain.services.MicroHealthProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.microHealthDTO.MicroHealthDTO;
import org.tat.fni.api.dto.proposalDTO.ProposalMedicalDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/microHealth")
@Api(tags = "microHealth")
public class MicroHealthController {

	@Autowired
	private MicroHealthProposalService medicalProposalService;

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
		proposallist = medicalProposalService.createMicroHealthDtoToProposal(dto);

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
	
	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), 
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${MicroHealthController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(@ApiParam("Proposal Number") @Valid @RequestBody ProposalMedicalDTO proposalDto) {
		
		List<MedicalPolicy> policylist = new ArrayList<>();
		
		ProposalMedicalDTO dto = mapper.map(proposalDto, ProposalMedicalDTO.class);
		
//		policylist = medicalProposalService.retrievePolicyInfo(dto);
		
		// create response object
//		List<PolicyInformationResponseDTO> responseList = new ArrayList<PolicyInformationResponseDTO>();
//		
//		if(!policylist.isEmpty()) {
//			//Added temporary data
//			policylist.forEach(policy -> {
//				PolicyInformationResponseDTO policyInfoResponseDto = PolicyInformationResponseDTO.builder()
//						.policyStatus(policy.getPolicyStatus())
//						.premium(policy.getPremium())
//						.startDate(policy.getActivedPolicyStartDate())
//						.endDate(policy.getActivedPolicyEndDate())
////						.paymentTimes(policy.get)
////						.oneYearPremium(policy.getNetPremium())
////						.termPremium(policy.getTotalBasicTermPremium())
//						.totalTerm(policy.getTotalTermPremium()).build();
//				responseList.add(policyInfoResponseDto);
//			});
//		}
//		
//		
//		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
//		return responseDTO;
		return null;
	}

}
