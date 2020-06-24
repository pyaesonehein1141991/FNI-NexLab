package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.GradeInfo;
import org.tat.fni.api.domain.services.GradeInfoService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.GradeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/grade")
@Api(tags = "Grades")
public class GradeController {

	@Autowired
	private GradeInfoService gradeInfoService;

	@GetMapping("/grades")
	@ApiOperation(value = "${GradeController.grades}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> grades() {

		List<GradeInfo> resultList = new ArrayList<GradeInfo>();

		resultList = gradeInfoService.findAll();

		List<GradeDTO> gradeList = new ArrayList<GradeDTO>();

		resultList.forEach(result -> {
			GradeDTO gradeDTO = GradeDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			gradeList.add(gradeDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(gradeList).build();

		return responseDTO;

	}

}
