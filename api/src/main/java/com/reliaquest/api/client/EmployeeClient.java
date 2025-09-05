package com.reliaquest.api.client;

import com.reliaquest.api.model.Employee;


import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.model.SingleEmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeClient {

    private static final Logger log = LoggerFactory.getLogger(EmployeeClient.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8112/api/v1/employee";

    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees from {}", BASE_URL);
        ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(BASE_URL, EmployeeResponse.class);
        return Optional.ofNullable(response.getBody())
                .map(EmployeeResponse::getData)
                .orElse(Collections.emptyList());
    }

    public Employee getEmployeeById(String id) {
        String url = BASE_URL + "/" + id;
        log.info("Fetching employee by ID: {}", id);
        ResponseEntity<SingleEmployeeResponse> response = restTemplate.getForEntity(url, SingleEmployeeResponse.class);
        return Optional.ofNullable(response.getBody())
                .map(SingleEmployeeResponse::getData)
                .orElse(null);
    }

    public Employee createEmployee(EmployeeInput employee) {
        log.info("Creating new employee: {}", employee);
        SingleEmployeeResponse response =
                restTemplate.postForObject(BASE_URL, employee, SingleEmployeeResponse.class);

        return Optional.ofNullable(response)
                .map(SingleEmployeeResponse::getData)
                .orElseThrow(() -> new RuntimeException("Employee creation failed"));
    }

    public String deleteEmployee(String id) {
        String url = BASE_URL + "/" + id;
        log.info("Deleting employee by ID: {}", id);
        restTemplate.delete(url);
        return id;
    }
}
