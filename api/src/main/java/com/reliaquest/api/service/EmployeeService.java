package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.reliaquest.api.model.EmployeeInput;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeClient client;
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    public List<Employee> getAllEmployees() {
        return client.getAllEmployees();
    }

    public Employee getEmployeeById(String id) {
        log.info("Getting employee by id {}", id);
        return client.getEmployeeById(id);
    }

    public List<Employee> searchEmployeesByName(String abc) {
        log.info("Searching employee by name {}", abc);
        return client.getAllEmployees().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(abc.toLowerCase()))
                .collect(Collectors.toList());
    }

    public int getHighestSalary() {
        log.info("Getting highest salary");
        return client.getAllEmployees().stream()
                .mapToInt(Employee::getSalary)
                .max()
                .orElse(0);
    }

    public List<String> getTop10HighestEarningEmployeeNames() {
        log.info("Getting top 10 employee names");
        return client.getAllEmployees().stream()
                .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
                .limit(10)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }

    public Employee createEmployee(EmployeeInput employee) {
        return client.createEmployee(employee);
    }

    public String deleteEmployeeById(String id) {
        return client.deleteEmployee(id);
    }
}
