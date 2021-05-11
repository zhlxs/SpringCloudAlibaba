package com.alibaba.content.service.content;

import com.alibaba.content.dao.content.ShareMapper;
import com.alibaba.content.dao.trans.RocketmqTransactionLogMapper;
import com.alibaba.content.domain.dto.content.ShareAuditDTO;
import com.alibaba.content.domain.dto.content.ShareDTO;
import com.alibaba.content.domain.dto.message.UserAddBonusMsgDTO;
import com.alibaba.content.domain.dto.user.UserDTO;
import com.alibaba.content.domain.entity.content.Share;
//import com.alibaba.content.feign.UserCenterFeignClient;
import com.alibaba.content.domain.entity.trans.RocketmqTransactionLog;
import com.alibaba.content.enums.AuditStatusEnum;
import com.alibaba.content.feign.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;
    //private final RestTemplate restTemplate;
    //private final DiscoveryClient discoveryClient;
    private final UserCenterFeignClient userCenterFeignClient;

    private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

    private final RocketMQTemplate rocketMQTemplate;

    public ShareDTO findById(Integer id) {
        //获取分享详情
        Share share = shareMapper.selectByPrimaryKey(id);
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
        //整合Feign接口请求方式
        UserDTO userDTO = this.userCenterFeignClient.findById(userId);
        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share, shareDTO);
        //TODO NULL
        shareDTO.setWxNickName(userDTO.getWxNickname());
        return shareDTO;
    }

    /**
     * 根据用户ID进行审核
     *
     * @param id
     * @param auditDTO
     * @return
     */
    public Share auditById(Integer id, ShareAuditDTO auditDTO) {
        // 1. 查询share是否存在，不存在或者当前的audit_status != NOT_YET，那么抛异常
        Share share = this.shareMapper.selectByPrimaryKey(id);
        if (share == null) {
            throw new IllegalArgumentException("参数非法！该分享不存在！");
        }
        if (!Objects.equals("NOT_YET", share.getAuditStatus())) {
            throw new IllegalArgumentException("参数非法！该分享已审核通过或审核不通过！");
        }
        if (AuditStatusEnum.PASS.equals(auditDTO.getAuditStatusEnum())) {
            //发送半消息
            this.rocketMQTemplate.sendMessageInTransaction("tx-add-bouns-group", "add-bouns",
                    MessageBuilder.withPayload(UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build())
                            .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString())
                            .setHeader("share_id", id)
                            .build(), auditDTO);
        } else {
            // 2.进行审核操作
            //auditShare(id, auditDTO);
        }
        // 3. 如果是PASS，那么发送消息给rocketmq，让用户中心去消费，并为发布人添加积分
        this.rocketMQTemplate.convertAndSend("add-bonus", UserAddBonusMsgDTO.builder().userId(share.getUserId()).bonus(50).build());
        return share;
    }

    /**
     * 审核
     *
     * @param id
     * @param auditDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditShare(Integer id, ShareAuditDTO auditDTO) {
        Share share = Share.builder().id(id).auditStatus(auditDTO.getAuditStatusEnum().toString()).reason(auditDTO.getReason()).build();
        share.setAuditStatus(auditDTO.getAuditStatusEnum().toString());
        this.shareMapper.updateByPrimaryKeySelective(share);
    }

    /**
     * 回查审核
     *
     * @param id
     * @param auditDTO
     * @param transactionId
     */
    @Transactional(rollbackFor = Exception.class)
    public void auditByIdWithRocketMqLog(Integer id, ShareAuditDTO auditDTO, String transactionId) {
        this.auditShare(id, auditDTO);
        //记录消息日志
        this.rocketmqTransactionLogMapper.insertSelective(
                RocketmqTransactionLog.builder()
                        .transactionId(transactionId)
                        .log("审核分享...")
                        .build()
        );
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

    /**
     * MQ使用场景：
     * 1.异步处理；
     * 2.流量削峰填谷
     * 3.解耦微服务
     */
}
