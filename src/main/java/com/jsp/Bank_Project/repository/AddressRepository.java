package com.jsp.Bank_Project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	//fetch address by bank
	Optional<Address> findByBank(Bank bank);
	
	//fetch address by city
	@Query("select a from Address a where a.city=?1")
	List<Address> findByCity(String city);
	
	//fetch address by city and street
	Optional<Address> findByCityAndStreet(String street, String city);
	
	//fetch address by pincode
	Optional<Address> findByPincode(Long pincode);
}
