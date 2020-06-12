package org.tat.fni.api.controller.personalaccidentController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.lifepolicy.LifePolicy;
import org.tat.fni.api.domain.lifeproposal.LifeProposal;
import org.tat.fni.api.domain.services.PersonalaccidentProposalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.personalAccidentDTO.PersonalAccidentDTO;
import org.tat.fni.api.dto.proposalDTO.ProposalLifeDTO;
import org.tat.fni.api.dto.responseDTO.PolicyInformationResponseDTO;
import org.tat.fni.api.dto.responseDTO.ProposalResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/personalAccident")
@Api(tags = "personalAccident")
public class PersonalAccidentController {

	@Autowired
	private PersonalaccidentProposalService lifeProposalService;

	@Autowired
	private ModelMapper mapper;

	@PostMapping("/submitproposal")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"), @ApiResponse(code = 403, message = "Access denied"),
	@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${PersonalAccidentController.submitproposal}")
	public ResponseDTO<Object> submitproposal(@ApiParam("Submit PersonalAccident Proposal") @Valid @RequestBody PersonalAccidentDTO personalAccidentDTO) {

		List<LifeProposal> proposallist = new ArrayList<>();

		PersonalAccidentDTO dto = mapper.map(personalAccidentDTO, PersonalAccidentDTO.class);

		// create PersonalAccident proposal
		proposallist = lifeProposalService.createPersonalAccidentDtoToProposal(dto);

		// create response object
		List<ProposalResponseDTO> responseList = new ArrayList<ProposalResponseDTO>();

		proposallist.forEach(proposal -> {
			ProposalResponseDTO personalAccidentResponseDto = ProposalResponseDTO.builder().proposalID(proposal.getId()).proposalNo(proposal.getProposalNo())
					.proposedPremium(proposal.getPremium()).build();
			responseList.add(personalAccidentResponseDto);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();

		return responseDTO;
	}
	
	@PostMapping("/policyinfo")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"), 
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${PersonalAccidentController.getpolicyinfobyproposalno}")
	public ResponseDTO<Object> retrievePolicyInfo(@ApiParam("Proposal Number") @Valid @RequestBody ProposalLifeDTO proposalDto) {
		
		List<LifePolicy> policylist = new ArrayList<>();
		
		ProposalLifeDTO dto = mapper.map(proposalDto, ProposalLifeDTO.class);
		
		policylist = lifeProposalService.retrievePolicyInfo(dto);
		
		// create response object
		List<PolicyInformationResponseDTO> responseList = new ArrayList<PolicyInformationResponseDTO>();
		
		if(!policylist.isEmpty()) {
			//Added temporary data
			policylist.forEach(policy -> {
				PolicyInformationResponseDTO policyInfoResponseDto = PolicyInformationResponseDTO.builder()
						.policyStatus(policy.getPolicyStatus())
						.premium(policy.getPremium())
						.startDate(policy.getActivedPolicyStartDate())
						.endDate(policy.getActivedPolicyEndDate())
						.paymentTimes(policy.getPaymentTimes())
						.oneYearPremium(policy.getNetPremium())
						.termPremium(policy.getTotalBasicTermPremium())
						.totalTerm(policy.getTotalTermPremium()).build();
				responseList.add(policyInfoResponseDto);
			});
		}
		
		
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
