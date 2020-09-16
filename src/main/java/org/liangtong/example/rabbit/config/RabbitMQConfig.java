package org.liangtong.example.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.liangtong.example.rabbit.constant.MQConstant.*;

@Configuration
public class RabbitMQConfig {


    // 声明交换器
    @Bean("imExchange")
    public FanoutExchange imExchange() {
        return new FanoutExchange(EXCHANGE_IM_NAME);
    }

    @Bean("fixDelayExchange")
    public DirectExchange fixDelayExchange() {
        return new DirectExchange(EXCHANGE_DELAY_FIX_NAME);
    }
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(EXCHANGE_DELAY_NAME, "x-delayed-message", true, false, args);
    }

    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(EXCHANGE_DEAD_NAME);
    }

    // 声明队列
    @Bean("imQueue")
    public Queue imQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //绑定死信队列
        args.put("x-dead-letter-exchange", EXCHANGE_DEAD_NAME);
        //当前队列的死信路由Key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_DEAD_NAME);
        // x-message-ttl  声明队列的TTL
        return QueueBuilder.durable(QUEUE_IM_NAME).withArguments(args).build();
    }

    @Bean("fixDelayQueue1")
    public Queue fixDelayQueue1() {
        Map<String, Object> args = new HashMap<>(3);
        //绑定死信队列
        args.put("x-dead-letter-exchange", EXCHANGE_DEAD_NAME);
        //当前队列的死信路由Key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_DEAD_NAME);
        // x-message-ttl  声明队列的TTL
        args.put("x-message-ttl", 60000);
        return QueueBuilder.durable(QUEUE_DELAY_FIX_1_NAME).withArguments(args).build();
    }

    @Bean("fixDelayQueue2")
    public Queue fixDelayQueue2() {
        Map<String, Object> args = new HashMap<>(3);
        //绑定死信队列
        args.put("x-dead-letter-exchange", EXCHANGE_DEAD_NAME);
        //当前队列的死信路由Key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_DEAD_NAME);
        return QueueBuilder.durable(QUEUE_DELAY_FIX_2_NAME).withArguments(args).build();
    }

    @Bean("delayQueue")
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //绑定死信队列
        args.put("x-dead-letter-exchange", EXCHANGE_DEAD_NAME);
        //当前队列的死信路由Key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_DEAD_NAME);
        return QueueBuilder.durable(QUEUE_DELAY_NAME).withArguments(args).build();
    }

    @Bean("deadQueue")
    public Queue deadQueue() {
        return new Queue(QUEUE_DEAD_NAME);
    }

    // 绑定关系
    @Bean
    public Binding imBinding(@Qualifier("imQueue") Queue queue,
                             @Qualifier("imExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange);
    }

    @Bean
    public Binding fixDelay1Binding(@Qualifier("fixDelayQueue1") Queue queue,
                                   @Qualifier("fixDelayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_DELAY_FIX_1_NAME);
    }
    @Bean
    public Binding fixDelay2Binding(@Qualifier("fixDelayQueue2") Queue queue,
                                    @Qualifier("fixDelayExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_DELAY_FIX_2_NAME);
    }

    @Bean
    public Binding delayBinding(@Qualifier("delayQueue") Queue queue,
                                @Qualifier("delayExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_DELAY_NAME).noargs();
    }

    @Bean
    public Binding deadBinding(@Qualifier("deadQueue") Queue queue,
                               @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_DEAD_NAME);
    }
}
