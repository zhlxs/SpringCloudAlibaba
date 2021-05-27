package com.alibaba.core.service.user;

import com.alibaba.core.dao.user.UserMapper;
import com.alibaba.core.domain.entity.user.User;
import com.alibaba.core.dto.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

	/**
	 * 根据openid查询用户
	 *
	 * @param loginDTO
	 * @param openid
	 * @return
	 */
	public User login(UserLoginDTO loginDTO, String openid)
	{
		User user = this.userMapper.selectOne(User.builder().wxId(openid).build());
		if (user == null)
		{
			User userObj = User.builder()
					.wxId(openid)
					.bonus(300)
					.wxNickname(loginDTO.getWxNickname())
					.avatarUrl(loginDTO.getAvatarUrl())
					.roles("user")
					.createTime(new Date())
					.updateTime(new Date()).build();
			this.userMapper.insertSelective(userObj);
			return userObj;
		}
		return user;
	}
}
