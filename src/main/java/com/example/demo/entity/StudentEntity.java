package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student", schema = "public")
@Getter
@Setter
public class StudentEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "address")
    private String Address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToMany(orphanRemoval = true, mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<CourseEntity> courseEntities = new HashSet<>();


    public void setCourseEntities(Set<CourseEntity> courseEntities) {
        this.courseEntities.clear();
        if (!courseEntities.isEmpty()) {
            for (CourseEntity courseEntity : courseEntities) {
                courseEntity.setStudentEntity(this);
                this.courseEntities.add(courseEntity);
            }

        }
    }


}
