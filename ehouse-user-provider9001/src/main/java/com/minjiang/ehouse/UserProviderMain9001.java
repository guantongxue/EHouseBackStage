package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther guannw
 * @create 2021/3/1 21:08
 */

@SpringBootApplication
@EnableEurekaClient
public class UserProviderMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(UserProviderMain9001.class,args);
    }
}
