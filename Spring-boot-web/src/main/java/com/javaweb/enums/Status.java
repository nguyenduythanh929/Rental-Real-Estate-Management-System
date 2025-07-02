package com.javaweb.enums;

import java.util.Map;
import java.util.TreeMap;

public enum Status {
    CHUA_XU_LY("Chưa xử lý"),
    DANG_XU_LY("Đang xử lý"),
    DA_XU_LY("Đã xử lý");

    private final String statusName;

    Status(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public static Map<String, String> getStatus() {
        Map<String, String> statuses = new TreeMap<>();
        for (Status status : Status.values()) {
            statuses.put(status.name(), status.getStatusName());
        }
        return statuses;
    }
}
