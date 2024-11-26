package com.example.ems.service;

import com.example.ems.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);

    void deleteProject(Long id);

    ProjectDTO getProjectById(Long id);

    List<ProjectDTO> getAllProjects();

    List<ProjectDTO> getProjectsByDepartmentId(Long departmentId);
}
