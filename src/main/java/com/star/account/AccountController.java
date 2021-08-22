package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;


    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String account, @RequestParam(value = "password") String password){
        if (null != accountService.selectAccount(account, password)) {
            return "success";
        }
        return "failure";
    }
}
