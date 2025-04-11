package org.work.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.work.model.Task;
import org.work.service.TaskService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void test_shouldUpdateTaskStatus() throws Exception{
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test");
        task.setStatus("Done");

        when(taskService.updateTaskStatus(1L, "Done")).thenReturn(task);

        mockMvc.perform(put("/tasks/1/status")
                .param("status", "Done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Done"));
    }
    @Test
    void shouldReturn404_whenTaskNotFound() throws Exception {
        when(taskService.updateTaskStatus(99L, "Done"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mockMvc.perform(put("/tasks/99/status")
                        .param("status", "Done"))
                .andExpect(status().isNotFound());
    }
}
