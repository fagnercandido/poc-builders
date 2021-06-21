package io.platformbuilders.clientes.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.platformbuilders.clientes.entity.ClienteNaoEncontradoException;

@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

	private final ErrorsUtils errorUtils;

	public CommonExceptionHandler(ErrorsUtils errorUtils) {
		this.errorUtils = errorUtils;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return errorUtils.defaultErrorMessage(status, headers, ex);

	}

	@ExceptionHandler(ClienteNaoEncontradoException.class)
	public ResponseEntity<Object> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
		return errorUtils.defaultErrorMessage(HttpStatus.NOT_FOUND, ex);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleClienteNaoEncontradoException(Exception ex) {
		return errorUtils.defaultErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(DataIntegrityViolationException ex) {
		return errorUtils.defaultErrorMessage(HttpStatus.CONFLICT, ex);
	}

}
