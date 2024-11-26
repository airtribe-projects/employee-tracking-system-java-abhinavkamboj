package com.example.ems.service.impl;

import com.example.ems.dto.ProjectDTO;
import com.example.ems.entity.Department;
import com.example.ems.entity.Project;
import com.example.ems.repository.DepartmentRepository;
import com.example.ems.repository.ProjectRepository;
import com.example.ems.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setCost(projectDTO.getCost());

        if (projectDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(projectDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            project.setDepartment(department);
        }

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }

    @Override
    @CachePut(value = "projectCache", key = "#id")
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        existingProject.setName(projectDTO.getName());
        existingProject.setCost(projectDTO.getCost());

        if (projectDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(projectDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingProject.setDepartment(department);
        }

        Project savedProject = projectRepository.save(existingProject);
        return convertToDTO(savedProject);
    }

    @Override
    @CacheEvict(value = "projectCache", key = "#id")
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    @Cacheable(value = "projectCache", key = "#id")
    public ProjectDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToDTO(project);
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> getProjectsByDepartmentId(Long departmentId) {
        return projectRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProjectDTO convertToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getName(),
                project.getCost(),
                Optional.ofNullable(project.getDepartment()).map(Department::getId).orElse(null)
        );
    }
}
