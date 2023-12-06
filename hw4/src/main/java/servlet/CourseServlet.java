package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;
import service.CourseService;
import util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private CourseService courseService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() {
        this.courseService = new CourseService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String json = RequestReader.readInputStream(req.getInputStream());
        Course course = mapper.readValue(json, Course.class);

        if (courseService.addCourse(course)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String courseId = req.getParameter("id");
        try {
            if (courseService.deleteCourseById(courseId)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
