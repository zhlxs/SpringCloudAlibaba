package com.alibaba.content.service.impl;

import com.alibaba.content.dto.ShareDTO;
import com.alibaba.content.dto.UserDTO;
import com.alibaba.content.feign.UserCenterFeignClient;
import com.alibaba.content.mapper.ShareMapper;
import com.alibaba.content.model.Share;
import com.alibaba.content.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareServiceImpl implements ShareService {

    private final ShareMapper shareMapper;
    // private final RestTemplate restTemplate;
    // private final DiscoveryClient discoveryClient;
    private final UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id) {
        //获取分享详情
        Share share = shareMapper.selectById(id);
        Integer userId = share.getUserId();
        //怎么调用用户微服务模块
        //RestTemplate restTemplate = new RestTemplate();
        // 获取用户实例
        //List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        /*String targetUrl = instances.stream()
               .map(instance -> instance.getUri().toString() + "/userController/{id}")
               .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("当前没有实例！"));*/
        // 负载均衡，随机算法
        /*List<String> urls = instances.stream()
                .map(instance -> instance.getUri().toString() + "/userController/{id}").collect(Collectors.toList());
        int index = ThreadLocalRandom.current().nextInt(urls.size());
        String targetUrl = urls.get(index);
        log.info("请求的目标地址：{}", targetUrl);*/
        // 整合Ribbon实现负载均衡
        //String targetUrl = "http://user-center/userController/{userId}";
        //UserDTO userDTO = restTemplate.getForObject(targetUrl, UserDTO.class, userId);
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        //TODO NULL
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;
    }

    @Override
    public List<Share> findAllShare() {
        return shareMapper.findAllShare();
    }
    /**
     * Ribbon组成：
     * IClientConfig:读取配置；
     * IRule:负载均衡规则，选择实例算法
     * IPing:筛选掉ping不通的实例
     * ServerList
     * ServerListFilter
     * ILoadBalancer
     * ServerListUpdate:更新交给Ribbon的List的策略，如2个实例的serverList变化为3个实例serverList
     */
}
