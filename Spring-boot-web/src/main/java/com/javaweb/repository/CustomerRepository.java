package com.javaweb.repository;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerRepositoryCustom {
    Optional<CustomerEntity> findById(Long id);

    CustomerEntity findByPhone(String phone);

    List<CustomerEntity> findByIdIn(List<Long> ids);
}
