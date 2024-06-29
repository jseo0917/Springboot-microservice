package net.java.employeeService.service;

import net.java.employeeService.dto.APIResponseDto;
import net.java.employeeService.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    APIResponseDto getEmployeeById(Long id);
}
