package com.reliaquest.api;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void sleep() throws InterruptedException {
        Thread.sleep(5000); // half a second between tests
    }

    @Test
    void testCreateAndGetEmployee() {
        EmployeeInput input = new EmployeeInput("Alice", 75000, 29, "Developer");

        ResponseEntity<Employee> createResponse = restTemplate.postForEntity(
                "/employees", input, Employee.class);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Employee created = createResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Alice");

        // fetch by id
        ResponseEntity<Employee> getResponse = restTemplate.getForEntity(
                "/employees/" + created.getId(), Employee.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("Alice");
    }

    @Test
    void testGetAllEmployees() {
        ResponseEntity<Employee[]> response =
                restTemplate.getForEntity("/employees", Employee[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testSearchEmployeesByName() {
        ResponseEntity<Employee[]> response =
                restTemplate.getForEntity("/employees/search/Alice", Employee[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetHighestSalary() {
        ResponseEntity<Integer> response =
                restTemplate.getForEntity("/employees/highestSalary", Integer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isGreaterThan(0);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        ResponseEntity<List> response =
                restTemplate.getForEntity("/employees/topTenHighestEarningEmployeeNames", List.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testDeleteEmployee() {
        // First create
        EmployeeInput input = new EmployeeInput("Bob", 65000, 33, "Tester");
        Employee created = restTemplate.postForEntity("/employees", input, Employee.class).getBody();

        // Then delete
        ResponseEntity<String> deleteResponse =
                restTemplate.exchange("/employees/" + created.getId(),
                        HttpMethod.DELETE, null, String.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(deleteResponse.getBody()).contains("Bob");
    }
}
