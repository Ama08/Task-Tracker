package org.work.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.work.model.Task;
import org.work.service.TaskService;

import static org.mockito.Mockito.*;

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

        mockMvc.perform("/tasks/1/status")
                .param("status", "Done")
    }
}
