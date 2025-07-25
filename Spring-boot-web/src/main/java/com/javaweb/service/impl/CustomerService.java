package com.javaweb.service.impl;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Override
    public List<CustomerSearchResponse> fillAll(CustomerSearchRequest customerSearchRequest, Pageable pageable) {
        Page<CustomerEntity> customer = customerRepository.fillAll(customerSearchRequest, pageable);
        List<CustomerEntity> customers = customer.getContent();
        List<CustomerSearchResponse> customerSearchResponses = new ArrayList<>();
        for(CustomerEntity cus : customers){
            CustomerSearchResponse customerSearchResponse = customerConverter.toCustomerSearchResponse(cus);
            customerSearchResponses.add(customerSearchResponse);
        }
        return customerSearchResponses;
    }

    @Override
    public int countTotalItem(CustomerSearchRequest customerSearchRequest) {
        return customerRepository.countTotalItem(customerSearchRequest);
    }

    @Override
    public CustomerDTO findById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customerConverter.toCustomerDTO(customerEntity);
    }

    @Override
    public CustomerEntity createCustomer(CustomerDTO customerDTO) throws Exception{
        CustomerEntity customerExist = customerRepository.findByPhone(customerDTO.getPhone());
        if (customerExist != null && !customerExist.getId().equals(customerDTO.getId())) {
            throw new Exception("Customer Phone Number Existed");
        }
        CustomerEntity customerEntity = customerConverter.toCustomerEntity(customerDTO);
        customerRepository.save(customerEntity);
        return customerEntity;
    }

    @Override
    public CustomerEntity updateCustomer(CustomerDTO customerDTO) throws Exception{
        CustomerEntity customerExist = customerRepository.findByPhone(customerDTO.getPhone());
        if (customerExist != null && !customerExist.getId().equals(customerDTO.getId())) {
            throw new Exception("Customer Phone Number Existed");
        }
        CustomerEntity customerEntity = customerConverter.toCustomerEntity(customerDTO);
        customerRepository.save(customerEntity);
        return customerEntity;
    }

    @Override
    public String deleteCustomer(List<Long> ids) {
        List<CustomerEntity> customerEntities = customerRepository.findByIdIn(ids);
        for(CustomerEntity customerEntity : customerEntities){
            customerEntity.setIsActive(0);
        }
        customerRepository.saveAll(customerEntities);
        return SystemConstant.DELETE_SUCCESS;
    }

    @Override
    public boolean checkAssignedStaff(Long id, Long staffId) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getUsers().stream().noneMatch(it -> Objects.equals(it.getId(), staffId));
    }
}
