package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "course", schema = "public")
@Getter
@Setter

public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;


    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "joining_date")
    private String joiningDate;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity studentEntity;

}
