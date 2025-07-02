package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public TransactionEntity toTransactionEntity(TransactionDTO transactionDTO){
        TransactionEntity transactionOld = null;
        if(transactionDTO.getId() != null){
            transactionOld = transactionRepository.findById(transactionDTO.getId()).get();
        }
        TransactionEntity transactionNew = modelMapper.map(transactionDTO, TransactionEntity.class);
        CustomerEntity customer = customerRepository.findById(transactionDTO.getCustomerId()).get();
        transactionNew.setCustomer(customer);
        if(transactionOld != null){
            transactionNew.setCustomer(transactionOld.getCustomer());
            transactionNew.setCreatedBy(transactionOld.getCreatedBy());
            transactionNew.setCreatedDate(transactionOld.getCreatedDate());
        }
        return transactionNew;
    }
}
