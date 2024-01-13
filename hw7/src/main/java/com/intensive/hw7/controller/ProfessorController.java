package com.intensive.hw7.controller.integration;

import com.fasterxml.jackson.annotation.JsonView;
import com.intensive.hw7.model.Professor;
import com.intensive.hw7.service.ProfessorService;
import com.intensive.hw7.util.CourseViews;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Контроллер для получения информации о профессоре")

@RestController
@RequiredArgsConstructor
public class ProfessorController {

    private  final ProfessorService professorService;

    @Operation(summary = "Принятие  профессора на работу в университет", description = "Данный endpoint предназначен для сохранения данных о профессоре")
    @ApiResponse(responseCode = "HttpStatus.CREATED", description = "Сохранение ифоромации о профессоре прошло успешно", content = {@Content(schema = @Schema(implementation = Professor.class), mediaType = "application/json")})

    @PostMapping("/professor")
    @ResponseStatus(HttpStatus.CREATED)
    public void hireProfessor(@Valid @RequestBody  Professor professor) {
        professorService.addProfessor(professor);

    }
    @Operation(summary = "Получения профессора по id", description = "Данный endpoint предназначен для получения данных о профессоре ")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = {@Content(schema = @Schema(implementation = Professor.class), mediaType = "application/json")})

    @GetMapping("/professor/{id}")
    @JsonView(CourseViews.StudentAndProfessorView.class)
    public Professor fetchProfessor(@Parameter(description = "Id профессора")@PathVariable("id") String id) {
        return professorService.getProfessorWithCourses(id);
    }
    @Operation(summary = "Запись профессора на преводавание курса", description = "Данный endpoint предназначен для обновления данных о курсах профессора ")
    @ApiResponse(responseCode = "200", description = "Обновление выполнено успешно")

    @PutMapping("/professor")
    public void updateProfessorAssignment(@Parameter(description = "Id курса")@RequestParam("courseId") String courseId,@Parameter(description = "Id профессора") @RequestParam("professorId") String professorId) {
        professorService.registerProfessorForCourse(courseId, professorId);
    }
}