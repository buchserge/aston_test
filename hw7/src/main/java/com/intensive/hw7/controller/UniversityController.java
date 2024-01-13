package com.intensive.hw7.controller.integration;

import com.intensive.hw7.model.Course;
import com.intensive.hw7.model.Student;
import com.intensive.hw7.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UniversityController {

    private final CourseService courseService;

    @GetMapping("/courses")
    public String getUniversity(Model model){
        List<Course> courses = courseService.fetchAllWithProfessor();
        model.addAttribute("courses",courses);
        return "courses";
    }
    @GetMapping("/students/{id}")
    public String getStudents(@PathVariable("id")String courseId, Model model){
        Set<Student> students = courseService.fetchAllByCourseId(courseId);
        model.addAttribute("students",students);
        return "students";
    }
}
