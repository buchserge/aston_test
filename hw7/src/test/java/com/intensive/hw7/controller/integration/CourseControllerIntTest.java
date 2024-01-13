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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/init_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/init_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CourseControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void gerCourse() throws Exception {
        mockMvc.perform(get("/course/{id}", "someid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {"id":"someid","title":"emglish","professor":{"id":"someid"},"students":[{"id":"studentId"}]}
                          """))
                .andDo(print());


    }

    @Test
    void deleteCourse() throws Exception {
        mockMvc.perform(delete("/deleteCourse").param("id", "someid"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void createCourse() throws Exception {
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON).content("""
                {"id":"someid","title":"emglish","professor":{"id":"someid"},"students":[{"id":"studentId"}]}
                """))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}