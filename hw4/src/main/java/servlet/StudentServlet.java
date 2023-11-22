package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Student;
import service.StudentService;
import util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {

    private StudentService studentService;
    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void init() {
        this.studentService = new StudentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String json = RequestReader.readInputStream(req.getInputStream());
        Student student = mapper.readValue(json, Student.class);
        if (studentService.addStudent(student)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("studentId");
        if (studentService.deleteStudent(studentId)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String studentId = req.getParameter("id");
        Student student = null;
        try {
            student = studentService.getStudentWithCoursesByID(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json = mapper.writeValueAsString(student);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();
        try {
            switch (action) {

                case "/student/cancelStudentCourse":
                    cancelStudentCourse(req, resp);
                    break;

                case "/student/setStudentForCourse":
                    setStudentForCourse(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void setStudentForCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {

        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        if (studentService.registerStudentForCourse(courseId, studentId)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void cancelStudentCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException {
        String studentId = request.getParameter("studentId");
        String courseId = request.getParameter("courseId");
        if (studentService.removeStudentFromCourse(studentId, courseId)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


}
