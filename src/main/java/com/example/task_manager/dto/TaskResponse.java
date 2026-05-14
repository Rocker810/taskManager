package com.example.task_manager.dto;

import com.example.task_manager.model.Task;
import com.example.task_manager.model.TaskPriority;
import com.example.task_manager.model.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Task representation returned by the API")
public record TaskResponse(

        @Schema(description = "Unique identifier", example = "1c9605ed-4953-4bd2-8910-6b4892dbca1f")
        UUID id,

        @Schema(description = "Title of the task", example = "Buy groceries")
        String title,

        @Schema(description = "Detailed description", example = "Milk, eggs, bread")
        String description,

        @Schema(description = "Current status", example = "TODO")
        TaskStatus status,

        @Schema(description = "Priority level", example = "HIGH")
        TaskPriority priority,

        @Schema(description = "Due date in ISO-8601 format", example = "2026-06-01T10:00:00Z")
        OffsetDateTime dueDate,

        @Schema(description = "When the task was created", example = "2026-05-14T07:10:13Z")
        Instant createdAt,

        @Schema(description = "When the task was last updated", example = "2026-05-14T07:15:00Z")
        Instant updatedAt
) {
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
