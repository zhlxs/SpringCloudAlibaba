package com.alibaba.content.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDTO
{
	/**
	 * 为谁加积分
	 */
	private Integer userId;
	/**
	 * 加多少积分
	 */
	private Integer bonus;
}
