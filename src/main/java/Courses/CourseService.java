package Courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository CourseRepository;

//           private List<Course> topics =new ArrayList<>(Arrays.asList(
//            new Course("spring","spring framework","Spring FrameWork Description"),
//                new Course("java","core-java","Java Description"),
//                new Course("javaScript","JavaScript","JavaScriptDescription")));
    public List<Course> getAllCourses(String topicId){
       // return topics;
        List<Course> course = new ArrayList<>();
        CourseRepository.findByTopicId(topicId)
                .forEach(course::add);
        return course;

    }
    public Course getCourse(String id){

      // return topics.stream().filter(t->t.getId().equals(id)).findFirst().get();
       return CourseRepository.findById(id).get();

    }

    public void addCourse(Course course){
        //topics.add(topic);
        CourseRepository.save(course);


    }
    public void updateCourse( Course course){
//        for(int i =0; i < topics.size();i++){
//            Topic t = topics.get(i);
//            if(t.getId().equals(id)){
//                topics.set(i,topic);
//                        return;
//            }
//        }
        CourseRepository.save(course);
        }
    public void deleteCourse(String id) {
      //  topics.removeIf(t -> t.getId().equals(id));
        CourseRepository.deleteById(id);


    }
    }





