package com.navercorp.pinpoint.collector.rabbit;

import com.navercorp.pinpoint.collector.rabbit.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author keith
 * @version v1.0.0
 * @date 2020/6/24
 * @implNote No Description
 */
@Component("pinpointProducer")
public class PinpointProducer {
    private final RabbitMQConfig rabbitMQConfig;
    private final RabbitTemplate rabbitTemplate;

    public PinpointProducer(RabbitMQConfig rabbitMQConfig, RabbitTemplate rabbitTemplate) {
        this.rabbitMQConfig = rabbitMQConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProducer(String message) {
        // 生产者发送消息（因为在配置文件里面已经为rabbitTemplate指定了交换机和routing，所以可以省去它们）
        // rabbitTemplate.convertAndSend(message);

        // 生产者往solutionInfo_exchange这个交换机，这个info_queue_key路由中发送消息
        rabbitTemplate.convertAndSend(rabbitMQConfig.getExchangeName(), rabbitMQConfig.getRoutingKey(), message);
    }
}
