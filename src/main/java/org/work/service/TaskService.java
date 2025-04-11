package org.work.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.work.model.Task;
import org.work.repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public Task getTaskById(Long id){
        return taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getAllTasks(){
        return taskRepo.findAll();
    }

    public Task updateTaskStatus(Long id, String newStatus){
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Task with ID " + id + " not found"));
        task.setStatus(newStatus);
        return taskRepo.save(task);
    }

    public void deleteTask(Long id){
        taskRepo.deleteById(id);
    }

    public Task createTask(Task task){
        if (task.getDateTime() == null) {
            task.setDateTime(LocalDateTime.now());
        }
        return taskRepo.save(task);
    }

}
