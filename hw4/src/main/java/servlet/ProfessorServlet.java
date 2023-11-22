package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Professor;
import service.ProfessorService;
import util.RequestReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/professor")
public class ProfessorServlet extends HttpServlet {

    private ProfessorService professorService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        this.professorService = new ProfessorService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String json = RequestReader.readInputStream(req.getInputStream());
        Professor professor = mapper.readValue(json, Professor.class);
        if (professorService.addProfessor(professor)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("courseId");
        String studentId = req.getParameter("professorId");
        if (professorService.registerProfessorForCourse(courseId, studentId)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String professorId = req.getParameter("id");
        Professor professor = null;
        try {
            professor = professorService.getProfessorWithCourses(professorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json = mapper.writeValueAsString(professor);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setHeader("Content-Type", "application/json");
        resp.getOutputStream().println(json);


    }


}
