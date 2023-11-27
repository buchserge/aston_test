package ru.spring.hw6.service;

import ru.spring.hw6.model.Student;

public interface StudentService {

    Student getStudentWithCoursesByID(String studentId);

    void registerStudentForCourse(String courseId, String studentId);

    void removeStudentFromCourse(String studentId, String courseId);

    void addStudent(Student student);

    void deleteStudent(String studentId);
}
