package com.example.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String name;
    private Double budget;
    private List<Long> employeeIds;
    private List<Long> projectIds;


    public DepartmentDTO(Long id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }
}
