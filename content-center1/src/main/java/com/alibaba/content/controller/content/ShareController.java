package com.alibaba.content.controller.content;

import com.alibaba.content.domain.dto.content.ShareDTO;
import com.alibaba.content.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shareController")
public class ShareController
{

	@Autowired
	private ShareService shareService;

	/**
	 * 查询分享内容
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ShareDTO findById(@PathVariable Integer id, @RequestHeader("X-Token") String token)
	{
		return shareService.findById(id, token);
	}
}
