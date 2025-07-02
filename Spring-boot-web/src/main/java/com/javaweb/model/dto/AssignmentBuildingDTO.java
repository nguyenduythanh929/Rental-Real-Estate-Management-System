package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AssignmentBuildingDTO {
    @NotNull(message = "Building id not be null")
    private Long buildingId;
    private List<Long> staffs;
}
