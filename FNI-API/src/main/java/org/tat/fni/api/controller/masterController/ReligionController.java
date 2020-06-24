package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Religion;
import org.tat.fni.api.domain.services.ReligionService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.OccupationDTO;
import org.tat.fni.api.dto.masterDTO.ReligionDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/religion")
@Api(tags = "Religions")
public class ReligionController {
	
	@Autowired
	private ReligionService religionService;

	@GetMapping("/religions")
	@ApiOperation(value = "${ReligionController.religions}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> religions() {

		List<Religion> resultList = new ArrayList<Religion>();

		resultList = religionService.findAll();

		List<ReligionDTO> religionList = new ArrayList<ReligionDTO>();

		resultList.forEach(result -> {
			ReligionDTO religionDTO = ReligionDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			religionList.add(religionDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(religionList).build();

		return responseDTO;

	}

}
