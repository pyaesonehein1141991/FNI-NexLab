package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Bank;
import org.tat.fni.api.domain.services.BankService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.BankDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/bank")
@Api(tags = "Banks")
public class BankController {

	@Autowired
	private BankService bankService;

	@GetMapping("/banks")
	@ApiOperation(value = "${BankController.banks}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> banks() {

		List<Bank> resultList = new ArrayList<Bank>();

		resultList = bankService.findAll();

		List<BankDTO> bankList = new ArrayList<BankDTO>();

		resultList.forEach(result -> {
			BankDTO bankDTO = BankDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			bankList.add(bankDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(bankList).build();

		return responseDTO;

	}
}
