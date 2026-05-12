package com.jsp.Bank_Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Account;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.enums.AccountType;
import com.jsp.Bank_Project.exception.AccountBalanceExceedException;
import com.jsp.Bank_Project.exception.IdNotFoundException;
import com.jsp.Bank_Project.exception.NoRecordAvailableException;
import com.jsp.Bank_Project.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository ar;
	
	//create account
	public ResponseStructure<Account> saveAllAccount(Account account){
		ResponseStructure<Account> res=new ResponseStructure<Account>();
		 if(ar.existsByAccountNumber(account.getAccountNumber())) {
			 throw new DataIntegrityViolationException("Account number already exists");
		 }
		Optional<Bank> opt=ar.findByBankId(account.getBank().getId());
		 if(opt.isPresent()) {
			 account.setBank(opt.get());
			 res.setStatusCode(HttpStatus.CREATED.value());
			 res.setMessage("Account created");
			 res.setData(ar.save(account));
			 return res;
		 }
		 else {
			 throw new NoRecordAvailableException("Bank does not exist");
		 }
	}
	
	//get all account
	public ResponseStructure<List<Account>> getAllAccount(){
		ResponseStructure<List<Account>> res=new ResponseStructure<List<Account>>();
		List<Account> account=ar.findAll();
		if(!account.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Account records found");
			res.setData(account);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No record available");
		}
	}
	
	//get account by id
	public ResponseStructure<Account> getAccountById(Integer accountId){
		ResponseStructure<Account> res=new ResponseStructure<Account>();
		Optional<Account> opt=ar.findById(accountId);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setMessage("Account record found");
			res.setStatusCode(HttpStatus.FOUND.value());
			return res;
		}
		else {
			throw new NoRecordAvailableException("No record found");
		}
	}
	
	//delete account
	public ResponseStructure<String> deleteAccount(Integer accountId){
		ResponseStructure<String> res=new ResponseStructure<String>();
		Optional<Account> opt=ar.findById(accountId);
		if(opt.isPresent()) {
			ar.deleteById(accountId);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Deleted");
			res.setData("Account record deleted");
			return res;
		}
		else {
			throw new IdNotFoundException("no record found");
		}
	}
	
	//deposit amount to account
	public ResponseStructure<Account> depositAmount(Integer accountNumber, double amount){
		ResponseStructure<Account> res=new ResponseStructure<Account>();
		Optional<Account> opt=ar.findByAccountNumber(accountNumber);
		if(opt.isPresent()) {
			if(amount>0) {
				opt.get().setBalance(opt.get().getBalance()+amount);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Amount deposited");
				res.setData(ar.save(opt.get()));
				return res;
			}
			else {
				throw new NoRecordAvailableException("Transaction failed");
			}
		}
		else {
			throw new NoRecordAvailableException("Account does not exist");
		}
	}
	
	//withdraw amount from account
	public ResponseStructure<Account> withdrawAmount(Integer accountNumber, double amount){
		ResponseStructure<Account> res=new ResponseStructure<Account>();
		Optional<Account> opt=ar.findByAccountNumber(accountNumber);
		if(opt.isPresent()) {
			if(amount>0 && opt.get().getBalance()>=amount) {
				opt.get().setBalance(opt.get().getBalance()-amount);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Amount withdrawed");
				res.setData(ar.save(opt.get()));
				return res;
			}
			else {
				throw new AccountBalanceExceedException("Transaction failed");
			}
		}
		else {
			throw new NoRecordAvailableException("Account does not exist");
		}
	}
	
	//transfer amount from one account to another
	public ResponseStructure<Account> transferAmount(Integer sender, Integer reciever, double amount){
		ResponseStructure<Account> res=new ResponseStructure<Account>();
		Optional<Account> opt1=ar.findByAccountNumber(sender);
		Optional<Account> opt2=ar.findByAccountNumber(reciever);
		if(opt1.isPresent() && opt2.isPresent() && opt1.get()!=opt2.get()) {
			if(amount>0 && opt1.get().getBalance()>=amount) {
				opt1.get().setBalance(opt1.get().getBalance()-amount);
				opt2.get().setBalance(opt2.get().getBalance()+amount);
				ar.save(opt2.get());
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Amount transfered");
				res.setData(ar.save(opt1.get()));
				return res;
			}
			else {
				throw new AccountBalanceExceedException("Insufficient balance");
			}
		}
		else {
			throw new NoRecordAvailableException("Account does not exist or reciever cannot be same as sender");
		}
	}
	
	//fetch account by bank
	public ResponseStructure<List<Account>> getAccountByBank(Bank bank){
		ResponseStructure<List<Account>> res=new ResponseStructure<List<Account>>();
		List<Account> accounts=ar.findByBank(bank);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Account records found");
			res.setData(accounts);
			return res;
		}
		else {
			throw new NoRecordAvailableException("Account records of "+bank+" does not exist");
		}
	}
	
	//fetch account by account-type
	public ResponseStructure<List<Account>> getAccountByType(AccountType accountType){
		ResponseStructure<List<Account>> res=new ResponseStructure<List<Account>>();
		List<Account> accounts=ar.findByAccountType(accountType);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Account records found");
			res.setData(accounts);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records found");
		}
	}
	
	//get accounts based on balance greater than value
	public ResponseStructure<List<Account>> getAccountWithBalanceGreaterThan(Double balance){
		ResponseStructure<List<Account>> res=new ResponseStructure<List<Account>>();
		List<Account> accounts=ar.findByBalanceGreaterThan(balance);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Account records found");
			res.setData(accounts);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records found");
		}
	}
	
	//get accounts by sorting
	public ResponseStructure<List<Account>> getAccountBySorting(String fieldName){
		ResponseStructure<List<Account>> res=new ResponseStructure<List<Account>>();
		List<Account> accounts=ar.findAll(Sort.by(fieldName).ascending());
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Accounts retrieved in ascending order");
			res.setData(accounts);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No record available");
		}
	}
}
