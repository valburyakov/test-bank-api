package com.test.bank.tests.web;

import com.test.bank.model.Project;
import com.test.bank.model.Suite;
import com.test.bank.service.ProjectsService;
import com.test.bank.service.SuitesService;
import org.junit.Before;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SuitesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectsService projectsService;

    @MockBean
    private SuitesService suitesService;

    private Project project;

    @Before
    public void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Demo");
        when(projectsService.findProjectById(1L)).thenReturn(Optional.of(project));
    }

    @Test
    public void testCanCreateSuite() throws Exception {
        Suite suite = new Suite();
        suite.setProjectId(1L);
        suite.setName("Test suite");
        when(suitesService.add(1L, suite)).thenReturn(0L);

        this.mockMvc.perform(post("/projects/{projectId}/suites", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(suite)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0}"));
    }

    @Test
    public void testCanGetSuiteById() throws Exception {
        Suite suite = new Suite();
        suite.setId(1L);
        suite.setProjectId(1L);
        suite.setName("Test suite");
        when(suitesService.findSuiteById(1L)).thenReturn(Optional.of(suite));

        this.mockMvc.perform(get("/projects/suites/{suiteId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"projectId\":1,\"name\":\"Test suite\",\"deleted\":false}"));
    }

    @Test
    public void testCanGetActiveSuitesByProjectId() throws Exception {
        Suite suite = new Suite();
        suite.setId(1L);
        suite.setProjectId(1L);
        suite.setName("Test suite");
        when(suitesService.findActiveSuitesByProjectId(1L, false))
                .thenReturn(Collections.singletonList(suite));

        this.mockMvc.perform(get("/projects/{projectId}/suites", 1)
                .param("deleted", "false")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"suites\":[{\"id\":1,\"projectId\":1,\"name\":\"Test suite\",\"deleted\":false}]}"));
    }

    @Test
    public void testCanDeleteSuiteById() throws Exception {
        Suite suite = new Suite();
        suite.setId(1L);
        suite.setProjectId(1L);
        suite.setName("Test suite");
        when(suitesService.findSuiteById(1L))
                .thenReturn(Optional.of(suite));

        this.mockMvc.perform(delete("/projects/suites/{suiteId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"Deleted\"}"));
    }


}
