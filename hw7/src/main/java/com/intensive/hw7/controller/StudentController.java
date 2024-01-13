package com.intensive.hw7.controller.integration;

import com.fasterxml.jackson.annotation.JsonView;
import com.intensive.hw7.model.Student;
import com.intensive.hw7.service.StudentService;
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

@Tag(name = "Контроллер для получения информации о студенте")
@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Зачисление студента ", description = "Данный endpoint предназначен для сохранения данных о студенте")
    @ApiResponse(responseCode = "HttpStatus.CREATED", description = "Сохранение ифоромации о студенте прошло успешно", content = {@Content(schema = @Schema(implementation = Student.class), mediaType = "application/json")})
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public void acceptStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @Operation(summary = "Получения студента по id", description = "Данный endpoint предназначен для получения данных о студенте ")
    @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно", content = {@Content(schema = @Schema(implementation = Student.class), mediaType = "application/json")})
    @JsonView(CourseViews.StudentAndProfessorView.class)
    @GetMapping("/student/{id}")
    public Student fetchStudent(@Parameter(description = "Id студента") @PathVariable("id") String studentId) {
        return studentService.getStudentWithCoursesByID(studentId);
    }

    @Operation(summary = "Запись студента на курс", description = "Данный endpoint предназначен для обновления данных о курсах студента ")
    @ApiResponse(responseCode = "200", description = "Обновление выполнено успешно")
    @PutMapping("/setStudentCourse")
    public void setStudentForCourse(@Parameter(description = "Id студента") @RequestParam("studentId") String studentId, @Parameter(description = "Id курса") @RequestParam("courseId") String courseId) {
        studentService.registerStudentForCourse(courseId, studentId);
    }

    @Operation(summary = "Отмена запсиси студента на курс", description = "Данный endpoint предназначен для обновления данных о курсах студента ")
    @ApiResponse(responseCode = "200", description = "Обновление выполнено успешно")
    @PutMapping("/cancelStudentCourse")
    public void cancelStudentCourse(@Parameter(description = "Id студента") @RequestParam("studentId") String studentId, @Parameter(description = "Id курса") @RequestParam("courseId") String courseId) {
        studentService.removeStudentFromCourse(studentId, courseId);
    }

    @Operation(summary = "Отчисление студента по id", description = "Данный endpoint предназначен для удаления данных о студенте ")
    @ApiResponse(responseCode = "HttpStatus.NO_CONTENT", description = "Удаление выполнено успешно")
    @DeleteMapping("/deleteStudent/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dismissStudent(@Parameter(description = "Id курса") @PathVariable("id") String id) {
        studentService.deleteStudent(id);
    }


}