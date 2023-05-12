package com.example.demo.services.implement;

import com.example.demo.dto.Course;
import com.example.demo.dto.ServiceResponse;
import com.example.demo.dto.Student;
import com.example.demo.entity.CourseEntity;
import com.example.demo.entity.StudentEntity;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public ServiceResponse<List<Student>> getAllStudents() {

        log.info("operation=getStudent,status=IN_PROGRESS");

        ServiceResponse<List<Student>> serviceResponse = new ServiceResponse<>();
        try {

            List<StudentEntity> studentEntities = (List<StudentEntity>) studentRepository.findAll();
            List<Student> students = new ArrayList<>();

            for (StudentEntity studentEntity : studentEntities) {
                Student student = new Student();

                BeanUtils.copyProperties(studentEntity, student);
                Set<Course> courseSet = new HashSet<>();
                for (CourseEntity courseEntity : studentEntity.getCourseEntities()) {
                    Course course = new Course();
                    BeanUtils.copyProperties(courseEntity, course);
                    courseSet.add(course);
                }

                students.add(student);
                student.setCourseEntities(courseSet);
            }


            serviceResponse.setData(students);
            serviceResponse.setStatus(HttpStatus.OK);
            log.info("operation=getStudent,status=SUCCESS");

        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("api=/student,operation=getStudent,status=InternalServerError", e);

        }
        return serviceResponse;
    }


    @Override
    public ServiceResponse<Student> getStudent(Integer id) {
        log.info("operation=getSingleStudent,status=IN_PROGRESS");
        ServiceResponse<Student> serviceResponse = new ServiceResponse<>();
        try {
            StudentEntity studentEntities = studentRepository.findById(id).get();


            Student student = new Student();
            BeanUtils.copyProperties(studentEntities, student);

            Set<Course> courseSet = new HashSet<>();
            for (CourseEntity courseEntity : studentEntities.getCourseEntities()) {
                Course course = new Course();
                BeanUtils.copyProperties(courseEntity, course);
                courseSet.add(course);

            }
            student.setCourseEntities(courseSet);


//        student.setId(studentEntities.getId());
//        student.setStudentName(studentEntities.getStudentName());
//        student.setAddress(studentEntities.getAddress());
//        student.setPhoneNumber(studentEntities.getPhoneNumber());

            serviceResponse.setData(student);
            serviceResponse.setStatus(HttpStatus.OK);
            log.info("operation=getSingleStudent,status=SUCCESS");
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=getSingleStudent,status=InternalServerError", e);
        }

        return serviceResponse;
    }


    @Override
    public ServiceResponse<Void> addStudent(Student student) {
        log.info("operation=addStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            StudentEntity studentEntity = new StudentEntity();


            BeanUtils.copyProperties(student, studentEntity);

            Set<CourseEntity> courseEntity = new HashSet<>();

            for (Course course : student.getCourseEntities()) {
                CourseEntity courseEntity1 = new CourseEntity();
                BeanUtils.copyProperties(course, courseEntity1);

                courseEntity.add(courseEntity1);
            }
            studentEntity.setCourseEntities(courseEntity);


            studentRepository.save(studentEntity);



            serviceResponse.setStatus(HttpStatus.CREATED);
            log.info("operation=addStudent,status=SUCCESS");
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=addStudent,status=InternalServerError", e);
        }
        return serviceResponse;
    }

    @Override

    public ServiceResponse<Void> addManyStudents(List<Student> student) {
        log.info("operation=addManyStudent,status=IN_PROGRESS");

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            List<StudentEntity> studentEntity = new ArrayList<>();
            for (Student student1 : student) {
                StudentEntity studentEntity1 = new StudentEntity();
                BeanUtils.copyProperties(student1, studentEntity1);
                studentEntity.add(studentEntity1);


                Set<CourseEntity> courseEntitySet = new HashSet<>();
                for (Course course : student1.getCourseEntities()) {
                    CourseEntity courseEntity = new CourseEntity();
                    BeanUtils.copyProperties(course, courseEntity);
                    courseEntitySet.add(courseEntity);

                }
                studentEntity1.setCourseEntities(courseEntitySet);

            }
            serviceResponse.setStatus(HttpStatus.CREATED);
            studentRepository.saveAll(studentEntity);
            log.info("operation=addManyStudent,status=SUCCESS");
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=addManyStudent,status=InternalServerError", e);
        }
        return serviceResponse;

    }


    @Override
    public ServiceResponse<Void> updateStudent(Integer id, Student student) {
        log.info("operation=updateStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
        try {


            BeanUtils.copyProperties(student, studentEntity.get());
            studentEntity.get().setId(id);

            studentRepository.save(studentEntity.get());


            serviceResponse.setStatus(HttpStatus.OK);
            log.info("operation=updateStudent,status=SUCCESS");
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=updateStudent,status=InternalServerError", e);
        }
        return serviceResponse;
    }


    @Override
    public ServiceResponse<Void> updateManyStudent(List<Student> student) {
        log.info("operation=updateManyStudent,status=IN_PROGRESS");

        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();

        try {
            List<StudentEntity> studentEntities = new ArrayList<>();
            for (Student student1 : student) {
                StudentEntity studentEntity1 = new StudentEntity();
                BeanUtils.copyProperties(student1, studentEntity1);
                studentEntities.add(studentEntity1);

            }
            studentRepository.saveAll(studentEntities);
            serviceResponse.setStatus(HttpStatus.OK);
            log.info("operation=updateManyStudent,status=SUCCESS");
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=updateManyStudent,status=InternalServerError", e);
        }

        return serviceResponse;

    }


    @Override
    public ServiceResponse<Void> deleteStudent(Integer id) {
        log.info("operation=deleteStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            studentRepository.deleteById(id);
            log.info("operation=deleteStudent,status=SUCCESS");
            serviceResponse.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("operation=deleteStudent,status=InternalServerError", e);
        }
        return serviceResponse;
    }

    @Override
    public ServiceResponse<Void> deleteAllStudent(List<Integer> id) {
        log.info("operation=deleteStudent,status=IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            studentRepository.deleteAllById(id);
            log.info("operation=deleteStudent,status=SUCCESS");
            serviceResponse.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.info("" +
                    "operation=deleteAllStudent,status=InternalServerError", e);

        }
        return serviceResponse;


    }

    @Override
    public ServiceResponse<Page<Student>> findByAllPagination(int pageNo, int size, String sortBy, Sort.Direction
            direction) {
        log.info("operation=findAllByPagination,status=IN_PROGRESS");
        ServiceResponse<Page<Student>> serviceResponse = new ServiceResponse<>();
        Pageable pageable = PageRequest.of(pageNo, size, direction, sortBy);
        Page<StudentEntity> studentEntities = studentRepository.findAll(pageable);
        List<Student> student = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            Student student1 = new Student();
            BeanUtils.copyProperties(studentEntity, student1);
            student.add(student1);
        }
        Page page = new PageImpl(student, pageable, studentEntities.getTotalElements());
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setData(page);
        log.info("operation=findAllByPagination,status=Success");
        return serviceResponse;


    }

}
