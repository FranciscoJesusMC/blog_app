package com.blog.backend.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetalles> manejarResource (ResourceNotFoundException ex, WebRequest request){
		ErrorDetalles errores = new ErrorDetalles(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorDetalles> manejarBlogAppException (BlogAppException ex, WebRequest request){
		ErrorDetalles errores = new ErrorDetalles(new Date(), ex.getMensaje(), request.getDescription(false));
		return new ResponseEntity<>(errores,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetalles> manjearException (Exception ex, WebRequest request){
		ErrorDetalles errores = new ErrorDetalles(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errores,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error ->{
			String nombreCampo = ((FieldError)error).getField();
			String mensaje  = error.getDefaultMessage();
			
			errores.put(nombreCampo, mensaje);
		}));
		
		return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
	
	}
}
