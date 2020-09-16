package org.liangtong.example.rabbit.consumer;


import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

import static org.liangtong.example.rabbit.constant.MQConstant.QUEUE_IM_NAME;
/**
 * @author liangtong
 * @Description 即使消息消费端
 * @date 2020/09/10 16:40
 */
@Slf4j
@Component
public class ImConsumer {

    @RabbitListener(queues = QUEUE_IM_NAME)
    public void receiveIm(Message message,
                          Channel channel) throws IOException {
        log.info("收到即时消息: " + message);
        boolean ack = true;
        Exception exception = null;
        try {
            int random = new Random().nextInt( 2 );
            if (random == 1){
                throw new RuntimeException("发生异常！");
            }
        } catch (Exception e) {
            ack = false;
            exception = e;
        } finally {

        }
        if (!ack) {
            log.error("消息消费发生异常，error msg:{}", exception.getMessage(), exception);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
