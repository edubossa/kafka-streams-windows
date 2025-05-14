package com.kafka.streams.windows.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Properties;


/**
 * Crie um @Service para iniciar o Kafka Streams:
 */
@Service
public class PedidosStreamService {

    @Autowired
    private StreamsBuilder streamsBuilder;

    private KafkaStreams streams;

    private Properties props;

    @PostConstruct
    public void start() {
        streams = new KafkaStreams(streamsBuilder.build(), new StreamsConfig(props));
        streams.start();
    }

    @PreDestroy
    public void stop() {
        streams.close();
    }

}
