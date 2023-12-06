package ru.spring.hw5.service;

import ru.spring.hw5.model.Student;

public interface StudentService {

    Student getStudentWithCoursesByID(String studentId);

    void registerStudentForCourse(String courseId, String studentId);

    void removeStudentFromCourse(String studentId, String courseId);

    void addStudent(Student student);

    void deleteStudent(String studentId);
}
