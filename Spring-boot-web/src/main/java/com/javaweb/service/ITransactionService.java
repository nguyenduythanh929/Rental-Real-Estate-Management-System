package com.javaweb.service;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;

import java.util.List;

public interface ITransactionService {
    List<TransactionEntity> findByCodeAndCustomerId(String code, Long id);

    TransactionEntity addOrUpdateTransaction(TransactionDTO transactionDTO);

    String deleteTransaction(Long id);

    boolean checkAssignedStaff(Long id, Long staffId);
}
