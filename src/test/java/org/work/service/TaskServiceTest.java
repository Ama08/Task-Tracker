package org.work.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import org.work.model.Task;

import org.work.repository.TaskRepo;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskService taskService;

    @Test
    void test_shouldUpdateStatus_whenTestExists(){
        //Given
        Task task = new Task();
        task.setId(1L);
        task.setStatus("Not started");

        when(taskRepo.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepo.save(any(Task.class))).thenReturn(task);

        Task updated = taskService.updateTaskStatus(1L, "Done");

        assertEquals("Done", updated.getStatus());
        verify(taskRepo).save(task);
    }

    @Test
    void test_shouldThrowNotFound_whenTaskDoesNotExist(){
        when(taskRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->{
            taskService.updateTaskStatus(99L, "Done");
        });
    }
}
