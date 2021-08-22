package com.star.account;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Mapper
public interface AccountService {

    public Account selectAccount(@Param("username") String username, @Param("password") String password);
}
