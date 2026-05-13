package com.example.task_manager.dto;

import com.example.task_manager.model.TaskPriority;
import com.example.task_manager.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record UpdateTaskRequest(
        @NotBlank()
        @Size(max = 200)
        String title,

        @Size(max = 2000)
        String description,

        TaskStatus status,

        TaskPriority priority,

        OffsetDateTime dueDate
) {}
