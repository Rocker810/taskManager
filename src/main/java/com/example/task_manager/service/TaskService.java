package com.example.task_manager.service;

import com.example.task_manager.dto.CreateTaskRequest;
import com.example.task_manager.dto.PagedResponse;
import com.example.task_manager.dto.TaskResponse;
import com.example.task_manager.dto.UpdateTaskRequest;
import com.example.task_manager.model.TaskPriority;
import com.example.task_manager.model.TaskStatus;

import java.util.UUID;


public interface TaskService {
    TaskResponse createTask(CreateTaskRequest task);
    TaskResponse getTaskById(UUID id);
    TaskResponse updateTask(UUID id, UpdateTaskRequest task);
    void deleteTask(UUID id);
    PagedResponse<TaskResponse> getAllTasks(
            TaskStatus status,
            TaskPriority priority,
            String sortBy,
            String sortDir,
            int page,
            int size
    );
}
