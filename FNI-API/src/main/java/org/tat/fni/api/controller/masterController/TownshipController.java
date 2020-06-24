package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Township;
import org.tat.fni.api.domain.services.TownShipService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.TownshipDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/township")
@Api(tags = "Townships")
public class TownshipController {
	
	@Autowired
	private TownShipService townshipService;

	@GetMapping("/townships")
	@ApiOperation(value = "${TownshipController.townships}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> townships() {

		List<Township> resultList = new ArrayList<Township>();

		resultList = townshipService.findAll();

		List<TownshipDTO> townshipList = new ArrayList<TownshipDTO>();

		resultList.forEach(result -> {
			TownshipDTO townshipDTO = TownshipDTO.builder().id(result.getId()).name(result.getName())
					.code(result.getCode()).build();
			townshipList.add(townshipDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(townshipList)
				.build();

		return responseDTO;

	}

}
