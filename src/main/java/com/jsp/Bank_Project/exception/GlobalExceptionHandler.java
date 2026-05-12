package com.jsp.Bank_Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.Bank_Project.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception){
		ResponseStructure<String> res=new ResponseStructure<>();		
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordAvailableException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailableException(NoRecordAvailableException exception){
		ResponseStructure<String> res=new ResponseStructure<>();		
		res.setStatusCode(HttpStatus.NOT_FOUND.value());
		res.setMessage(exception.getMessage());
		res.setData("Failure");		
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccountBalanceExceedException.class)
	public ResponseEntity<ResponseStructure<String>> handleAccountBalanceExceedException(AccountBalanceExceedException exception){
		ResponseStructure<String> res=new ResponseStructure<String>();
		res.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		res.setMessage(exception.getMessage());
		res.setData("Failed");
		return new ResponseEntity<ResponseStructure<String>>(res,HttpStatus.NOT_ACCEPTABLE);
	}
	
}
