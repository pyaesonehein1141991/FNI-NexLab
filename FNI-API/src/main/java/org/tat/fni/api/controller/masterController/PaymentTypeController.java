package org.tat.fni.api.controller.masterController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tat.fni.api.domain.PaymentType;
import org.tat.fni.api.domain.services.PaymentTypeService;
import org.tat.fni.api.dto.ResponseDTO;
import org.tat.fni.api.dto.masterDTO.PaymentTypeDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/paymentType")
@Api(tags = "Payment Types")
public class PaymentTypeController {
	
	@Autowired
	private PaymentTypeService paymentTypeService;

	@GetMapping("/paymentTypes")
	@ApiOperation(value = "${PaymentTypeController.paymentTypes}")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Something went wrong"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 500, message = "Expired for invalid JWT token") })
	public ResponseDTO<Object> paymentTypes() {

		List<PaymentType> resultList = new ArrayList<PaymentType>();

		resultList = paymentTypeService.findAll();

		List<PaymentTypeDTO> paymentTypeList = new ArrayList<PaymentTypeDTO>();

		resultList.forEach(result -> {
			PaymentTypeDTO paymentTypeDTO = PaymentTypeDTO.builder().id(result.getId()).name(result.getName())
					.month(result.getMonth()).description(result.getDescription()).build();
			paymentTypeList.add(paymentTypeDTO);
		});

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status("Success").responseBody(paymentTypeList)
				.build();

		return responseDTO;

	}

}
