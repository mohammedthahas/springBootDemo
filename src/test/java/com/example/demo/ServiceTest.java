package com.example.demo;

import com.example.demo.dto.Course;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.StudentService;
import com.example.demo.services.implement.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.ASC;

public class ServiceTest {

    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentRepository);

    }

    @Test

    public void getAllStudentsOk() {
        StudentEntity student1 = new StudentEntity();
        student1.setId(123);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        StudentEntity student2 = new StudentEntity();
        student2.setId(124);
        student2.setStudentName("Anil");
        student2.setAddress("Bangalore");
        student2.setPhoneNumber(8879921929L);


        CourseEntity course = new CourseEntity();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        CourseEntity course1 = new CourseEntity();
        course1.setCourseName("JavaScript");
        course1.setCourseDescription("JavaScriptDescription");
        course1.setJoiningDate("13/04/1999");
        Set<CourseEntity> courseSet = new HashSet<>();
        courseSet.add(course);
        courseSet.add(course1);

        student1.setCourseEntities(courseSet);

        List<StudentEntity> allStudents = new ArrayList<>();
        allStudents.add(student1);
        allStudents.add(student2);

        Mockito.when(studentRepository.findAll()).thenReturn(allStudents);
        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudents();

        assertEquals(HttpStatus.OK, serviceResponse.getStatus());
        assertEquals(("Mohammed Thaha"), serviceResponse.getData().get(0).getStudentName());
        assertEquals(123, serviceResponse.getData().get(0).getId());

        List<Course> courseList = new ArrayList<>(serviceResponse.getData().get(0).getCourseEntities());
        assertEquals("Java",courseList.get(0).getCourseName());
    }

    @Test
    public void getAllStudentsInternalServerError() {

        Mockito.when(studentRepository.findAll()).thenThrow(new RuntimeException(" "));
        ServiceResponse<List<Student>> serviceResponse = studentService.getAllStudents();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }


    @Test
    public void getStudentOK() {

        StudentEntity student1 = new StudentEntity();
        student1.setId(1);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);


        CourseEntity course = new CourseEntity();
        course.setCourseName("Java");
        course.setCourseDescription("JavaDescription");
        course.setJoiningDate("13/04/2023");

        CourseEntity course1 = new CourseEntity();
        course.setCourseName("JavaScript");
        course.setCourseDescription("JavaScriptDescription");
        course.setJoiningDate("13/04/2023");

        Set<CourseEntity> courseEntities = new HashSet<>();
        courseEntities.add(course);
        courseEntities.add(course1);

        student1.setCourseEntities(courseEntities);



        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student1));

        ServiceResponse<Student> serviceResponse = studentService.getStudent(1);
        assertEquals(8073541166L, serviceResponse.getData().getPhoneNumber());
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());

        List<Course> courseList = new ArrayList<>(serviceResponse.getData().getCourseEntities());
        assertEquals("13/04/2023",courseList.get(1).getJoiningDate());

    }

    @Test
    public void getStudentsInternalServerError() {

        Mockito.when(studentRepository.findById(1)).thenReturn(Mockito.any());
        ServiceResponse<Student> serviceResponse = studentService.getStudent(Mockito.anyInt());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }

    @Test
    public void addStudentOk() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(123);
        studentEntity.setStudentName("Mohammed Thaha");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);

        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        CourseEntity courseEntity1 = new CourseEntity();
        courseEntity1.setCourseName("NodeJs");
        courseEntity1.setCourseDescription("NodeJs Description");
        courseEntity1.setJoiningDate("13/04/2023");

        CourseEntity courseEntity2 = new CourseEntity();
        courseEntity1.setCourseName("Node");
        courseEntity1.setCourseDescription("Node Description");
        courseEntity1.setJoiningDate("13/04/2023");

        Set<CourseEntity> courseEntitiesSet = new HashSet<>();
        courseEntitiesSet.add(courseEntity1);
        courseEntitiesSet.add(courseEntity2);

        studentEntity.setCourseEntities(courseEntitiesSet);



        Mockito.when(studentRepository.save(studentEntity)).thenReturn(null);

        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);
        assertEquals(HttpStatus.CREATED, serviceResponse.getStatus());



    }

    @Test
    public void addStudentInternalServerError() {

        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Mockito.doThrow(new RuntimeException(" ")).when(studentRepository).save(Mockito.any());

        ServiceResponse<Void> serviceResponse = studentService.addStudent(student);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }

    @Test
    public void addManyStudentsOk() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(123);
        studentEntity.setStudentName("Mohammed Thaha");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);

        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity.setId(1234);
        studentEntity.setStudentName("Nivesh");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);


        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Student student1 = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        CourseEntity courseEntity1 = new CourseEntity();
        courseEntity1.setCourseName("NodeJs");
        courseEntity1.setCourseDescription("NodeJs Description");
        courseEntity1.setJoiningDate("13/04/2023");

        CourseEntity courseEntity2 = new CourseEntity();
        courseEntity1.setCourseName("Node");
        courseEntity1.setCourseDescription("Node Description");
        courseEntity1.setJoiningDate("13/04/2023");

        Set<CourseEntity> courseEntitiesSet = new HashSet<>();
        courseEntitiesSet.add(courseEntity1);
        courseEntitiesSet.add(courseEntity2);

        studentEntity.setCourseEntities(courseEntitiesSet);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);

        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity1);
        studentEntities.add(studentEntity);

        Mockito.when(studentRepository.saveAll(studentEntities)).thenReturn(studentEntities);
        ServiceResponse<Void> serviceResponse = studentService.addManyStudents(students);
        assertEquals(HttpStatus.CREATED, serviceResponse.getStatus());


    }

    @Test
    public void addManyInternalServerError() {
        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Student student1 = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);

        Mockito.when(studentRepository.saveAll(Mockito.any())).thenThrow(new RuntimeException(" "));
        ServiceResponse<Void> serviceResponse = studentService.addManyStudents(students);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());

    }

    @Test
    public void updateStudentOk() {

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1234);
        studentEntity.setStudentName("Nivesh");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);

        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(studentEntity));


        ServiceResponse<Void> serviceResponse = studentService.updateStudent(1, student);
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());
    }

    @Test
    public void updateStudentInternalServerError() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1234);
        studentEntity.setStudentName("Nivesh");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);

        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Mockito.when(studentRepository.findById(1)).thenThrow(new RuntimeException(""));
        ServiceResponse<Void> serviceResponse = studentService.updateStudent(1234, student);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());

    }

    @Test
    public void updateManyStudents() {
        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        Student student1 = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student1);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(123);
        studentEntity.setStudentName("Mohammed Thaha");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);

        StudentEntity studentEntity1 = new StudentEntity();
        studentEntity.setId(1234);
        studentEntity.setStudentName("Nivesh");
        studentEntity.setAddress("Bangalore");
        studentEntity.setPhoneNumber(8073541166L);
        List<StudentEntity> studentEntities = new ArrayList<>();
        studentEntities.add(studentEntity1);
        studentEntities.add(studentEntity);

        Mockito.when(studentRepository.saveAll(studentEntities)).thenReturn(null);
        ServiceResponse<Void> serviceResponse = studentService.updateManyStudent(students);
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());


    }

    @Test

    public void updateManyStudentsInternalServerError() {
        Mockito.when(studentRepository.findById(1)).thenThrow(new RuntimeException(""));
        ServiceResponse<Void> serviceResponse = studentService.updateStudent(1234, Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }

    @Test
    public void deleteStudent() {
        Student student = new Student();
        student.setId(1234);
        student.setStudentName("Mohammed Thaha");
        student.setAddress("Bangalore");
        student.setPhoneNumber(8073541166L);


        Mockito.doNothing().when(studentRepository).deleteById(1);
        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(1);
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());


    }

    @Test

    public void deleteStudentsInternalServerError() {
        Mockito.doThrow(new RuntimeException("")).when(studentRepository).deleteById(Mockito.any());
        ServiceResponse<Void> serviceResponse = studentService.deleteStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }

    @Test
    public void deleteAllStudent() {


        List<Integer> id = new ArrayList<>();
        id.add(123);
        id.add(1234);


        Mockito.doNothing().when(studentRepository).deleteAllById(id);
        ServiceResponse<Void> serviceResponse = studentService.deleteAllStudent(id);
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());


    }

    @Test
    public void deleteAllStudentsInternalServerError() {


        Mockito.doThrow(new RuntimeException("")).when(studentRepository).deleteAllById(Mockito.any());
        ServiceResponse<Void> serviceResponse = studentService.deleteAllStudent(Mockito.any());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, serviceResponse.getStatus());
    }

    @Test
    public void findByAllPagination() {
        StudentEntity student1 = new StudentEntity();
        student1.setId(123);
        student1.setStudentName("Mohammed Thaha");
        student1.setAddress("Bangalore");
        student1.setPhoneNumber(8073541166L);

        StudentEntity student2 = new StudentEntity();
        student2.setId(124);
        student2.setStudentName("Anil");
        student2.setAddress("Bangalore");
        student2.setPhoneNumber(8879921929L);


        List<StudentEntity> allStudents = new ArrayList<>();
        allStudents.add(student1);
        allStudents.add(student2);
        ServiceResponse<Page<StudentEntity>> serviceResponse = new ServiceResponse<>();
        Pageable pageable = PageRequest.of(0, 2, ASC, "id");
        Page page = new PageImpl(allStudents);
        serviceResponse.setData(page);
        serviceResponse.setStatus(HttpStatus.OK);

        Mockito.when(studentRepository.findAll(pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, serviceResponse.getStatus());
        assertEquals("Mohammed Thaha", serviceResponse.getData().getContent().get(0).getStudentName());


    }

}
