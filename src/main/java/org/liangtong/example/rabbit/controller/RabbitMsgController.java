package org.liangtong.example.rabbit.controller;

import org.liangtong.example.rabbit.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("rabbitmq")
@RestController
public class RabbitMsgController {

    @Autowired
    private MsgProducer sender;

    @RequestMapping("send")
    public void sendMsg(String msg){
        sender.sendMsg(msg);
    }

    @RequestMapping("fixDelay1")
    public void sendFixDelay1Msg(String msg){
        sender.sendFixDelayMsg1(msg);
    }
    @RequestMapping("fixDelay2")
    public void sendFixDelay2Msg(String msg, Integer delay){
        sender.sendFixDelayMsg2(msg, delay);
    }

    @RequestMapping("delay")
    public void sendDelay(String msg, Integer delay){
        sender.delayMsg(msg, delay);
    }
}
