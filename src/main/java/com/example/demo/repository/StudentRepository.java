package com.example.demo.repository;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>, PagingAndSortingRepository<StudentEntity,Integer> {

    Page<StudentEntity> findAll(Pageable pageable);

}
