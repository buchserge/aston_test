package ru.spring.hw5.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spring.hw5.model.Student;
import ru.spring.hw5.service.StudentServiceImpl;
import ru.spring.hw5.util.CourseViews;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentServiceImpl studentService;

    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public void acceptStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @JsonView(CourseViews.Normal.class)
    @GetMapping("/student/{id}")
    public Student fetchStudent(@PathVariable("id") String studentId) {
        return studentService.getStudentWithCoursesByID(studentId);
    }

    @PutMapping("/setStudentCourse")
    public void setStudentForCourse(@RequestParam("studentId") String studentId, @RequestParam("courseId") String courseId) {
        studentService.registerStudentForCourse(courseId, studentId);
    }

    @PutMapping("/cancelStudentCourse")
    public void cancelStudentCourse(@RequestParam("studentId") String studentId, @RequestParam("courseId") String courseId) {
        studentService.removeStudentFromCourse(studentId, courseId);
    }

    @DeleteMapping("/deleteStudent/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dismissStudent(@PathVariable("id") String id) {
        studentService.deleteStudent(id);
    }


}
