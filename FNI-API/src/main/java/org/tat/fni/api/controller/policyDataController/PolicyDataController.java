package org.tat.fni.api.controller.policyDataController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.services.Interfaces.IPolicyService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.policyDataDTO.PolicyDataCriteria;
import org.tat.fni.api.dto.responseDTO.policyResponse.ResponseDataDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/policyData")
@Api(tags = "Policy Data")
public class PolicyDataController {
	
	@Autowired
	private IPolicyService policyService;
	
	@PostMapping("/policyData")
	@ApiResponses(value = { //
			@ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired or invalid JWT token") })
	@ApiOperation(value = "${PolicyDataController.getPolicyData}")
	public <T> ResponseDTO<Object> retrievePolicyInfo(
			@ApiParam("Proposal Number") @Valid @RequestBody PolicyDataCriteria policyDto) {

		List<T> responseList = new ArrayList<T>();

		// Get response data list of policy information
		responseList = policyService.getResponseData(policyDto);

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success!").responseBody(responseList).build();
		return responseDTO;
	}

}
