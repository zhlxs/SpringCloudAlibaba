package com.alibaba.core.service.user;

import com.alibaba.core.dao.user.UserMapper;
import com.alibaba.core.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService
{

	private final UserMapper userMapper;

	/**
	 * 根据Id查询用户
	 *
	 * @param id
	 * @return
	 */
	public User findById(Integer id)
	{
		return this.userMapper.selectByPrimaryKey(id);
	}
}
