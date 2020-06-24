package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Branch;
import org.tat.fni.api.domain.services.BranchService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.BranchDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/branch")
@Api(tags = "Branches")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@GetMapping("/branches")
	@ApiOperation(value = "${BranchController.branches}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> branchs() {

		List<Branch> resultList = new ArrayList<Branch>();

		resultList = branchService.findAll();

		List<BranchDTO> branchList = new ArrayList<BranchDTO>();

		resultList.forEach(result -> {
			BranchDTO branchDTO = BranchDTO.builder().id(result.getId()).name(result.getName())
					.branchCode(result.getBranchCode()).address(result.getAddress()).build();
			branchList.add(branchDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(branchList).build();

		return responseDTO;

	}

}
