package com.example.task_manager.dto;

import com.example.task_manager.model.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

@Schema(description = "Request body for creating a new task")
public record CreateTaskRequest(
        @Schema(description = "Title of the task", example = "Buy groceries", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 200)
        String title,

        @Schema(description = "Optional detailed description of the task", example = "Milk, eggs, bread")
        @Size(max = 2000)
        String description,

        @Schema(description = "Priority level (LOW, MEDIUM, HIGH). Defaults to MEDIUM.", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "Optional due date in ISO-8601 format", example = "2026-06-01T10:00:00Z")
        OffsetDateTime dueDate
) {}
