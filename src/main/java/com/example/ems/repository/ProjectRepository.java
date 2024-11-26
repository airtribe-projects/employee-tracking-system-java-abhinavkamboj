package com.example.ems.repository;

import com.example.ems.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Custom query to find all projects under a specific department
    @Query("SELECT p FROM Project p WHERE p.department.id = :departmentId")
    List<Project> findByDepartmentId(@Param("departmentId") Long departmentId);
}
