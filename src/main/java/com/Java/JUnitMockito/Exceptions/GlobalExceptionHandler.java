package com.Java.JUnitMockito.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Java.JUnitMockito.DTO.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleValidationErrors(
			MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		ResponseDto<Map<String, List<String>>> responseDto = new ResponseDto.Builder<Map<String, List<String>>>()
				.status(String.valueOf(HttpStatus.BAD_REQUEST.value())).message(HttpStatus.BAD_REQUEST.name())
				.data(getErrorsMap(errors)).build();

		return new ResponseEntity<ResponseDto<Map<String, List<String>>>>(responseDto, HttpStatus.BAD_REQUEST);
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleUserNotFoundException(
			UserNotFoundException ex) {
		ResponseDto<Map<String, List<String>>> responseDto = new ResponseDto.Builder<Map<String, List<String>>>()
				.status(String.valueOf(HttpStatus.NOT_FOUND.value())).message(HttpStatus.BAD_REQUEST.name())
				.data(getErrorsMap(List.of(ex.getMessage()))).build();

		return new ResponseEntity<ResponseDto<Map<String, List<String>>>>(responseDto, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ResponseDto<Map<String, List<String>>>> handleGlobalException(Exception ex) {
		ResponseDto<Map<String, List<String>>> responseDto = new ResponseDto.Builder<Map<String, List<String>>>()
				.status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
				.message(HttpStatus.INTERNAL_SERVER_ERROR.name()).data(getErrorsMap(List.of(ex.getMessage()))).build();

		return new ResponseEntity<ResponseDto<Map<String, List<String>>>>(responseDto,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
