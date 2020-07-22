package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.addon.AddOn;
import org.tat.fni.api.domain.services.AddOnService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.AddonDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/addon")
@Api(tags = "Addons")
public class AddonController {
	
	@Autowired
	private AddOnService addonService;

	@GetMapping("/addons")
	@ApiOperation(value = "${AddonController.addons}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> agents() {

		List<AddOn> resultList = new ArrayList<AddOn>();

		resultList = addonService.findAllAddOn();

		List<AddonDTO> agentList = new ArrayList<AddonDTO>();

		resultList.forEach(result -> {
			AddonDTO agentDTO = AddonDTO.builder().id(result.getId()).name(result.getName()).build();
			agentList.add(agentDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(agentList).build();

		return responseDTO;
	}

}
