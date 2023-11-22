package service;

import dao.CourseDaoImpl;
import dao.ProfessorDaoImpl;
import model.Course;
import model.Professor;
import java.sql.SQLException;
import java.util.Optional;

public class ProfessorService {
    private final ProfessorDaoImpl professorDao;
    private final CourseDaoImpl courseDao;

    public ProfessorService() {
        this.courseDao = new CourseDaoImpl(Course.class);
        this.professorDao = new ProfessorDaoImpl(Professor.class);
    }


    public Professor getProfessorWithCourses(String professorId) throws SQLException {
        return professorDao.getById(professorId).orElseThrow();
    }

    public boolean addProfessor(Professor professor) {
      return professorDao.save(professor);
    }

    public boolean registerProfessorForCourse(String courseId, String studentId) {
        Optional<Course> course = courseDao.getById(courseId);
        Optional<Professor> professor = professorDao.getById(studentId);
        course.ifPresent(c -> c.setProfessor(professor.orElseThrow()));
        professor.ifPresent(p -> System.out.println(p.getProfessorCourses()));
        professor.ifPresent(p -> p.addCourse(course.orElseThrow()));
        return professorDao.update(professor.orElseThrow());

    }
}
