package com.alibaba.content.controller.content;

import com.alibaba.content.auth.CheckAuthorization;
import com.alibaba.content.domain.dto.content.ShareAuditDTO;
import com.alibaba.content.domain.entity.content.Share;
import com.alibaba.content.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shareAdminController")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareAdminController
{

	private final ShareService shareService;

	@PutMapping("/audit/{id}")
	@CheckAuthorization("admin") // TODO AOP实现授权认证
	public Share auditById(@PathVariable Integer id, @RequestBody ShareAuditDTO auditDTO)
	{
		return this.shareService.auditById(id, auditDTO);
	}
}
