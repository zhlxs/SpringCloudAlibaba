package com.alibaba.content.service.content;

import com.alibaba.content.dao.content.ShareMapper;
import com.alibaba.content.domain.dto.content.ShareDTO;
import com.alibaba.content.domain.dto.user.UserDTO;
import com.alibaba.content.domain.entity.content.Share;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    private final RestTemplate restTemplate;

    public ShareDTO findById(Integer id) {
        //获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        //怎么调用用户微服务模块
        //RestTemplate restTemplate = new RestTemplate();
        UserDTO userDTO = restTemplate.getForObject("http://localhost:8080/userController/{id}", UserDTO.class, userId);
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        //TODO NULL
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;
    }
}
