package com.example.ems.controller;

import com.example.ems.dto.EmployeeDTO;
import com.example.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/search")
    public List<EmployeeDTO> searchEmployeesByName(@RequestParam String name) {
        return employeeService.searchEmployeesByName(name);
    }

    @GetMapping("/without-projects")
    public List<EmployeeDTO> getEmployeesWithoutProjects() {
        return employeeService.getEmployeesWithoutProjects();
    }

    @GetMapping("/department/{departmentId}")
    public List<EmployeeDTO> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getEmployeesByDepartmentId(departmentId);
    }

    @GetMapping("/project/{projectId}")
    public List<EmployeeDTO> getEmployeesByProject(@PathVariable Long projectId) {
        return employeeService.getEmployeesByProjectId(projectId);
    }
}
