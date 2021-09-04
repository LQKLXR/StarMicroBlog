package com.star.account;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Mapper
public interface AccountService {

    public Account selectAccount(String email, String password);

    public Integer insertAccount(Account account);
}
