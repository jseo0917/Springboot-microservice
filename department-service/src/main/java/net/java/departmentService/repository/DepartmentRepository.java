package net.java.departmentService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.java.departmentService.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
    Department findByDepartmentCode(String departmentCode);
}
