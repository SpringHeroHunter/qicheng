package com.springboot.springboot.service;

import com.springboot.springboot.entity.Account;
import com.springboot.springboot.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public int add(String name,double money) {
        return accountMapper.add(name,money);
    }

    @Override
    public int update(String name, double money, int id) {
        return accountMapper.update(name,money,id);
    }

    @Override
    public int del(int id) {
        return accountMapper.delete(id);
    }

    @Override
    public List<Account> findAccountList() {
        return accountMapper.findAccountList();
    }

    @Override
    public Account findAccountById(int id) {
        return accountMapper.findAccountById(id);
    }
}
