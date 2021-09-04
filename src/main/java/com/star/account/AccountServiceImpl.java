package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Repository
public class AccountServiceImpl implements AccountService{

    public static final int EXISTED = -1;

    @Autowired
    private AccountService accountService;

    @Override
    public Account selectAccount(String email, String password) {
       return accountService.selectAccount(email,password);
    }

    @Transactional
    @Override
    public Integer insertAccount(Account account) {
        if (null != selectAccount(account.getEmail(), account.getPassword())) {
            return EXISTED;
        }
        return accountService.insertAccount(account);
    }
}
