package org.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        // Java Code

        // create connection
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost("vhost1");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "ex";
        String queue = "qu";

        // exchange declare
        System.out.println("Declaring the exchange ");
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT, true, false, false, null);

        // queue declare and bind
        System.out.println("Declaring the queue ");
        channel.queueDeclare(queue, true, false, false, null);

        System.out.println("Binding the queue ");
        channel.queueBind(queue, exchange, "");

        // publish some messages
        for (int i = 0; i < 100; i++) {
            String msg = "hello - " + i;
            System.out.println("Sending msg: " + msg);
            channel.basicPublish(exchange, "", null, msg.getBytes());
        }

        // consume messages
        CountDownLatch countDownLatch = new CountDownLatch(100);
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive msg: " + new String(body));
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();

        // release resource
        channel.close();
        connection.close();

    }
}