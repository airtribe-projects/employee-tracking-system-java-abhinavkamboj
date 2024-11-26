package com.example.ems.service;

import com.example.ems.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> getAllEmployees();

    List<EmployeeDTO> searchEmployeesByName(String name);

    List<EmployeeDTO> getEmployeesByDepartmentId(Long departmentId);

    List<EmployeeDTO> getEmployeesByProjectId(Long projectId);

    List<EmployeeDTO> getEmployeesWithoutProjects();
}
