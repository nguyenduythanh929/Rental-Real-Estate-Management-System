package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.Status;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerSearchResponse toCustomerSearchResponse(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerSearchResponse.class);
    }

    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntityOld = null;
        if(customerDTO.getId() != null){
            customerEntityOld = customerRepository.findById(customerDTO.getId()).get();
        }
        CustomerEntity customerEntityNew = modelMapper.map(customerDTO, CustomerEntity.class);
        customerEntityNew.setStatus(Status.CHUA_XU_LY.getStatusName());
        if(StringUtils.check(customerDTO.getStatus())){
            String status = Status.valueOf(customerDTO.getStatus()).getStatusName();
            customerEntityNew.setStatus(status);
        }
        if(customerEntityOld != null){
            customerEntityNew.setUsers(customerEntityOld.getUsers());
            customerEntityNew.setCreatedDate(customerEntityOld.getCreatedDate());
            customerEntityNew.setCreatedBy(customerEntityOld.getCreatedBy());
        }
        customerEntityNew.setIsActive(1);
        return customerEntityNew;
    }
}
