package org.liangtong.example.rabbit.constant;

public class MQConstant {

    /** Exchange 交换器**/
    public static final String EXCHANGE_IM_NAME = "exchange.demo.im";
    public static final String EXCHANGE_DELAY_FIX_NAME = "exchange.demo.fix.delay";
    public static final String EXCHANGE_DELAY_NAME = "exchange.demo.delay";
    public static final String EXCHANGE_DEAD_NAME = "exchange.demo.dead";

    /** 路由定义 **/
    public static final String ROUTING_KEY_DELAY_NAME = "routingkey.demo.delay";
    public static final String ROUTING_KEY_DELAY_FIX_1_NAME = "routingkey.demo.fix.1.delay";
    public static final String ROUTING_KEY_DELAY_FIX_2_NAME = "routingkey.demo.fix.2.delay";
    public static final String ROUTING_KEY_DEAD_NAME = "routingkey.demo.dead";


    /** 队列定义 **/
    public static final String QUEUE_IM_NAME = "queue.demo.im";
    public static final String QUEUE_DELAY_FIX_1_NAME = "queue.demo.fix.1.delay";
    public static final String QUEUE_DELAY_FIX_2_NAME = "queue.demo.fix.2.delay";
    public static final String QUEUE_DELAY_NAME = "queue.demo.delay";
    public static final String QUEUE_DEAD_NAME = "queue.demo.dead";

}
