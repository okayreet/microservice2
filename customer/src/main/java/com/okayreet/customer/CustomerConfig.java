package com.okayreet.customer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



//    @Bean
//    public TopicExchange internalTopicExchange() {
//        return new TopicExchange("customerExchange");
//    }
//
//    @Bean
//    public Queue notificationQueue() {
//        return new Queue("customerQueue");
//    }
//
//    @Bean
//    public Binding internalToNotificationBinding() {
//        return BindingBuilder
//                .bind(notificationQueue())
//                .to(internalTopicExchange())
//                .with("customerRoutingKey");
//    }

}
