package com.sujal.springboottemplate.repository;

import com.sujal.springboottemplate.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account ,Long> {

}
