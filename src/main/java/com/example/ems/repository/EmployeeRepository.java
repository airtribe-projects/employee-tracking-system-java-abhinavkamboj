package com.example.ems.repository;

import com.example.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom query to search employees by name
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Find employees by department ID
    List<Employee> findByDepartmentId(Long departmentId);

    // Find employees assigned to a specific project
    @Query("SELECT e FROM Employee e JOIN e.projects p WHERE p.id = :projectId")
    List<Employee> findByProjectId(@Param("projectId") Long projectId);

    // Find employees not assigned to any project
    @Query("SELECT e FROM Employee e WHERE e.projects IS EMPTY")
    List<Employee> findEmployeesWithoutProjects();
}
