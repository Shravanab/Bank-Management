package com.jsp.Bank_Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jsp.Bank_Project.dto.ResponseStructure;
import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.exception.IdNotFoundException;
import com.jsp.Bank_Project.exception.NoRecordAvailableException;
import com.jsp.Bank_Project.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository ar;
	
	//get address by id
	public ResponseStructure<Address> getAddressById(Integer id){
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		Optional<Address> opt=ar.findById(id);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Address record with "+id+" does not exist" );
		}
	}
	
	//update address
	public ResponseStructure<Address> updateAddress(Address address){
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		//case-I
		if(address.getId()==null) {
			throw new IdNotFoundException("ID should be present to update record" );
		}
		
		Optional<Address> opt=ar.findById(address.getId());
		//case-II
		if(opt.isPresent()) {
			res.setData(ar.save(address));
			res.setMessage("Updated");
			res.setStatusCode(HttpStatus.OK.value());
			return res;
		}
		//CASE-III
		else {
			throw new IdNotFoundException("ID does not exist" );
		}
	}
	
	//get address by bank
	public ResponseStructure<Address> getAddressByBank(Bank bank){
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		Optional<Address> opt=ar.findByBank(bank);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address record found");
			return res;
		}
		else {
			throw new IdNotFoundException("Address record with "+bank+" does not exist" );
		}
	}
	
	//get address by city
	public ResponseStructure<List<Address>> getAddressByCity(String city){
		ResponseStructure<List<Address>> res=new ResponseStructure<List<Address>>();
		List<Address> address=ar.findByCity(city);
		if(!address.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("All records found");
			res.setData(address);
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records present");
		}
	}
	
	//get address by city and street
	public ResponseStructure<Address> getAddressByCityAndStreet(String city,String street){
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		Optional<Address> opt=ar.findByCityAndStreet(city, street);
		if(!opt.isEmpty()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address record found");
			return res;
		}
		else {
			throw new NoRecordAvailableException("Address record with "+city +" and "+street+" does not exist");
		}
	}
	
	//get address by pincode
	public ResponseStructure<Address> getAddressByPincode(Long pincode){
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		Optional<Address> opt=ar.findByPincode(pincode);
		if(opt.isPresent()) {
			res.setData(opt.get());
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address record found");
			return res;
		}
		else {
			throw new NoRecordAvailableException("No records present");
		}
	}
}
