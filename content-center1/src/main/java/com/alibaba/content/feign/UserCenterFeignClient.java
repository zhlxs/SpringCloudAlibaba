package com.alibaba.content.feign;

import com.alibaba.content.config.UserCenterFeignConfiguration;
import com.alibaba.content.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-center", configuration = UserCenterFeignConfiguration.class)
//@FeignClient(name = "user-center")
public interface UserCenterFeignClient
{

	/**
	 * feign
	 *
	 * @param id 主键
	 * @return 用户信息
	 */
	@GetMapping("/userController/{id}")
	UserDTO findById(@PathVariable("id") Integer id);
}
