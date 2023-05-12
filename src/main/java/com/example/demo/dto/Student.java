package com.example.demo.dto;

import com.example.demo.entity.CourseEntity;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class Student {

    private Integer id;

    private String studentName;

    private String Address;

    private Long phoneNumber;
    private Set<Course> courseEntities = new HashSet<>();


}
