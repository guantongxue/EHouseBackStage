package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther guannw
 * @create 2021/3/1 20:55
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class UserConsumerMain80 {
    public static void main(String[] args) {
        SpringApplication.run(UserConsumerMain80.class,args);
    }
}
