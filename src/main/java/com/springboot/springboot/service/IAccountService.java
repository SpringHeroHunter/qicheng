package com.springboot.springboot.service;

import com.springboot.springboot.entity.Account;
import java.util.List;

public interface IAccountService {

    /**
     * 增加
     * @return
     */
    int add(String name,double money);

    /**
     * 编辑
     * @return
     */
    int update(String name, double money, int id);

    /**
     * 删除
     * @param id
     * @return
     */
    int del(int id);

    /**
     * 查询
     * @return
     */
    List<Account> findAccountList();

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Account findAccountById(int id);
}
