package com.springboot.springboot.dao;

import com.springboot.springboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAccountDao extends JpaRepository<Account,Integer> {
}
