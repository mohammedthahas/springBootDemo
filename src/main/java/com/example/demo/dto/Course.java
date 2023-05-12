package com.example.demo.dto;

import com.example.demo.entity.StudentEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class Course {

    private Integer id;

    private String courseName;
    private String courseDescription;
    private String joiningDate;

    private Student student;

}
