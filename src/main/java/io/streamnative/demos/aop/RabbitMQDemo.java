package io.streamnative.demos.aop;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class RabbitMQDemo {

    private final static String QUEUE_NAME = "example_queue";
    private final static String AMQP_HOST = "192.168.1.121";
    private final static int AMQP_PORT = 5672;
    private final static String VIRTUAL_HOST_NAME = "vhost1";
    private static final String EXCHANGE_NAME = "ex";

    private static final String RABBIT_CLIENT_VERSION = "3.6.3";

    static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(AMQP_HOST);
        factory.setPort(AMQP_PORT);
        factory.setVirtualHost(VIRTUAL_HOST_NAME);
        return factory;
    }

    public static void main(String[] args) {
        // Create a producer thread
        Thread producerThread = new Thread(() -> {
            try {
                // Create a connection to the RabbitMQ server
                Connection connection = getConnectionFactory().newConnection();
                Channel channel = connection.createChannel();
                // channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true, false, false, null);

                // Declare a queue
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                // Produce messages to the queue
                for (int i = 1; i <= 999999; i++) {
                    String message = String.format("Message %d sent with RabbitMQ client library version %s",
                            i, RABBIT_CLIENT_VERSION);
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    Thread.sleep(1000); // Simulate some delay
                }

                // Close the channel and connection
                channel.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Create a consumer thread
        Thread consumerThread = new Thread(() -> {
            try {
                // Create a connection to the RabbitMQ server
                Connection connection = getConnectionFactory().newConnection();
                Channel channel = connection.createChannel();

                // Declare a queue
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                            throws IOException, UnsupportedEncodingException {
                        String message = new String(body, "UTF-8");
                        System.out.println("Received '" + message + "'");
                    }
                };

                // Start consuming messages from the queue
                channel.basicConsume(QUEUE_NAME, true, consumer);


                // Keep the consumer running (since it runs in its own thread)
                while (true) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the producer and consumer threads
        producerThread.start();
        consumerThread.start();
    }
}
