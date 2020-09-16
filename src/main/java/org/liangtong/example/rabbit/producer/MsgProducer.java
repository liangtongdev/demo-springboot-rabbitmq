package org.liangtong.example.rabbit.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.liangtong.example.rabbit.constant.MQConstant.*;

@Slf4j
@Component
public class MsgProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend(EXCHANGE_IM_NAME,
                QUEUE_IM_NAME,
                msg,
                message -> {
                    message.getMessageProperties().setDelay(10000);
                    return message;
                }
        );
    }


    public void sendFixDelayMsg1(String msg){
        log.info("发送延时消息：{}", msg);
        rabbitTemplate.convertAndSend(EXCHANGE_DELAY_FIX_NAME,
                ROUTING_KEY_DELAY_FIX_1_NAME,
                msg,
                new CorrelationData()
        );
    }

    public void sendFixDelayMsg2(String msg, Integer delay){
        log.info("发送延时消息：{} 延时{}秒", msg, delay);
        rabbitTemplate.convertAndSend(EXCHANGE_DELAY_FIX_NAME,
                ROUTING_KEY_DELAY_FIX_2_NAME,
                msg,
                message -> {
                    message.getMessageProperties().setExpiration(String.valueOf(delay * 1000) );
                    return message;
                }
        );
    }

    public void delayMsg(String msg, Integer delay){
        log.info("发送延时消息：{} 延时{}秒", msg, delay);
        rabbitTemplate.convertAndSend(EXCHANGE_DELAY_NAME,
                ROUTING_KEY_DELAY_NAME,
                msg,
                message -> {
                    message.getMessageProperties().setDelay(delay * 1000);
                    return message;
                }
        );
    }
}
