package net.java.employeeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.java.employeeService.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
