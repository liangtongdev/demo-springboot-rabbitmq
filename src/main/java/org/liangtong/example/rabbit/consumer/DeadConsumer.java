package org.liangtong.example.rabbit.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.liangtong.example.rabbit.constant.MQConstant.QUEUE_DEAD_NAME;
/**
 * @author liangtong
 * @Description 死信消息消费端
 * @date 2020/09/10 16:50
 */
@Slf4j
@Component
public class DeadConsumer {
    @RabbitListener(queues = QUEUE_DEAD_NAME)
    public void receiveDead(Message message,
                            Channel channel) throws IOException {
        log.info("收到死信消息: " + message);
//        log.info("死信消息properties：{}", message.getMessageProperties());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
