package net.java.employeeService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.java.employeeService.dto.APIResponseDto;
import net.java.employeeService.dto.EmployeeDto;
import net.java.employeeService.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    EmployeeService employeeService;    

    @PostMapping
    ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDto),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<APIResponseDto> getEmployee(@PathVariable("id") Long id){
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }
}
