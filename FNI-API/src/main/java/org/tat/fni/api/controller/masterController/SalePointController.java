package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.SalesPoints;
import org.tat.fni.api.domain.services.SalePointService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.SalePointDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/salepoint")
@Api(tags = "SalePoints")
public class SalePointController {
	
	@Autowired
	private SalePointService salepointService;

	@GetMapping("/salepoints")
	@ApiOperation(value = "${SalePointController.salepoints}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> salepoints() {

		List<SalesPoints> resultList = new ArrayList<SalesPoints>();

		resultList = salepointService.findAll();

		List<SalePointDTO> salepointList = new ArrayList<SalePointDTO>();

		resultList.forEach(result -> {
			SalePointDTO salepointDTO = SalePointDTO.builder().id(result.getId()).name(result.getName())
					.code(result.getCode()).description(result.getDescription()).build();
			salepointList.add(salepointDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(salepointList)
				.build();

		return responseDTO;

	}

}
