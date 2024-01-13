package com.intensive.hw7.service;

import com.intensive.hw7.model.Student;


public interface StudentService {

    Student getStudentWithCoursesByID(String studentId);

    void registerStudentForCourse(String courseId, String studentId);

    void removeStudentFromCourse(String studentId, String courseId);

    void addStudent(Student student);

    void deleteStudent(String studentId);
}
