package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @auther guannw
 * @create 2021/3/21 15:53
 */

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableAsync
public class HouseResouceMain9020 {

    public static void main(String[] args) {
        SpringApplication.run(HouseResouceMain9020.class,args);
    }
}
