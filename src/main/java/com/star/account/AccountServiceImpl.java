package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Repository
public class AccountServiceImpl implements AccountService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String selectAccount(String username, String password) {
        String sql = "SELECT fusername FROM t_user WHERE fusername = ? AND fpassword = ?";
        String selectUsername = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, String.class);
        return selectUsername;
    }
}
