package net.java.employeeService.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import net.java.employeeService.dto.EmployeeDto;
import net.java.employeeService.entity.Employee;
import net.java.employeeService.exception.EmailAlreadyExistsException;
import net.java.employeeService.exception.ErrorDetails;
import net.java.employeeService.exception.ResourceNotFoundException;
import net.java.employeeService.repository.EmployeeRepository;
import net.java.employeeService.service.EmployeeService;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // Employee employee =  EmployeeMapper.mapToEmployee(employeeDto);
        
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);
        
        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
            );

        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if(optionalEmployee.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for the user");
        }

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        return employeeDto;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException exception, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(
            LocalDateTime.now(),
            exception.getMessage(),
            webRequest.getDescription(false),
            "USER_EMAIL_ALREADY_EXISTS" 
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
