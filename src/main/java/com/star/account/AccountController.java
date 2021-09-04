package com.star.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private AccountService accountService;
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
            Long expireTime = redisTemplate.getExpire(email);
            return String.valueOf(expireTime);
        }
        executorService.submit(() -> sendMail(email, verifyCode));
        return "success";
    }

    @PostMapping(value = "/register")
    public String register(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("verifyCode") String verifyCode) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        // 如果没有验证码或者验证码不正确
        if (opsForValue.get(email) == null || !opsForValue.get(email).toString().equals(verifyCode)) {
            return "001";
        }
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        if (accountService.insertAccount(account) == AccountServiceImpl.EXISTED) {
            return "002";
        }
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
