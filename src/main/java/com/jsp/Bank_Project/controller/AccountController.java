package com.jsp.Bank_Project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Account;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.enums.AccountType;
import com.jsp.Bank_Project.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService as;
	
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<Account>> createAccount(@RequestBody Account account){
		return new ResponseEntity<ResponseStructure<Account>>(as.saveAllAccount(account), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Account>>> getAllAccount(){
		return new ResponseEntity<ResponseStructure<List<Account>>>(as.getAllAccount(),HttpStatus.FOUND);
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable Integer accountId){
		return new ResponseEntity<ResponseStructure<Account>>(as.getAccountById(accountId),HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{accountId}")
	public ResponseEntity<ResponseStructure<String>> deleteAccount(@PathVariable Integer accountId){
		return new ResponseEntity<ResponseStructure<String>>(as.deleteAccount(accountId),HttpStatus.OK);
	}
	
	@PatchMapping("/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> depositAmount(@PathVariable Integer accountNumber, @PathVariable double amount){
		return new ResponseEntity<ResponseStructure<Account>>(as.depositAmount(accountNumber, amount),HttpStatus.OK);
	}
	
	@PatchMapping("/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> withdrawAmount(@PathVariable Integer accountNumber, @PathVariable double amount){
		return new ResponseEntity<ResponseStructure<Account>>(as.withdrawAmount(accountNumber, amount),HttpStatus.OK);
	}
	
	@PatchMapping("/transfer/{sender}/{reciever}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> transferAmount(@PathVariable Integer sender, @PathVariable Integer reciever, @PathVariable double amount){
		return new ResponseEntity<ResponseStructure<Account>>(as.transferAmount(sender, reciever, amount),HttpStatus.OK);
	}
	
	@GetMapping("/bank")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<List<Account>>>(as.getAccountByBank(bank),HttpStatus.FOUND);
	}
	
	@GetMapping("/accountType/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAcountByType(@PathVariable AccountType accountType){
		return new ResponseEntity<ResponseStructure<List<Account>>>(as.getAccountByType(accountType),HttpStatus.FOUND);
	}
	
	@GetMapping("/greater/{balance}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountWithBalanceGreaterThan(@PathVariable double balance){
		return new ResponseEntity<ResponseStructure<List<Account>>>(as.getAccountWithBalanceGreaterThan(balance),HttpStatus.FOUND);
	}
	@GetMapping("/sort/{fieldName}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountBySorting(@PathVariable String fieldName){
		return new ResponseEntity<ResponseStructure<List<Account>>>(as.getAccountBySorting(fieldName),HttpStatus.OK);
	}
}
