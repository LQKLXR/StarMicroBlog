package com.star.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author 刘乾坤
 * @Date 2021/9/3
 * @Description
 */
@Configuration
public class BeanConfiguration {


    @Bean(name = "sendMailPool")
    public ExecutorService sendMailPool(){
        return Executors.newFixedThreadPool(10);
    }
}
