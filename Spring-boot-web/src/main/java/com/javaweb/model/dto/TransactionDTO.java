package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TransactionDTO extends AbstractDTO{
    @NotBlank(message = "Code not be blank")
    private String code;
    @NotBlank(message = "Note not be blank")
    private String note;
    @NotNull(message = "Customer Id not be null")
    private Long customerId;
}
