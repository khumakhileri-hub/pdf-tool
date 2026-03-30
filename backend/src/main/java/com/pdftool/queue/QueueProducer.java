package com.pdftool.queue;


import com.pdftool.model.Job;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Job job) {
        rabbitTemplate.convertAndSend("pdf-queue", job);
    }
}