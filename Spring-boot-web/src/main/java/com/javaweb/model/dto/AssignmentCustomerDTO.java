package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AssignmentCustomerDTO {
    @NotNull(message = "Customer id not be null")
    private Long customerId;
    private List<Long> staffs;
}
