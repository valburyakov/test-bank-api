package com.test.bank.tests.web;

import com.test.bank.dto.ProjectDTO;
import com.test.bank.model.Project;
import com.test.bank.service.ProjectsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @MockBean
    private ProjectsService service;

    @Test
    public void testCanCreateProject() throws Exception {
        ProjectDTO project = new ProjectDTO();
        project.setName("Demo");

        when(service.addProject(project)).thenReturn(0L);

        this.mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(project)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0}"));
    }

    @Test
    public void testCanGetProjectById() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setName("Demo");

        when(service.findProjectById(1L)).thenReturn(Optional.of(project));

        this.mockMvc.perform(get("/projects/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Demo\"}"));
    }

    @Test
    public void testCanGetProjectByName() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setName("Demo");

        when(service.getProjectByName("Demo")).thenReturn(Optional.of(project));

        this.mockMvc.perform(get("/project?name=Demo", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Demo\"}"));
    }

    @Test
    public void testCanGetAllProjects() throws Exception {
        Project project = new Project();
        project.setId(1L);
        project.setName("Demo");

        when(service.getAllProjects()).thenReturn(Collections.singletonList(project));

        this.mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Demo\"}]"));
    }
}
