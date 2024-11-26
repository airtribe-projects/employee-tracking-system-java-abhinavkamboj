package com.example.ems.service.impl;

import com.example.ems.dto.DepartmentDTO;
import com.example.ems.entity.Department;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    @Transactional
    @CachePut(value = "departmentCache", key = "#id")
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO updatedDepartmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existingDepartment.setName(updatedDepartmentDTO.getName());
        existingDepartment.setBudget(updatedDepartmentDTO.getBudget());

        Department savedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setBudget(departmentDTO.getBudget());

        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Override
    @Cacheable(value = "departmentCache", key = "#id")
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return convertToDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "departmentCache", key = "#id")
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public double getTotalBudgetAllocatedToProjects(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return department.getProjects().stream()
                .mapToDouble(project -> project.getCost())
                .sum();
    }

    private DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getBudget()
        );
    }
}
