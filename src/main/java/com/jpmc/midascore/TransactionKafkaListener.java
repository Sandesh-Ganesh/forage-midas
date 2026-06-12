package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionKafkaListener {

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-group",
            properties = {"auto.offset.reset=earliest"}
    )
    public void consume(Transaction transaction) {
        System.out.println(transaction.getAmount());
    }
}
