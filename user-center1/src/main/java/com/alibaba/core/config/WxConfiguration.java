package com.alibaba.core.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxConfiguration
{
	private final String APP_ID = "wx5398457df5368458";
	private final String SECRET = "9ec68b33ae9a8e4db5a92c2e24ef4aea";

	@Bean
	public WxMaConfig wxMaConfig()
	{
		WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
		config.setAppid(APP_ID);
		config.setSecret(SECRET);
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig wxMaConfig)
	{
		WxMaServiceImpl service = new WxMaServiceImpl();
		service.setWxMaConfig(wxMaConfig);
		return service;
	}
}
