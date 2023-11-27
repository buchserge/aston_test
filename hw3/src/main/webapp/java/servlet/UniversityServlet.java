package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Professor;
import model.Student;
import service.CourseService;
import service.ProfessorService;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/")
public class UniversityServlet extends HttpServlet {
    private StudentService studentService;
    private ProfessorService professorService;
    private CourseService courseService;
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    public void init() {
        this.studentService = new StudentService();
        this.professorService = new ProfessorService();
        this.courseService = new CourseService();
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/student":
                    fetchStudentByID(req, resp);
                    break;
                case "/professor":
                    fetchProfessorById(req, resp);
                    break;
                case "/assignStudent":
                    setStudentForCourse(req, resp);
                    break;
                case "/deleteCourse":
                    deleteCourse(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void fetchProfessorById(HttpServletRequest request, HttpServletResponse response)
            throws IOException, SQLException {
        String professorId = request.getParameter("id");
        Professor professor = professorService.getProfessorWithCourses(professorId);
        String json = GSON.toJson(professor);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().println(json);

    }

    private void fetchStudentByID(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String studentId = request.getParameter("id");
        Student student = studentService.getStudentWithCoursesByID(studentId);
        String json = GSON.toJson(student);
        response.setStatus(200);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream().println(json);


    }

    private void setStudentForCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        String courseId = request.getParameter("courseId");
        String studentName = request.getParameter("studentName");
        if (studentService.registerStudentForCourse(courseId, studentName)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
    }


    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        String courseId = request.getParameter("courseId");

        if (courseService.deleteCourseById(courseId)) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }

    }


}
