package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘乾坤
 * @Date 2021/8/19
 * @Description
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private String useMailName = "lqkdevelop@163.com";
    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Resource(name = "sendMailPool")
    private ExecutorService executorService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "username") String account, @RequestParam(value = "password") String password){
        if (null != accountService.selectAccount(account, password)) {
            return "success";
        }
        return "failure";
    }

    @RequestMapping(value = "/getRegisterCode", method = RequestMethod.GET)
    public String getRegisterCode(@RequestParam("email") String email) {
        String verifyCode = getVerifyCode();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        Boolean ifAbsent = opsForValue.setIfAbsent(email, verifyCode, 60, TimeUnit.SECONDS);
        if (!ifAbsent) {
            return "failure";
        }
        executorService.submit(() -> sendMail(email, verifyCode));
        return "success";
    }

    private void sendMail(String email, String verifyCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(useMailName);
        message.setTo(email);
        message.setText("您的注册验证码为: " + verifyCode + "，您正在使用邮箱注册，验证码有效时间 60 秒，请尽快完成注册");
        message.setSubject("【星博客】星博客注册验证码");
        javaMailSender.send(message);
    }

    private String getVerifyCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
