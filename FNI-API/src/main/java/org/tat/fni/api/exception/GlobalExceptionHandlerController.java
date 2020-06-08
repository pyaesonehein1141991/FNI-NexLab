package org.tat.fni.api.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.tat.fni.api.dto.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandlerController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public ErrorAttributes errorAttributes() {
		// Hide exception field in the return object
		return new DefaultErrorAttributes() {
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest requestAttributes, boolean includeStackTrace) {
				Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
				errorAttributes.remove("exception");
				logger.error(errorAttributes.toString());
				return errorAttributes;
			}
		};
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
		logger.error("handleCustomException: " + ex.getMessage());
		res.sendError(ex.getHttpStatus().value(), ex.getMessage());
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(ex.getHttpStatus().toString()).message(ex.getMessage()).build();
		return ResponseEntity.badRequest().body(responseDTO);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(HttpServletResponse res) throws IOException {
		logger.error("Access Denied");
		// res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(HttpStatus.FORBIDDEN.toString()).message("Access denied").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(HttpServletResponse res) throws IOException {
		logger.error("IOEXception :");
		// res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went
		// wrong");
		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(HttpStatus.BAD_REQUEST.toString()).message("Something went wrong").build();
		return ResponseEntity.badRequest().body(responseDTO);
	}

	// TODO FIXME PSH ADD Error code
	@ExceptionHandler(SystemException.class)
	public ResponseEntity<Object> handleDAOException(HttpServletResponse res, SystemException e) throws IOException {
		logger.error("SystemException :");
		HttpStatus status = null;
		String message = null;

		// if
		// (ErrorCode.SYSTEM_ERROR_RESOURCE_NOT_FOUND.equals(e.getErrorCode()))
		// {
		//// res.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		// status = HttpStatus.NOT_FOUND;
		// message = e.getMessage();
		// }
		// else if (ErrorCode.NRC_FORMAT_NOT_MATCH.equals(e.getErrorCode())) {
		//// res.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		// status = HttpStatus.BAD_REQUEST;
		// message = e.getMessage();
		// }
		// else if
		// (ErrorCode.PAYMENT_ALREADY_CONFIRMED.equals(e.getErrorCode())) {
		//// res.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		// status = HttpStatus.BAD_REQUEST;
		// message = e.getMessage();
		// }
		// else {
		//// res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went
		// wrong");
		// status = HttpStatus.BAD_REQUEST;
		// message = "Something went wrong";
		// }

		ResponseDTO<Object> responseDTO = ResponseDTO.builder().status(status.toString()).message(message).build();
		return ResponseEntity.status(status).body(responseDTO);
	}
}
