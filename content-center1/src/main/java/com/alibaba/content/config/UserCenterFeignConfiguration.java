package com.alibaba.content.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 别加@Configuration，
 * 父子级上下文
 */
public class UserCenterFeignConfiguration {
    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }
}
