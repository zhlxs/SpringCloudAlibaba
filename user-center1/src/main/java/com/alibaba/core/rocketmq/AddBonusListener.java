package com.alibaba.core.rocketmq;

import com.alibaba.core.dao.bonus.BonusEventLogMapper;
import com.alibaba.core.dao.user.UserMapper;
import com.alibaba.core.domain.entity.bonus.BonusEventLog;
import com.alibaba.core.domain.entity.user.User;
import com.alibaba.core.domain.entity.user.dto.message.UserAddBonusMsgDTO;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RocketMQMessageListener(consumerGroup = "consumer-group", topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

    private final UserMapper userMapper;
    private final BonusEventLogMapper bonusEventLogMapper;

    @Override
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        //1.为用户加积分
        Integer userId = userAddBonusMsgDTO.getUserId();
        User user = this.userMapper.selectByPrimaryKey(userId);
        user.setBonus(user.getBonus() + userAddBonusMsgDTO.getBonus());
        //2.记录日志
        this.bonusEventLogMapper.insert(BonusEventLog.builder().userId(userId).value(userAddBonusMsgDTO.getBonus()).
                event("测试").createTime(new Date()).description("添加积分").build());
    }
}
