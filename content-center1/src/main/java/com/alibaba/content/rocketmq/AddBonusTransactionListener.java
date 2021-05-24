package com.alibaba.content.rocketmq;

import com.alibaba.content.dao.trans.RocketmqTransactionLogMapper;
import com.alibaba.content.domain.dto.content.ShareAuditDTO;
import com.alibaba.content.domain.entity.trans.RocketmqTransactionLog;
import com.alibaba.content.service.content.ShareService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@RocketMQTransactionListener(txProducerGroup = "tx-add-bouns-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener
{

	private final ShareService shareService;
	private final RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

	/**
	 * 执行本地事务
	 *
	 * @param message
	 * @param o
	 * @return
	 */
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o)
	{
		MessageHeaders headers = message.getHeaders();
		String transId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
		Integer shareId = Integer.valueOf((String) message.getHeaders().get("share_id"));
		try
		{
			shareService.auditByIdWithRocketMqLog(shareId, (ShareAuditDTO) o, transId);
			return RocketMQLocalTransactionState.COMMIT;
		}
		catch (Exception e)
		{
			return RocketMQLocalTransactionState.ROLLBACK;
		}
	}

	/**
	 * 本地事务的检查接口(回查接口)
	 *
	 * @param message
	 * @return
	 */
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message)
	{

		MessageHeaders headers = message.getHeaders();
		String transId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);

		RocketmqTransactionLog transactionLog = this.rocketmqTransactionLogMapper.selectOne(RocketmqTransactionLog.builder().
				transactionId(transId).build());
		if (transactionLog != null)
		{
			return RocketMQLocalTransactionState.COMMIT;
		}
		return RocketMQLocalTransactionState.ROLLBACK;
	}
}
