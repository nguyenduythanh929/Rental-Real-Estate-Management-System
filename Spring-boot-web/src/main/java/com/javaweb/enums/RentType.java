package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum RentType {

    NGUYEN_CAN("Nguyên căn"),
    TANG_TRET("Tầng trệt"),
    NOI_THAT("Nội thất");

    private final String name;

    private RentType(String name) {
        this.name = name;
    }

    public static Map<String, String> getType() {
        Map<String, String> rentTypes = new TreeMap<>();
        for (RentType rentType : RentType.values()) {
            rentTypes.put(rentType.toString(), rentType.name);
        }
        return rentTypes;
    }
}
