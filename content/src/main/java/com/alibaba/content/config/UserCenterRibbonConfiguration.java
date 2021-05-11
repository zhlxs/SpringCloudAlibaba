package com.alibaba.content.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import specialconfig.RibbonConfig;

/**
 * 负载均衡细粒度配置类
 */
@Configuration
//@RibbonClient(name = "user-center", configuration = RibbonConfig.class)
//实现全局的配置
@RibbonClients(defaultConfiguration = RibbonConfig.class)
public class UserCenterRibbonConfiguration {
}
