package com.jgw.supercodeplatform.marketing.mq.config;

import com.jgw.supercodeplatform.marketing.constants.RabbitMqQueueName;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 消息队列配置文件
 *
 * @author liujianqiang
 * @date 2018年1月16日
 */
@Configuration
public class RabbitConfig {


    @Bean(name = "publishBatch")   //SEARCH_TEST_QUEUE  || SEARCH_COUNT_QUEUE
    public Queue publishBatch() {
        return new Queue(RabbitMqQueueName.PUSH_BATCH_DATA_QUEUE);
    }


    @Bean(name = RabbitMqQueueName.UN_PUSH_BATCH_DATA_QUEUE)
    public Queue unbingdProductAndCode() {
        return new Queue(RabbitMqQueueName.UN_PUSH_BATCH_DATA_QUEUE);
    }

    /**
     * 码管理
     * template.convertAndSend(RabbitConfig.PUB_SUB_EXCHANGE, "", i);
     *
     * @return
     */
    @Bean(RabbitMqQueueName.PUB_SUB_EXCHANGE)
    public Exchange buildunbindingExchange() {
        return ExchangeBuilder.fanoutExchange(RabbitMqQueueName.PUB_SUB_EXCHANGE).build();
    }


    @Bean
    public Binding bindingQueueAndunbindingExchange(@Qualifier(RabbitMqQueueName.UN_PUSH_BATCH_DATA_QUEUE) Queue queue, @Qualifier(RabbitMqQueueName.PUB_SUB_EXCHANGE) Exchange exchange) {
        // with是路由key,这模式默认为空就好
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();
    }


}


/**
 * public static final String PUBLISH_SUBSCRIBE_EXCHANGE_DIRCT = "direct";
 * <p>
 * 交换机类型:[主题]
 * 功能:消息路由
 * 描述: 相关route队列都可接收
 * <p>
 * public static final String PUBLISH_SUBSCRIBE_EXCHANGE_TOPIC= "topic";
 * public static final String PUBLISH_SUBSCRIBE_EXCHANGE_HEARDERS= "headers";
 * public static final String PUBLISH_SUBSCRIBE_EXCHANGE_FANOUT = "fanout";
 */