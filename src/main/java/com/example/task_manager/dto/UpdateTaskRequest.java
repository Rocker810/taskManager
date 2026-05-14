package com.example.task_manager.dto;

import com.example.task_manager.model.TaskPriority;
import com.example.task_manager.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Schema(description = "Request body for updating an existing task")
public record UpdateTaskRequest(
        @Schema(description = "Title of the task", example = "Buy groceries", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank()
        @Size(max = 200)
        String title,

        @Schema(description = "Optional detailed description of the task", example = "Milk, eggs, bread, butter")
        @Size(max = 2000)
        String description,

        @Schema(description = "Current status of the task", example = "IN_PROGRESS")
        TaskStatus status,

        @Schema(description = "Priority level", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "Optional due date in ISO-8601 format", example = "2026-06-01T10:00:00Z")
        OffsetDateTime dueDate
) {}
