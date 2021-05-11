package com.alibaba.content.controller;

import com.alibaba.content.dto.ShareDTO;
import com.alibaba.content.model.Share;
import com.alibaba.content.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/shares")
    public List<Share> findAllShare() {
        return shareService.findAllShare();
    }
}
