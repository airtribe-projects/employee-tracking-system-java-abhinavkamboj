package com.example.ems.service.impl;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.entity.Department;
import com.example.ems.entity.Employee;
import com.example.ems.entity.Project;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.repository.ProjectRepository;
import com.example.ems.service.EmployeeService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    @CachePut(value = "employeeCache", key = "#id")
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO updatedEmployeeDTO) {
        // Fetch the existing employee
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Update basic fields
        existingEmployee.setName(updatedEmployeeDTO.getName());
        existingEmployee.setEmail(updatedEmployeeDTO.getEmail());

        // Update department if provided
        if (updatedEmployeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(updatedEmployeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingEmployee.setDepartment(department);
        }

        // Update projects if provided
        if (updatedEmployeeDTO.getProjectIds() != null) {
            Set<Project> projects = updatedEmployeeDTO.getProjectIds().stream()
                    .map(projectId -> projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found")))
                    .collect(Collectors.toSet()); // Convert to Set instead of List
            existingEmployee.setProjects(projects);
        }

        // Save the updated employee
        Employee savedEmployee = employeeRepository.save(existingEmployee);

        // Convert to DTO and return
        return convertToDTO(savedEmployee);
    }


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }

        if (employeeDTO.getProjectIds() != null) {
            Set<Project> projects = employeeDTO.getProjectIds().stream()
                    .map(projectId -> projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found")))
                    .collect(Collectors.toSet()); // Convert to Set instead of List
            employee.setProjects(projects);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return convertToDTO(savedEmployee);
    }


    @Override
    @Cacheable(value = "employeeCache", key = "#id")
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesByProjectId(Long projectId) {
        return employeeRepository.findByProjectId(projectId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithoutProjects() {
        return employeeRepository.findEmployeesWithoutProjects().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "employeeCache", key = "#id")
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                Optional.ofNullable(employee.getDepartment()).map(Department::getId).orElse(null),
                employee.getProjects() != null ? employee.getProjects().stream().map(Project::getId).collect(Collectors.toList()) : new ArrayList<>()
        );
    }


    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        if (employeeDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            employee.setDepartment(department);
        }

        if (employeeDTO.getProjectIds() != null) {
            List<Project> projects = employeeDTO.getProjectIds().stream()
                    .map(projectId -> projectRepository.findById(projectId)
                            .orElseThrow(() -> new RuntimeException("Project not found")))
                    .collect(Collectors.toList());
            employee.setProjects((Set<Project>) projects);
        }

        return employee;
    }
}
