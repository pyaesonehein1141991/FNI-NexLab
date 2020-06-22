package org.tat.fni.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.SaleMan;
import org.tat.fni.api.domain.services.SaleManService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.SaleManDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/saleman")
@Api(tags = "SaleMan")
public class SaleManController {

	@Autowired
	private SaleManService salemanService;

	@GetMapping("/salemans")
	@ApiOperation(value = "${SalemanController.banks}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> agents() {

		List<SaleMan> resultList = new ArrayList<SaleMan>();

		resultList = salemanService.findAll();

		List<SaleManDTO> salemanList = new ArrayList<SaleManDTO>();

		resultList.forEach(result -> {
			SaleManDTO salemanDTO = SaleManDTO.builder().build();
			salemanList.add(salemanDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(salemanList).build();

		return responseDTO;

	}

}
