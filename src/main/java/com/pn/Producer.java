package com.pn;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    private FanoutExchangeChannel channel;
    public void start() throws IOException, TimeoutException {
        //create connection
        Connection connection= ConnectionManager.createConnection();

        //create channel
        channel = new FanoutExchangeChannel(connection, Constant.EXCHANGE_NAME);

        //create fanout exchange
        channel.declareExchange();

        //create queues
        channel.declareQueues(Constant.DEV_QUEUE_NAME, Constant.GENERAL_QUEUE_NAME,
                            Constant.MANAGER_QUEUE_NAME);

        //binding queues without key
        channel.performQueuesBinding(Constant.MANAGER_QUEUE_NAME);
        channel.performQueuesBinding(Constant.GENERAL_QUEUE_NAME);
        channel.performQueuesBinding(Constant.DEV_QUEUE_NAME);
    }

    public void send(String message) throws IOException {
        //send mesage
        channel.publishMessage(message);
    }
}
