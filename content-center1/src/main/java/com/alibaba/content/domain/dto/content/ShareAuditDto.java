package com.alibaba.content.domain.dto.content;

import com.alibaba.content.enums.AuditStatusEnum;
import lombok.Data;

@Data
public class ShareAuditDTO
{
	/**
	 * 审核状态
	 */
	private AuditStatusEnum auditStatusEnum;
	/**
	 * 原因
	 */
	private String reason;
}
