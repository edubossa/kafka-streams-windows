package com.kafka.streams.windows.controler;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Consultando o Estado (Interactive Queries)
 */
@RestController
public class PedidosController {

    @Autowired
    private KafkaStreams streams;

    @GetMapping("/pedidos/ultimo-minuto")
    public long getPedidosUltimoMinuto() {
        ReadOnlyWindowStore<String, Long> store =
                streams.store(StoreQueryParameters.fromNameAndType("pedidos-por-minuto-store", QueryableStoreTypes.windowStore()));

        // Pega a janela mais recente (último minuto)
        long nowMs = System.currentTimeMillis();
        java.time.Instant now = java.time.Instant.ofEpochMilli(nowMs);
        java.time.Instant minuteAgo = java.time.Instant.ofEpochMilli(nowMs - 60000);
        WindowStoreIterator<Long> iterator = store.fetch("pedidos-minuto", minuteAgo, now);

        if (iterator.hasNext()) {
            return iterator.next().value; // Retorna a contagem
        }

        return 0;
    }
}
