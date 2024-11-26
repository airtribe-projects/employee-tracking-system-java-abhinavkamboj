package com.example.ems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private Double cost;
    private Long departmentId;
    private List<Long> employeeIds;

    public ProjectDTO(Long id, String name, double cost, Long aLong) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.departmentId = aLong;
    }
}
