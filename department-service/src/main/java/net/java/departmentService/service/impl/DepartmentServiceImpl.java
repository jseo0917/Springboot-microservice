package net.java.departmentService.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.java.departmentService.dto.DepartmentDto;
import net.java.departmentService.entity.Department;
import net.java.departmentService.exception.ResourceNotFoundException;
import net.java.departmentService.repository.DepartmentRepository;
import net.java.departmentService.service.DepartmentService;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private DepartmentRepository departmentRepository;

    private ModelMapper modelMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = modelMapper.map(departmentDto, Department.class);

        Department savedDepartment = departmentRepository.save(department);
        
        DepartmentDto savedDepartmentDto = modelMapper.map(savedDepartment, DepartmentDto.class);

        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        
        if(department == null){
            System.out.println("null");
            throw new ResourceNotFoundException("Department", "DepartmentCode", departmentCode);
        }

        DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
        
        return departmentDto;
    }
}
