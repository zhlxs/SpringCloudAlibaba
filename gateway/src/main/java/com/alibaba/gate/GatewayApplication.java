package com.alibaba.gate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication
{

	public static void main(String[] args)
	{
		System.out.println("【网关服务】启动~~~");
		SpringApplication.run(GatewayApplication.class, args);
	}

}
