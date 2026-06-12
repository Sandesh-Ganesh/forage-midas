package com.jpmc.midascore.entity;
import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord reciever;

    @Column(nullable = false)
    private float amount;

    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender, UserRecord reciever, float amount) {
        this.sender = sender;
        this.reciever = reciever;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, sender='%s', recieverId='%s', amount='%f'", id, sender,reciever, amount);
    }

    public Long getId() {
        return id;
    }

    public UserRecord getSenderId() {
        return sender;
    }

    public UserRecord getRecieverId() {
        return reciever;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
