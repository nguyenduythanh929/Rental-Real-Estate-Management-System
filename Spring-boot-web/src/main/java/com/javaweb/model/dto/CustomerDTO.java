package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CustomerDTO extends AbstractDTO {
    @NotBlank(message = "Full Name Not Be Blank")
    private String fullName;
    @NotBlank(message = "Phone Number Not Be Blank")
    private String phone;
    private String email;
    private String companyName;
    private String demand;
    private String status;
}
