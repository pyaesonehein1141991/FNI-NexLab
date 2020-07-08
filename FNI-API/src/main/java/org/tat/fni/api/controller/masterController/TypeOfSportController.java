package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.TypesOfSport;
import org.tat.fni.api.domain.services.TypesOfSportService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.TypeOfSportDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/typeofsport")
@Api(tags = "Type Of Sports")
public class TypeOfSportController {
	
	@Autowired
	private TypesOfSportService typesOfSportService;

	@GetMapping("/typeofsports")
	@ApiOperation(value = "${TypeOfSportController.typeOfSports}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> qualifications() {

		List<TypesOfSport> resultList = new ArrayList<TypesOfSport>();

		resultList = typesOfSportService.findAll();

		List<TypeOfSportDTO> qualificationList = new ArrayList<TypeOfSportDTO>();

		resultList.forEach(result -> {
			TypeOfSportDTO qualificationDTO = TypeOfSportDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			qualificationList.add(qualificationDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(qualificationList)
				.build();

		return responseDTO;
	}

}
