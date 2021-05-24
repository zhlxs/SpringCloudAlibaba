package com.alibaba.core.controller.user;

import com.alibaba.core.domain.entity.user.User;
import com.alibaba.core.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userController")
public class UserController
{

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public User findById(@PathVariable Integer id)
	{
		return userService.findById(id);
	}
}
