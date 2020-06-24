package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.Customer;
import org.tat.fni.api.domain.services.CustomerService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.CustomerDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customer")
@Api(tags = "Customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/Customers")
	@ApiOperation(value = "${CustomerController.customers}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> customers() {

		List<Customer> resultList = new ArrayList<Customer>();

		resultList = customerService.findAll();

		List<CustomerDTO> customerList = new ArrayList<CustomerDTO>();

		resultList.forEach(result -> {
			CustomerDTO customerDTO = CustomerDTO.builder().id(result.getId()).dateOfBirth(result.getDateOfBirth())
					.fatherName(result.getFatherName()).fullIdNo(result.getFullIdNo()).gender(result.getGender())
					.idType(result.getIdType()).initialId(result.getInitialId()).marialStatus(result.getMaritalStatus())
					.mobile(result.getPhone()).firstName(result.getName().getFirstName())
					.middleName(result.getName().getMiddleName()).lastName(result.getName().getLastName())
					.address(result.getResidentAddress().getFullResidentAddress()).build();
			customerList.add(customerDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(customerList).build();

		return responseDTO;

	}

}
