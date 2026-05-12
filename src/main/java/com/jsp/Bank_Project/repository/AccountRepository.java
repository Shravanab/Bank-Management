package com.jsp.Bank_Project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.Bank_Project.entity.Account;
import com.jsp.Bank_Project.entity.Bank;
import com.jsp.Bank_Project.enums.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	//to check existence of account number
	boolean existsByAccountNumber(Integer accountNumber);
	
	//to check in account while saving an account that if bank exists or not
	@Query("select b from Bank b where b.id=?1")
	Optional<Bank> findByBankId(Integer id);
	
	//to deposit amount into account using accountNumber
	Optional<Account> findByAccountNumber(Integer accountNumber);
	
	//fetch account by bank
	List<Account> findByBank(Bank bank);
	
	//fetch account by accountType
	List<Account> findByAccountType(AccountType accountType);
	
	//fetch account with balance greater than value
	List<Account> findByBalanceGreaterThan(Double balance);
}
