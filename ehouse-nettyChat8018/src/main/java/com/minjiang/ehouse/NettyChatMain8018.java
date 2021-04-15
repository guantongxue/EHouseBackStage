package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther guannw
 * @create 2021/4/7 9:58
 */

@SpringBootApplication
@EnableEurekaClient
public class NettyChatMain8018 {

    public static void main(String[] args) {
        SpringApplication.run(NettyChatMain8018.class,args);
    }
}
