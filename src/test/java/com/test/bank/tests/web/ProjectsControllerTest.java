package com.test.bank.tests.web;

import com.test.bank.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCanCreateProject() throws Exception {
        Project project = new Project();
        project.setName("Demo");

        this.mockMvc.perform(post("/projects/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(project)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Demo")));
    }
}
