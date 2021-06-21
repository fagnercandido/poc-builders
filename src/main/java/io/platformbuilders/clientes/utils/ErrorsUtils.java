package io.platformbuilders.clientes.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ErrorsUtils {
	
	private final MessageSource messages;

	public ErrorsUtils(MessageSource messages) {
		this.messages = messages;
	}

	public ResponseEntity<Object> defaultErrorMessage(HttpStatus status, HttpHeaders headers, MethodArgumentNotValidException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
	
	public ResponseEntity<Object> defaultErrorMessage(HttpStatus status, RuntimeException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		List<String> errors = Arrays.asList(new String[] { ex.getMessage() });
		body.put("errors", errors);
		return new ResponseEntity<>(body, status);
	}

	public ResponseEntity<Object> defaultErrorMessage(HttpStatus status, Exception ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		List<String> errors = Arrays.asList(new String[] { ex.getMessage() });
		body.put("errors", errors);
		return new ResponseEntity<>(body, status);
	}
	
	public ResponseEntity<Object> defaultErrorMessage(HttpStatus status, DataIntegrityViolationException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		String [] key =  new String[] { ((ConstraintViolationException)ex.getCause()).getConstraintName() };
		String message = messages.getMessage("chave.duplicada", key, Locale.US);
		List<String> errors = Arrays.asList(new String[] { message });
		body.put("errors", errors);
		return new ResponseEntity<>(body, status);
	}

}
