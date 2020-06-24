package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Occupation;
import org.tat.fni.api.domain.services.OccupationService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.OccupationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/occupation")
@Api(tags = "Occupations")
public class OccupationController {

	@Autowired
	private OccupationService occupationService;

	@GetMapping("/occupations")
	@ApiOperation(value = "${OccupationController.occupations}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> occupations() {

		List<Occupation> resultList = new ArrayList<Occupation>();

		resultList = occupationService.findAll();

		List<OccupationDTO> occupationList = new ArrayList<OccupationDTO>();

		resultList.forEach(result -> {
			OccupationDTO occupationDTO = OccupationDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			occupationList.add(occupationDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(occupationList).build();

		return responseDTO;

	}
	
}
