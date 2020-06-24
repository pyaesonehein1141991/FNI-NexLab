package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Agent;
import org.tat.fni.api.domain.services.AgentService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.AgentDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/agent")
@Api(tags = "Agents")
public class AgentController {

	@Autowired
	private AgentService agentService;

	@GetMapping("/agents")
	@ApiOperation(value = "${AgentController.agents}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> agents() {

		List<Agent> resultList = new ArrayList<Agent>();

		resultList = agentService.findAll();

		List<AgentDTO> agentList = new ArrayList<AgentDTO>();

		resultList.forEach(result -> {
			AgentDTO agentDTO = AgentDTO.builder().id(result.getId()).name(result.getName().getFirstName()).codeNo(result.getCodeNo())
					.fatherName(result.getFatherName()).gender(result.getGender()).idType(result.getIdType())
					.initialId(result.getInitialId()).joinDate(result.getAppointedDate()).build();
			agentList.add(agentDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(agentList).build();

		return responseDTO;
	}

}
