package com.test.bank.tests.web;


import com.test.bank.enums.LaunchStatus;
import com.test.bank.model.Launch;
import com.test.bank.model.Project;
import com.test.bank.service.LaunchesService;
import com.test.bank.service.ProjectsService;
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
public class LaunchesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectsService projectsService;

    @MockBean
    private LaunchesService launchesService;

    private Project project;

    @Before
    public void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Demo");

        when(projectsService.findProjectById(1L)).thenReturn(Optional.of(project));
    }

    @Test
    public void testCanCreateLaunch() throws Exception {
        Launch launch = new Launch();
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.add(launch)).thenReturn(1L);

        this.mockMvc.perform(post("/projects/{projectId}/launches", project.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(launch)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"id\":0}"));
    }

    @Test
    public void testCanGetLaunchesByProjectId() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findAllLaunchesByProjectId(project.getId())).thenReturn(Collections.singletonList(launch));

        this.mockMvc.perform(get("/projects/{projectId}/launches", project.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"launches\":[{\"id\":1,\"projectId\":1,\"testCaseId\":null,\"result\":\"NOT_RUN\",\"description\":\"\",\"executionDate\":null}]}"));
    }

    @Test
    public void testCanGetAllLaunchesByTestCaseId() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findAllLaunchesByTestCaseId(1L)).thenReturn(Collections.singletonList(launch));

        this.mockMvc.perform(get("/projects/launches/cases/{caseId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"launches\":[{\"id\":1,\"projectId\":1,\"testCaseId\":1,\"result\":\"NOT_RUN\",\"description\":\"\",\"executionDate\":null}]}"));
    }

    @Test
    public void testCanGetLaunchesById() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));

        this.mockMvc.perform(get("/projects/launches/{launchId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"launch\":{\"id\":1,\"projectId\":1,\"testCaseId\":1,\"result\":\"NOT_RUN\",\"description\":\"\",\"executionDate\":null}}"));
    }

    @Test
    public void testCanDeleteLaunchById() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));

        this.mockMvc.perform(delete("/projects/launches/{launchId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"Deleted\"}"));
    }

    @Test
    public void testCanNotFoundLaunchesById() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));

        this.mockMvc.perform(get("/projects/launches/{launchId}", 2)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"No such launch with id 2\"}"));
    }

    @Test
    public void testCanNotCreateLaunchWithWrongResult() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult("Done");

        when(launchesService.add(launch)).thenReturn(1L);

        this.mockMvc.perform(post("/projects/{projectId}/launches", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(launch)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":\"No such result Done found for launch, use on of NOT_RUN, FAILED, PASSED status.\"}"));
    }

    @Test
    public void testCanNotUpdateLaunchWithWrongProjectId() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));
        when(launchesService.add(launch)).thenReturn(1L);

        Launch launchUpdate = new Launch();
        launchUpdate.setProjectId(0L);
        launchUpdate.setId(1L);
        launchUpdate.setTestCaseId(1L);
        launchUpdate.setResult(LaunchStatus.PASSED.name());

        this.mockMvc.perform(put("/projects/launches/{launchId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(launchUpdate)))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":\"No such project with id 0\"}"));
    }

    @Test
    public void testCanNotUpdateLaunchWithWrongId() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));
        when(launchesService.add(launch)).thenReturn(1L);

        Launch launchUpdate = new Launch();
        launchUpdate.setProjectId(project.getId());
        launchUpdate.setId(2L);
        launchUpdate.setTestCaseId(1L);
        launchUpdate.setResult(LaunchStatus.PASSED.name());

        this.mockMvc.perform(put("/projects/launches/{launchId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(launchUpdate)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":\"Wrong lunch id.\"}"));
    }

    @Test
    public void testCanNotUpdateLaunchWithWrongTestCaseId() throws Exception {
        Launch launch = new Launch();
        launch.setProjectId(project.getId());
        launch.setId(1L);
        launch.setTestCaseId(1L);
        launch.setResult(LaunchStatus.NOT_RUN.name());

        when(launchesService.findLaunchById(1L)).thenReturn(Optional.of(launch));
        when(launchesService.add(launch)).thenReturn(1L);

        Launch launchUpdate = new Launch();
        launchUpdate.setProjectId(project.getId());
        launchUpdate.setId(1L);
        launchUpdate.setTestCaseId(2L);
        launchUpdate.setResult(LaunchStatus.PASSED.name());

        this.mockMvc.perform(put("/projects/launches/{launchId}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(IntegrationTestUtils.toJson(launchUpdate)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":\"Wrong test case id.\"}"));
    }

}
