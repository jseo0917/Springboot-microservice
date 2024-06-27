package net.java.employeeService.service.Impl;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.java.employeeService.dto.EmployeeDto;
import net.java.employeeService.entity.Employee;
import net.java.employeeService.repository.EmployeeRepository;
import net.java.employeeService.service.EmployeeService;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee(
            employeeDto.getId(),
            employeeDto.getFirstName(),
            employeeDto.getLastName(),
            employeeDto.getEmail()
        );
                
        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeDto savedEmployeeDto = new EmployeeDto(
            savedEmployee.getId(),
            savedEmployee.getFirstName(),
            savedEmployee.getLastName(),
            savedEmployee.getEmail()
        );
        
        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).get();

        EmployeeDto employeeDto = new EmployeeDto(
            employee.getId(),
            employee.getFirstName(),
            employee.getLastName(),
            employee.getEmail()
        );

        return employeeDto;
    }
}
