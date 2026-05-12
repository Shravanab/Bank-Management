package com.jsp.Bank_Project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.Bank_Project.entity.Address;
import com.jsp.Bank_Project.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{
	
	//to check existence of ifscCode
	boolean existsByIfscCode(String ifscCode);
	
	//to check existence of contact
	boolean existsByContact(Long contact);
	
	//fetch bank by ifscCode
	Optional<Bank> findByIfscCode(String ifscCode);
	
	//fetch bank by address
	Optional<Bank> findByAddress(Address address);
	
	//fetch bank by city
	@Query("select b from Bank b where b.address.city=?1")
	List<Bank> findByCity(String city);
	
	//fetch bank by contact
	Optional<Bank> findByContact(Long contact);
	
}
