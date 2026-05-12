package com.jsp.Bank_Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	private BankService bs;
	
	//create bank
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<Bank>> saveAllBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<Bank>>(bs.saveAllBank(bank),HttpStatus.CREATED);
	}
	
	//get all banks
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks(){
		return new ResponseEntity<ResponseStructure<List<Bank>>>(bs.getAllBank(),HttpStatus.FOUND);
	}
	
	//get bank by id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable Integer id) {
			return new ResponseEntity<ResponseStructure<Bank>>(bs.getBankById(id),HttpStatus.FOUND);
	}
	
	//delete bank
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable Integer id) {
			return new ResponseEntity<ResponseStructure<String>>(bs.deleteBank(id),HttpStatus.OK);
	}
	
	//get bank by ifsc
	@GetMapping("/ifsc/{ifscCode}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByifscCode(@PathVariable String ifscCode){
			return new ResponseEntity<ResponseStructure<Bank>>(bs.getBankByIfscCode(ifscCode),HttpStatus.FOUND);
	}
	
	//get bank by pagination
	@GetMapping("/pages/{pageNumber}/{pageSize}")
	public ResponseEntity<ResponseStructure<Page<Bank>>> getBankByPages(@PathVariable int pageNumber,@PathVariable int pageSize){
		return new ResponseEntity<ResponseStructure<Page<Bank>>>(bs.getBankByPages(pageNumber, pageSize),HttpStatus.OK);
	}
	
	//get bank by address
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<Bank>> getBankByAddress(@RequestBody Address address) {
			return new ResponseEntity<ResponseStructure<Bank>>(bs.getBankByAddress(address),HttpStatus.FOUND);
	}
	
	//get bank by city
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(@PathVariable String city){
		return new ResponseEntity<ResponseStructure<List<Bank>>>(bs.getBankByCity(city),HttpStatus.FOUND);
	}
	
	//get bank by contact
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByContact(@PathVariable Long contact) {
			return new ResponseEntity<ResponseStructure<Bank>>(bs.getBankByContact(contact),HttpStatus.FOUND);
	}
}

