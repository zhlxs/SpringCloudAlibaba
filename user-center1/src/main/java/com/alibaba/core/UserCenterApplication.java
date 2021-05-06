package com.alibaba.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.alibaba")
public class UserCenterApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
