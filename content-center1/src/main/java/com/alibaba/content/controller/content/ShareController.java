package com.alibaba.content.controller.content;

import com.alibaba.content.domain.dto.content.ShareDTO;
import com.alibaba.content.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shareController")
public class ShareController {

    @Autowired
    private ShareService shareService;

    /**
     * 查询分享内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ShareDTO findById(@PathVariable Integer id) {
        return shareService.findById(id);
    }
}
