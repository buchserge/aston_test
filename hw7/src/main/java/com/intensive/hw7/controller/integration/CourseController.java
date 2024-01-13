package com.intensive.hw7.controller.integration;

import com.fasterxml.jackson.annotation.JsonView;
import com.intensive.hw7.model.Course;
import com.intensive.hw7.repo.CourseRepo;
import com.intensive.hw7.service.CourseService;
import com.intensive.hw7.util.CourseViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Контроллер для получения информации о курсе")
@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseRepo courseRepo;

    @Operation(summary = "Создание курса ", description = "Данный endpoint предназначен для сохранения данных о курсе")
    @ApiResponse(responseCode = "HttpStatus.CREATED", description = "Сохранение выполнено успешно",content = {@Content(schema = @Schema(implementation = Course.class),mediaType = "application/json")})
    @PostMapping("/course")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody Course course) {

        courseService.addCourse(course);

    }
    @Operation(summary = "Удаление курса по id", description = "Данный endpoint предназначен для удаления данных о курсе и связанному с ним профессору")
    @ApiResponse(responseCode = "HttpStatus.NO_CONTENT", description = "Удаление выполнен успешно")
    @DeleteMapping("/deleteCourse")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@Parameter(description = "Id курса") @RequestParam("id") String id) {
        courseService.deleteCourseById(id);
    }

    @Operation(summary = "Получения курса по id", description = "Данный endpoint предназначен для получения данных о курсе и связанному с ним профессору")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно",content = {@Content(schema = @Schema(implementation = Course.class),mediaType = "application/json")})
    @JsonView(CourseViews.CourseView.class)
    @GetMapping("/course/{id}")
    public Course gerCourse(@Parameter(description = "Id курса") @PathVariable("id") String id) {
        return courseService.getById(id);
    }

}