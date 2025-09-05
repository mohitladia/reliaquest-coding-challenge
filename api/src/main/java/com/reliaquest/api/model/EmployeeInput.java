package com.reliaquest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInput {

    @NotBlank(message = "Name must not be blank")
    @JsonProperty("employee_name")
    private String name;

    @NotNull(message = "Salary must be provided")
    @Min(value = 1, message = "Salary must be greater than zero")
    @JsonProperty("employee_salary")
    private Integer salary;

    @NotNull(message = "Age must be provided")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 75, message = "Age must not exceed 75")
    @JsonProperty("employee_age")
    private Integer age;

    @NotBlank(message = "Title must not be blank")
    @JsonProperty("employee_title")
    private String title;
}
