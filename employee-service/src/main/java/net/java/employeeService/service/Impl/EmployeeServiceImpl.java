package net.java.employeeService.service.Impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.java.employeeService.dto.APIResponseDto;
import net.java.employeeService.dto.DepartmentDto;
import net.java.employeeService.dto.EmployeeDto;
import net.java.employeeService.dto.OrganizationDto;
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
    // private RestTemplate restTemplate;
    private WebClient webClient;
    // private APIClient apiClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // Employee employee =  EmployeeMapper.mapToEmployee(employeeDto);
        
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());

        if(optionalEmployee.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for the user");
        }

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee, EmployeeDto.class);
        
        return savedEmployeeDto;
    }

    @Override
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public APIResponseDto getEmployeeById(Long id) {
        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id)
            );

        // ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode()
        // , DepartmentDto.class);

        DepartmentDto departmentDto = webClient.get()
            .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
            .retrieve()
            .bodyToMono(DepartmentDto.class)
            .block();

        OrganizationDto organizationDto = webClient.get()
            .uri("http://localhost:8080/api/organizations/" + employee.getOrganizationCode())
            .retrieve()
            .bodyToMono(OrganizationDto.class)
            .block();
        // DepartmentDto departmentDto = responseEntity.getBody();

        // DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        
        APIResponseDto apiResponseDto = new APIResponseDto();
    
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);
        return apiResponseDto;
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

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment() method");

        Employee employee = employeeRepository.findById(employeeId).get();
        
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("Department Default");
        departmentDto.setDepartmentCode("Temp");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Temporary default");

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();

        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        return apiResponseDto;
    }
}
