package com.reliaquest.api.model;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeResponse {
    private List<Employee> data;
    private String status;
}
