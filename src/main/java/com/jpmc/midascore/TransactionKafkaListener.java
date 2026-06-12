package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionKafkaListener {

    private final UserRepository userRepository;

    public TransactionKafkaListener( UserRepository userRepository ){
        this.userRepository = userRepository;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-group",
            properties = {"auto.offset.reset=earliest"}
    )
    @Transactional
    public void consume(Transaction transaction) {
        //System.out.println(transaction);

        // Get Sender
        long senderId = transaction.getSenderId();
        UserRecord sender = userRepository.findById(senderId);

        // Get Recipient
        long recipientId = transaction.getRecipientId();
        UserRecord reciever = userRepository.findById(recipientId);

        // Check both exists
        if(sender != null && reciever != null){

            System.out.println("----Before Transaction ---- ");
            System.out.println(sender);
            System.out.println(reciever);

            float amount = transaction.getAmount();
            float senderBalance = sender.getBalance();
            if( senderBalance - amount >= 0 ){

                sender.setBalance(senderBalance - amount);
                reciever.setBalance(reciever.getBalance() + amount);

                userRepository.save(sender);
                userRepository.save(reciever);

                System.out.println("----After Transaction ---- ");
                System.out.println(sender);
                System.out.println(reciever);
            }else{
                System.out.println("Insufficient Balance");
            }

        }else{
            System.out.println("Invalid Transaction: Sender or Reciver Not found");
        }

    }
}
