package com.example.demo.controller;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.entity.StudentEntity;
import com.example.demo.services.StudentService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {


    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("api=/student,operation=addStudent,status=IN_PROGRESS");
        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudents();
        log.info("api=/student,operation=getStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).body(serviceResponse.getData());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer id) {
        log.info("api=/student/id,operation=getSingleStudent,status=IN_PROGRESS");
        ServiceResponse<Student> serviceResponse = studentService.getStudent(id);
        log.info("api=/student,operation=getSingleStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).body(serviceResponse.getData());
    }

    @PostMapping
    public ResponseEntity<Void> addStudent(@RequestBody Student student) {
        log.info("api=/student,operation=addStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);
        log.info("api=/student,operation=addStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();
    }

    @PostMapping(value = "/addMany")
    public ResponseEntity<Void> addManyStudent(@RequestBody List<Student> student) {
        log.info("api=/student,operation=addManyStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.addManyStudents(student);
        log.info("api=/student,operation=addManyStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        log.info("api=/student/id,operation=updateStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.updateStudent(id, student);
        log.info("api=/student,operation=updateStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();

    }

    @PutMapping(value = "/updateMany")
    public ResponseEntity<Void> updateManyStudent(@RequestBody List<Student> student) {
        log.info("api=/student/id,operation=updateStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.updateManyStudent(student);
        log.info("api=/student,operation=updateStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
        log.info("api=/student/id,operation=deleteStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(id);
        log.info("api=/student,operation=deleteStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();


    }

    @DeleteMapping(value = "/DeleteAll")
    public ResponseEntity<Void> deleteAllStudent(@RequestBody List<Integer> id) {
        log.info("api=/student/id,operation=deleteStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = studentService.deleteAllStudent(id);
        log.info("api=/student,operation=deleteStudent,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).build();


    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<Student>> getAllByPage(@RequestParam(value = "PageNo", defaultValue = "0") int pageNo,
                                                      @RequestParam(value = "Size", defaultValue = "5") int size,
                                                      @RequestParam(value = "Sort", defaultValue = "id") String sortBy,
                                                      @RequestParam(value = "Direction", defaultValue = "ASC") Sort.Direction direction) {
        log.info("api=/student/page,operation=pagination&Sorting,status=IN_PROGRESS");
        ServiceResponse<Page<Student>> serviceResponse = studentService.findByAllPagination(pageNo, size, sortBy, direction);
        log.info("api=/student/page,operation=pagination&Sorting,status=SUCCESS");
        return ResponseEntity.status(serviceResponse.getStatus()).body(serviceResponse.getData());
    }

}
