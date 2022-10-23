FROM apachepulsar/pulsar:2.9.3

ADD protocols/*.nar /pulsar/protocols/

RUN echo "messagingProtocols=amqp" >> /pulsar/conf/standalone.conf  && \
    echo "protocolHandlerDirectory=./protocols" >> /pulsar/conf/standalone.conf && \
    echo "amqpListeners=amqp://127.0.0.1:5672" >> /pulsar/conf/standalone.conf

