package com.star.account;


import org.springframework.stereotype.Component;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Component
public interface AccountService {

    public String selectAccount(String username, String password);
}
