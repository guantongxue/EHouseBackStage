package com.minjiang.ehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @auther guannw
 * @create 2021/3/12 3:38
 */

@SpringBootApplication
@EnableEurekaClient
public class mapMain9010 {

    public static void main(String[] args) {
        SpringApplication.run(mapMain9010.class,args);
    }
}
