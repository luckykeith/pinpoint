package com.navercorp.pinpoint.collector.rabbit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 *
 * @author keith
 * @version v1.0.0
 * @date 2020/6/24
 * @implNote No Description
 */
@Configuration
public class RabbitMQConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public final static String QUEUE_NAME = "pinpoint.span.data";
    public final static String EXCHANGE_NAME = "pinpoint";
    public final static String ROUTING_KEY = "pinpoint.span.data.key";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("172.38.50.130", 8672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("pp");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory rabbitConnectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
        // 注意，autoStartup 必须设置为 true，否则 Spring 容器不会加载 RabbitAdmin 类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        return new RabbitTemplate(rabbitConnectionFactory);
    }
}
