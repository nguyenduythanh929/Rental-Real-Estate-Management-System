package com.javaweb.service.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IAssignmentCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssignmentCustomerService implements IAssignmentCustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateAssignment(AssignmentCustomerDTO assignmentCustomerDTO) {
        List<UserEntity> users = userRepository.findByIdIn(assignmentCustomerDTO.getStaffs());
        CustomerEntity customer = customerRepository.findById(assignmentCustomerDTO.getCustomerId()).get();
        customer.setUsers(users);
        customerRepository.save(customer);
    }
}
