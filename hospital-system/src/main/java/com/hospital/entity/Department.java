package com.hospital.entity;

import lombok.Data;

@Data
public class Department {
    private Long id;
    private String name;
    private String description;//简介
}