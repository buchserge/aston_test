package ru.spring.hw5.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.spring.hw5.model.Professor;
import ru.spring.hw5.service.ProfessorServiceImpl;

@RestController
@RequiredArgsConstructor
public class ProfessorController {

    private ProfessorServiceImpl professorService;


    @PostMapping("/professor")
    @ResponseStatus(HttpStatus.CREATED)
    public void hireProfessor(@RequestBody Professor professor) {
        professorService.addProfessor(professor);

    }

    @GetMapping("/professor/{id}")
    public Professor fetchProfessor(@PathVariable("id") String id) {
        return professorService.getProfessorWithCourses(id);
    }

    @PutMapping("/professor")
    public void updateProfessorAssignment(@RequestParam("courseId") String courseId, @RequestParam("professorId") String professorId) {
        professorService.registerProfessorForCourse(courseId, professorId);
    }
}