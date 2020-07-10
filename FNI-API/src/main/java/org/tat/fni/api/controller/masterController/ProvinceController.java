package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Province;
import org.tat.fni.api.domain.services.ProvinceService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.ProvinceDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/province")
@Api(tags = "Provinces")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;

	@GetMapping("/province")
	@ApiOperation(value = "${ProvinceController.provinces}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> provinces() {

		List<Province> resultList = new ArrayList<Province>();

		resultList = provinceService.findAll();

		List<ProvinceDTO> provinceList = new ArrayList<ProvinceDTO>();

		resultList.forEach(result -> {
			ProvinceDTO provinceDTO = ProvinceDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).code(result.getCode()).build();
			provinceList.add(provinceDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(provinceList)
				.build();

		return responseDTO;

	}

}
