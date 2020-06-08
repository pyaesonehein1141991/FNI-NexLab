package org.tat.fni.api.exception;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.tat.fni.api.dto.ResponseDTO;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(APIExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldError = ex.getBindingResult().getFieldErrors();

		List<String> details = new ArrayList<>();
		for (FieldError error : fieldError) {
			details.add(error.getDefaultMessage());
		}
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(status.toString()).message(generateMessage(fieldError)).responseBody(null).build();
		LOGGER.error(details.toString());
		return ResponseEntity.badRequest().body(responseDTO);

	}

	public String generateMessage(List<FieldError> fieldErrors) {
		String result = "";

		for (FieldError error : fieldErrors) {
			if (!result.isEmpty()) {
				result += ",";
			}
			result += error.getDefaultMessage();
		}
		return result;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(HttpStatus.BAD_REQUEST.toString()).message(ex.getMessage()).build();
		LOGGER.error(ex.getMessage());
		return ResponseEntity.badRequest().body(responseDTO);
	}
}
