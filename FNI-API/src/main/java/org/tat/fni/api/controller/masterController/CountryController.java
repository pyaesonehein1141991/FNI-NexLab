package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Country;
import org.tat.fni.api.domain.services.CountryService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.CountryDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/country")
@Api(tags = "Countrys")
public class CountryController {

	@Autowired
	private CountryService countryService;

	@GetMapping("/countrys")
	@ApiOperation(value = "${CountryController.countrys}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> countrys() {

		List<Country> resultList = new ArrayList<Country>();

		resultList = countryService.findAll();

		List<CountryDTO> countryList = new ArrayList<CountryDTO>();

		resultList.forEach(result -> {
			CountryDTO countryDTO = CountryDTO.builder().id(result.getId()).name(result.getName())
					.code(result.getCode()).description(result.getDescription()).build();
			countryList.add(countryDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(countryList).build();

		return responseDTO;

	}

}
