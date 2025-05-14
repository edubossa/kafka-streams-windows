package com.kafka.streams.windows.streams;

import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

/**
 * Definindo o Stream Processor
 */
@Configuration
public class PedidosStreamConfig {

    @Bean
    public KStream<String, String> processPedidos(StreamsBuilder builder) {
        // Lê do tópico "pedidos-novos" (chave: pedidoId, valor: JSON do pedido)
        KStream<String, String> pedidosStream = builder.stream("pedidos-novos");

        // Agrupa por minuto (Tumbling Window de 1 minuto)
        KTable<Windowed<String>, Long> pedidosPorMinuto = pedidosStream
                .groupBy((key, value) -> "pedidos-minuto") // Agrupa todos os pedidos em uma única chave
                .windowedBy(TimeWindows.of(Duration.ofMinutes(1))) // Janela de 1 minuto
                .count(Materialized.as("pedidos-por-minuto-store"));

        // Escreve o resultado em um novo tópico
        pedidosPorMinuto
                .toStream()
                .map((windowedKey, count) -> new KeyValue<>(windowedKey.key(), count.toString()))
                .to("pedidos-por-minuto");

        return pedidosStream;
    }

}
