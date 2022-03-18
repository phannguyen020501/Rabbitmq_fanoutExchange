package com.pn;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class App {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //create producer
        Producer producer = new Producer();
        producer.start();

        //send one message to exchange, it will be forward to all queue
        producer.send("hello pn");

        //create consumer
        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();

        TimeUnit.SECONDS.sleep(2);

        //try to send a second message
        producer.send("hello pn again");
    }



}
