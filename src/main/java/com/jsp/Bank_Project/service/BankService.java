package com.jsp.Bank_Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.exception.IdNotFoundException;
import com.jsp.Bank_Project.exception.NoRecordAvailableException;
import com.jsp.Bank_Project.repository.BankRepository;


@Service
public class BankService {

	@Autowired
	private BankRepository br;
	
	//create bank
	public ResponseStructure<Bank> saveAllBank(Bank bank){
		ResponseStructure<Bank> res=new ResponseStructure<>();

			if(br.existsByIfscCode(bank.getIfscCode())) {
				throw new DataIntegrityViolationException("ifscCode already exists");
			}
			if(br.existsByContact(bank.getContact())) {
				throw new DataIntegrityViolationException("ifscCode already exists");
			}
			if(String.valueOf(bank.getContact()).length()!=10 && String.valueOf(bank.getAddress().getPincode()).length()!=6) {
				throw new DataIntegrityViolationException("Contact is not 10 digit or pincode is not 6 digit");
			}
			if(bank.getAddress()==null) {
				throw new NoRecordAvailableException("Address must be passed to save bank");
			}
			
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Bank records saved");
		res.setData(br.save(bank));
		return res;
	}
	
	//get all banks
	public ResponseStructure<List<Bank>> getAllBank(){
		ResponseStructure<List<Bank>> res=new ResponseStructure<List<Bank>>();
		List<Bank> bank=br.findAll();
		if(!bank.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("All records found");
			res.setData(bank);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records present");
		}
	}
	
	//get bank by id
	public ResponseStructure<Bank> getBankById(Integer id){
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		Optional<Bank> opt=br.findById(id);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Bank record with "+id+" does not exist" );
		}
	}
	
	//delete bank
	public ResponseStructure<String> deleteBank(Integer id){
		ResponseStructure<String> res=new ResponseStructure<String>();
		Optional<Bank> opt=br.findById(id);
		if(opt.isPresent()) {
			if(opt.get().getAccount()==null || opt.get().getAccount().isEmpty()) {
				br.deleteById(id);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Bank Record Deleted");
				res.setData("Deleted");
				return res;
			}
			else
				throw new IdNotFoundException("Bank Has Active Accounts, so cannot be deleted.");
		}
		else
			throw new IdNotFoundException("No Bank Record Found To delete with Id "+id);
	}
	
	//get bank by ifsc
	public ResponseStructure<Bank> getBankByIfscCode(String ifscCode){
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		Optional<Bank> opt=br.findByIfscCode(ifscCode);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Bank record with "+ifscCode+" does not exist" );
		}
	}
	
	//get bank by pagination
	public ResponseStructure<Page<Bank>> getBankByPages(int pageNumber, int pageSize){
		ResponseStructure<Page<Bank>> res= new ResponseStructure<>();
		Page<Bank> pages=br.findAll(PageRequest.of(pageNumber, pageSize));
		if(!pages.isEmpty()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Records retrieved");
			res.setData(pages);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records available in pages");
		}
	}
	
	//get bank by address
	public ResponseStructure<Bank> getBankByAddress(Address address){
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		Optional<Bank> opt=br.findByAddress(address);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Bank record with "+address+" does not exist" );
		}
	}
	
	//get bank by city
	public ResponseStructure<List<Bank>> getBankByCity(String city){
		ResponseStructure<List<Bank>> res=new ResponseStructure<List<Bank>>();
		List<Bank> bank=br.findByCity(city);
		if(!bank.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("All records found");
			res.setData(bank);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records present");
		}
	}
	
	//get bank by contact num
	public ResponseStructure<Bank> getBankByContact(Long contact){
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		Optional<Bank> opt=br.findByContact(contact);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Bank record with "+contact+" does not exist" );
		}
	}
}
