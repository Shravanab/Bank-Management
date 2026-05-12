package com.jsp.Bank_Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	AddressService as;
	
	//get address by id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<Address>>(as.getAddressById(id),HttpStatus.FOUND);
	}
	
	//update address
	@PutMapping("/update")
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@RequestBody Address address){
		return new ResponseEntity<ResponseStructure<Address>>(as.updateAddress(address),HttpStatus.FOUND);
	}
	
	//get address by bank
	@GetMapping("/bank")
	public ResponseEntity<ResponseStructure<Address>> getAddressByBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<Address>>(as.getAddressByBank(bank),HttpStatus.FOUND);
	}
	
	//get address by city
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(@PathVariable String city){
		return new ResponseEntity<ResponseStructure<List<Address>>>(as.getAddressByCity(city),HttpStatus.FOUND);
	}
	
	//get address by city and street
	@GetMapping("/city/street/{city}/{street}")
	public ResponseEntity<ResponseStructure<Address>> getAddressByCityAndStreet(@PathVariable String city, @PathVariable String street){
		return new ResponseEntity<ResponseStructure<Address>>(as.getAddressByCityAndStreet(city, street),HttpStatus.FOUND);
	}
	
	//get address by pincode
	@GetMapping("pincode/{pincode}")
	public ResponseEntity<ResponseStructure<Address>> getAddressByPincode(@PathVariable Long pincode){
		return new ResponseEntity<ResponseStructure<Address>>(as.getAddressByPincode(pincode),HttpStatus.FOUND);
	}
}
