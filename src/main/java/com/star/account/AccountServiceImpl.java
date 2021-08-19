package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Repository
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountService accountService;

    @Override
    public Account selectAccount(String account, String password) {
        return accountService.selectAccount(account, password);
    }
}
