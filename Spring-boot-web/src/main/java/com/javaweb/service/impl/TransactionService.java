package com.javaweb.service.impl;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.TransactionConverter;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.repository.TransactionRepository;
import com.javaweb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionConverter transactionConverter;

    @Override
    public List<TransactionEntity> findByCodeAndCustomerId(String code, Long id) {
        return transactionRepository.findByCodeAndCustomer_Id(code, id);
    }

    @Override
    public TransactionEntity addOrUpdateTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transaction = transactionConverter.toTransactionEntity(transactionDTO);
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public String deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
        return SystemConstant.DELETE_SUCCESS;
    }

    @Override
    public boolean checkAssignedStaff(Long id, Long staffId) {
        TransactionEntity transaction = transactionRepository.findById(id).get();
        return transaction.getCustomer().getUsers().stream()
                .anyMatch(user -> user.getId().equals(staffId));
    }
}
