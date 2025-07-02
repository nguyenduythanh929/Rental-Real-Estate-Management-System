package com.javaweb.service;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    List<CustomerSearchResponse> fillAll(CustomerSearchRequest customerSearchRequest, Pageable pageable);

    int countTotalItem(CustomerSearchRequest customerSearchRequest);

    CustomerDTO findById(Long id);

    CustomerEntity createCustomer(CustomerDTO customerDTO) throws Exception;

    CustomerEntity updateCustomer(CustomerDTO customerDTO) throws Exception;

    String deleteCustomer(List<Long> ids);

    boolean checkAssignedStaff(Long id, Long staffId);
}
