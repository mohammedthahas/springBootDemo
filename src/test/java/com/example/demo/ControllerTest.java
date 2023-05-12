package com.example.demo;

import com.example.demo.controller.StudentController;
import com.example.demo.dto.Course;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.ASC;

public class ControllerTest {

    StudentController studentController;

    @Mock
    StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentController = new StudentController(studentService);
    }

    @Test
    public void getAllStudentsTestOK() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        Student student2 = new Student();
        student2.setId(2);
        student2.setStudentName("Nivesh");
        student2.setAddress("Bangalore");
        student2.setPhoneNumber(888887889L);


        Course course = new Course();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        Course course1 = new Course();
        course1.setCourseName("JavaScript");
        course1.setCourseDescription("JavaScriptDescription");
        course1.setJoiningDate("13/04/1999");
        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        courseSet.add(course1);

        student1.setCourseEntities(courseSet);

        List<Student> addAll = new ArrayList<>();
        addAll.add(student1);
        addAll.add(student2);


        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setData(addAll);
        serviceResponse.setStatus(HttpStatus.OK);

        Mockito.when(studentService.getAllStudents()).thenReturn(serviceResponse);

        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudents();


        assertEquals(1, responseEntity.getBody().get(0).getId());
        assertEquals("Nivesh", responseEntity.getBody().get(1).getStudentName());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Course> courseList = new ArrayList<>(responseEntity.getBody().get(0).getCourseEntities());
        assertEquals("Java", courseList.get(0).getCourseName());
        assertEquals("JavaDescription", courseList.get(0).getCourseDescription());


    }

    @Test
    public void getAllStudentsInternalServerError() {
        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);


        Mockito.when(studentService.getAllStudents()).thenReturn(serviceResponse);
        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudents();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

    }

    @Test
    public void getStudentOk() {
        Student student = new Student();
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Course course = new Course();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        Course course1 = new Course();
        course1.setCourseName("JavaScript");
        course1.setCourseDescription("JavaScript Description");
        course1.setJoiningDate("13/04/2023");

        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        courseSet.add(course1);

        student.setCourseEntities(courseSet);

        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();
        serviceResponse.setData(student);
        serviceResponse.setStatus(HttpStatus.OK);

        Mockito.when(studentService.getStudent(1)).thenReturn(serviceResponse);

        ResponseEntity<Student> responseEntity = studentController.getStudent(1);
        assertEquals("Bangalore", responseEntity.getBody().getAddress());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<Course> courses = new ArrayList<>(responseEntity.getBody().getCourseEntities());
        assertEquals("Java", courses.get(0).getCourseName());
    }

    @Test
    public void getStudentInternalServerError() {
        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(studentService.getStudent(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<Student> responseEntity = studentController.getStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void addStudentOk() {
        Student student = new Student();
        student.setId(1);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Course course = new Course();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        Course course1 = new Course();
        course1.setCourseName("JavaScript");
        course1.setCourseDescription("JavaScript Description");
        course1.setJoiningDate("13/04/2023");

        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        courseSet.add(course1);

        student.setCourseEntities(courseSet);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.CREATED);

        Mockito.when(studentService.addStudent(student)).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.addStudent(student);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void addStudentInternalServerError() {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(studentService.addStudent(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.addStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void addManyStudentOk() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        Student student2 = new Student();
        student1.setId(2);
        student1.setStudentName("Nivesh");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        Course course = new Course();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        Course course1 = new Course();
        course1.setCourseName("JavaScript");
        course1.setCourseDescription("JavaScript Description");
        course1.setJoiningDate("13/04/2023");


        Set<Course> courseSet = new HashSet<>();
        courseSet.add(course);
        courseSet.add(course1);

        student1.setCourseEntities(courseSet);


        List<Student> addMany = new ArrayList<>();
        addMany.add(student1);
        addMany.add(student2);


        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.CREATED);

        Mockito.when(studentService.addManyStudents(addMany)).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.addManyStudent(addMany);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());


    }

    @Test
    public void addManyStudentInternalServerError() {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(studentService.addManyStudents(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.addManyStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updateStudent() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.OK);

        Mockito.when(studentService.updateStudent(1, student1)).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.updateStudent(student1, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


    }

    @Test
    public void updateManyStudentInternalServerError() {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(studentService.updateManyStudent(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.updateManyStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void deleteStudent() {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        Student student1 = new Student();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        serviceResponse.setStatus(HttpStatus.OK);
        Mockito.when(studentService.deleteStudent(1)).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.deleteStudent(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());


    }

    @Test
    public void DeleteManyStudentInternalServerError() {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        Mockito.when(studentService.deleteAllStudent(Mockito.any())).thenReturn(serviceResponse);
        ResponseEntity<Void> responseEntity = studentController.deleteAllStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void getAllByPageOK() {
        Student student1 = new Student();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        Student student2 = new Student();
        student2.setId(2);
        student2.setStudentName("Nivesh");
        student2.setAddress("Bangalore");
        student2.setPhoneNumber(888887889L);

        Student student3 = new Student();
        student2.setId(3);
        student2.setStudentName("Anil");
        student2.setAddress("Bangalore");
        student2.setPhoneNumber(88888778L);

        List<Student> addAll = new ArrayList<>();
        addAll.add(student1);
        addAll.add(student2);
        addAll.add(student3);

        ServiceResponse<Page<Student>> serviceResponse = new ServiceResponse<>();
        Page page = new PageImpl(addAll);


        serviceResponse.setData(page);
        serviceResponse.setStatus(HttpStatus.OK);

        Mockito.when(studentService.findByAllPagination(0, 2, "id", ASC)).thenReturn(serviceResponse);

        ResponseEntity<Page<Student>> responseEntity = studentController.getAllByPage(0, 2, "id", ASC);
        assertEquals("Mohammed Thaha", responseEntity.getBody().getContent().get(0).getStudentName());
    }


}

