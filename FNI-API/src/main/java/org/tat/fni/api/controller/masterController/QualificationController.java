package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Qualification;
import org.tat.fni.api.domain.services.QualificationService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.QualificationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/qualification")
@Api(tags = "Qualifications")
public class QualificationController {

	@Autowired
	private QualificationService qualificationService;

	@GetMapping("/qualifications")
	@ApiOperation(value = "${QualificationController.qualifications}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> qualifications() {

		List<Qualification> resultList = new ArrayList<Qualification>();

		resultList = qualificationService.findAll();

		List<QualificationDTO> qualificationList = new ArrayList<QualificationDTO>();

		resultList.forEach(result -> {
			QualificationDTO qualificationDTO = QualificationDTO.builder().id(result.getId()).name(result.getName())
					.description(result.getDescription()).build();
			qualificationList.add(qualificationDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(qualificationList)
				.build();

		return responseDTO;
	}

}
