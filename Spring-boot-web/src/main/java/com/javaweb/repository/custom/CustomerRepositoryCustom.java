package com.javaweb.repository.custom;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.CustomerSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerRepositoryCustom {
    Page<CustomerEntity> fillAll(CustomerSearchRequest customerSearchRequest, Pageable pageable);

    int countTotalItem(CustomerSearchRequest customerSearchRequest);
}
