package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther guannw
 * @create 2021/3/1 21:09
 */

@SpringBootApplication
@EnableEurekaClient
public class UserProviderMain9002 {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderMain9002.class,args);
    }
}
