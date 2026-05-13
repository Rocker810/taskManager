package com.example.task_manager.dto;

import com.example.task_manager.model.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record CreateTaskRequest(
        @NotBlank
        @Size(max = 200)
        String title,

        @Size(max = 2000)
        String description,

        TaskPriority priority,

        OffsetDateTime dueDate
) {}
