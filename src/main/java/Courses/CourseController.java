package Courses;

import com.example.demo.topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {


    @Autowired
    private CourseService CourseService;


   @RequestMapping("/course/{topicId}/{id}")
    public List<Course> getAllCourses(@PathVariable String id){
        return CourseService.getAllCourses(id);

    }
    @RequestMapping("/topics/{topicId}/courses/{id}")
    public Course getCourse(@PathVariable String id){
       return CourseService.getCourse(id);

    }
    @PostMapping(value = "/topics/courses")
    public void addCourse(@RequestBody Course course){
            course.setTopic(new Topic("ss"," ",""));
       CourseService.addCourse(course);

    }



    @RequestMapping(method = RequestMethod.PUT,value = "/topics/{topicId}/courses/{id}")
    public void updateCourse(@RequestBody Course course, @PathVariable String topicId,@PathVariable String id){
        course.setTopic(new Topic(topicId," ",""));
       CourseService.updateCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/topics/{topicId}/courses/{id}")
    public void deleteCourse(@PathVariable String id){
        CourseService.deleteCourse(id);
    }
}
