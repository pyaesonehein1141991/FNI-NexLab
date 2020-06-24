package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Hospital;
import org.tat.fni.api.domain.services.HospitalService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.HospitalDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/hospital")
@Api(tags = "Hospitals")
public class HospitalController {
	
	@Autowired
	private HospitalService hospitalService;

	@GetMapping("/hospitals")
	@ApiOperation(value = "${HospitalController.hospitals}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> hospitals() {

		List<Hospital> resultList = new ArrayList<Hospital>();

		resultList = hospitalService.findAll();

		List<HospitalDTO> hospitalList = new ArrayList<HospitalDTO>();

		resultList.forEach(result -> {
			HospitalDTO hospitalDTO = HospitalDTO.builder().id(result.getId()).name(result.getName())
					.phone(result.getPhone()).address(result.getAddress().getFullAddress()).build();
			hospitalList.add(hospitalDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(hospitalList).build();

		return responseDTO;

	}

}
