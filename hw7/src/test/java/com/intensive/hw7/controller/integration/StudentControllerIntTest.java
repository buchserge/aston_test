package com.intensive.hw7.controller.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/init_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/init_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class StudentControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void acceptStudent() throws Exception {
        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content("""
                {"id":"studentId","name":"Alex","universityName":"princeton"}
                """));
    }

    @Test
    void fetchStudent() throws Exception {
        mockMvc.perform(get("/student/{id}", "studentId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"universityName":"princeton","name":"Alex","studentCourses":[{"universityName":"princeton"}]}
                          """))
                .andDo(print());
    }

    @Test
    void setStudentForCourse() throws Exception {

        mockMvc.perform(put("/setStudentCourse").param("studentId", "studentId")
                        .param("courseId", "someid"))
                         .andExpect(status().isOk())
                         .andDo(print());
    }

    @Test
    void cancelStudentCourse() throws Exception {

        mockMvc.perform(put("/cancelStudentCourse").param("studentId", "studentId")
                        .param("courseId", "someid"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void dismissStudent() throws Exception {

        mockMvc.perform(delete("/deleteStudent/{id}", "studentId"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}