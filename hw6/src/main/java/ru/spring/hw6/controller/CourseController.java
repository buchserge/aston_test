package ru.spring.hw6.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spring.hw6.model.Course;
import ru.spring.hw6.service.CourseService;
import ru.spring.hw6.util.CourseViews;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @DeleteMapping("/deleteCourse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@RequestParam ("id")String id) {
        courseService.deleteCourseById(id);
    }

    @JsonView(CourseViews.Custom.class)
    @GetMapping("/course/{id}")
    public Course gerCourse(@PathVariable ("id")String id) {
        return courseService.getById(id);
    }
}