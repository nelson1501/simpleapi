package com.kalkivera.apps.simpleapi.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * 
 * Handler to manage application exceptions
 *
 */
@ControllerAdvice
@RestController
public class ShoppingCartExceptionHandler extends ResponseEntityExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(ex);
		List<String> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getLocalizedMessage(), errors, request.getDescription(false));
		return handleExceptionInternal(ex, cartError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ PersonNotFoundException.class })
	public ResponseEntity<Object> handleUserNotFoundException(PersonNotFoundException ex, WebRequest request) {
		List<String> messages = new ArrayList<>();
		String message = ex.getMessage();
		messages.add(message);
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getErrorMessage(), messages, request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(ex);
		List<String> messages = new ArrayList<>();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException formatException = (InvalidFormatException) ex.getCause();
			messages.add(formatException.getOriginalMessage());
		} else {
			messages.add(ex.getMessage());
		}
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessage(), messages, request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleConflictException(DataIntegrityViolationException ex, WebRequest request) {
		logException(ex);
		List<String> messages = new ArrayList<>();
		messages.add(ex.getCause().getMessage());
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.CONFLICT.getReasonPhrase(),
				ex.getMessage(), messages, request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		logException(ex);
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(),
				Arrays.asList(ex.getLocalizedMessage()), request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		logException(ex);
		List<String> messages = new ArrayList<>();
		String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		messages.add(error);
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessage(), messages, request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException(ex);
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		List<String> messages = new ArrayList<>();
		messages.add(builder.toString());

		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(),
				HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), ex.getLocalizedMessage(), messages,
				request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		logException(ex);
		List<String> errors = new ArrayList<>();
		String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		errors.add(error);
		SimpleApiErrorDTO cartError = new SimpleApiErrorDTO(new Date(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getLocalizedMessage(), errors, request.getDescription(false));
		return new ResponseEntity<>(cartError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	private void logException(Exception ex) {
		log.error(ex.getMessage(), ex);
	}

}
