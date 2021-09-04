package com.star.account;

import lombok.Data;

import java.util.Date;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@Data
public class Account {
    private Integer id;
    private String email;
    private String password;
}
