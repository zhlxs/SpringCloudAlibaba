package com.alibaba.content;

//import com.alibaba.content.config.UserCenterFeignConfiguration;
import com.netflix.loadbalancer.IPing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.alibaba.content.dao.content")
//@EnableFeignClients(defaultConfiguration = UserCenterFeignConfiguration.class) //feign全局配置
@EnableFeignClients
public class ContentCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentCenterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
/**
 * 1.雪崩效应：cascading failure，级联失效，级联故障
 * 2.Sentinel:容错库
 **/