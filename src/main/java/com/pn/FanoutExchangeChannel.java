package com.pn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FanoutExchangeChannel {

    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public FanoutExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.connection = connection;
        this.exchangeName = exchangeName;
        this.channel = connection.createChannel();
    }

    public void declareExchange() throws IOException {
        //exchangeDeclare(exchange, builinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
    }

    public void  declareQueues(String ...queueName) throws IOException {
        for(String queue: queueName){
            //queueDeclare (queuename, durable, exclusive, autoDelete, argument)
            channel.queueDeclare(queue, true, false, false, null);
        }
    }

    public void performQueuesBinding(String queueName) throws IOException {
        //create bindings - (queue, exchange, routing key)
        channel.queueBind(queueName, exchangeName, "");
    }

    public void publishMessage(String message) throws IOException {
        //basicPublish: (exchange, routingKey, basicProperties, body)
        System.out.println("[send]: " + message);
        channel.basicPublish(exchangeName, "", null,
                message.getBytes(StandardCharsets.UTF_8));
    }

    public void subscribeMessage(String queueName) throws IOException {
        //basicConsume: (exchange, autoAck, deliverCallback cancelCallback)
        channel.basicConsume(queueName,((consumerTag, message)->{
            System.out.println("[Received] [" + queueName+"]: " + new String(message.getBody()));
        }), consumerTag->{
            System.out.println(consumerTag);
        });

    }


}
