package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.RelationShip;
import org.tat.fni.api.domain.services.RelationshipService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.RelationshipDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/relationship")
@Api(tags = "Relationships")
public class RelationshipController {
	
	@Autowired
	private RelationshipService relationshipService;

	@GetMapping("/relationship")
	@ApiOperation(value = "${RelationshipController.relationships}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> relationships() {

		List<RelationShip> resultList = new ArrayList<RelationShip>();

		resultList = relationshipService.findAll();

		List<RelationshipDTO> relationshipList = new ArrayList<RelationshipDTO>();

		resultList.forEach(result -> {
			RelationshipDTO relationshipDTO = RelationshipDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			relationshipList.add(relationshipDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(relationshipList)
				.build();

		return responseDTO;

	}

}
