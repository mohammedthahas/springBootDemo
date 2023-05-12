package com.example.demo.services;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface StudentService {

    ServiceResponse<List<Student>> getAllStudents();

    ServiceResponse<Student> getStudent(Integer id);

    ServiceResponse<Void> addStudent(Student student);

    ServiceResponse<Void> addManyStudents(List<Student> student);

    ServiceResponse<Void> updateStudent(Integer id, Student student);

    ServiceResponse<Void> updateManyStudent(List<Student> student);

    ServiceResponse<Void> deleteStudent(Integer id);

    ServiceResponse<Void> deleteAllStudent(List<Integer> id);

     ServiceResponse<Page<Student>> findByAllPagination(int pageNo, int size, String SortBy, Sort.Direction direction);
}


