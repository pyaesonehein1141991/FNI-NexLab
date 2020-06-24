package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.School;
import org.tat.fni.api.domain.services.SchoolService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.SchoolDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/school")
@Api(tags = "Schools")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;

	@GetMapping("/schools")
	@ApiOperation(value = "${SchoolController.schools}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> schools() {

		List<School> resultList = new ArrayList<School>();

		resultList = schoolService.findAll();

		List<SchoolDTO> schoolList = new ArrayList<SchoolDTO>();

		resultList.forEach(result -> {
			SchoolDTO schoolDTO = SchoolDTO.builder().id(result.getId()).name(result.getName())
					.schoolType(result.getSchoolType()).schoolLevelType(result.getSchoolLevelType())
					.phoneNo(result.getPhoneNo()).schoolCodeNo(result.getSchoolCodeNo()).build();
			schoolList.add(schoolDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(schoolList)
				.build();

		return responseDTO;

	}

}
