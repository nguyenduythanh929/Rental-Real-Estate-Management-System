package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum Transaction {
    CSKH("Chăm sóc khách hàng"),
    DDX("Dẫn đi xem");

    private String transactionName;
    Transaction(String transactionName){ this.transactionName = transactionName;}
    public String getTransactionName(){
        return transactionName;
    }
    public static Map<String, String> getTransaction() {
        Map<String, String> transactions = new TreeMap<>();
        for (Transaction transaction : Transaction.values()) {
            transactions.put(transaction.name(), transaction.getTransactionName());
        }
        return transactions;
    }

}
