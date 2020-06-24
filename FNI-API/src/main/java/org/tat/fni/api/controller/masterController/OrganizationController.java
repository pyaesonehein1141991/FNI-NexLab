package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Organization;
import org.tat.fni.api.domain.services.OrganizationService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.OrganizationDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/organization")
@Api(tags = "Organizations")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@GetMapping("/organizations")
	@ApiOperation(value = "${OrganizationController.organizations}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> organizations() {

		List<Organization> resultList = new ArrayList<Organization>();

		resultList = organizationService.findAll();

		List<OrganizationDTO> organizationList = new ArrayList<OrganizationDTO>();

		resultList.forEach(result -> {
			OrganizationDTO organizationDTO = OrganizationDTO.builder().id(result.getId()).name(result.getName())
					.phone(result.getPhone()).ownerName(result.getOwnerName())
					.address(result.getAddress().getFullAddress()).build();
			organizationList.add(organizationDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(organizationList)
				.build();

		return responseDTO;

	}

}
