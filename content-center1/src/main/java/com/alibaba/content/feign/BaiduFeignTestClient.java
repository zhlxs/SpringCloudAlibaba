package com.alibaba.content.feign;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * 脱离ribbon的feign接口实现方案
 */
//@FeignClient(name = "baidu", url = "http://www.baidu.com")
public interface BaiduFeignTestClient
{

	@GetMapping("")
	public String index();
}
