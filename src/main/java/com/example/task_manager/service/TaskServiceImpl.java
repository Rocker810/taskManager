package com.example.task_manager.service;


import com.example.task_manager.dto.CreateTaskRequest;
import com.example.task_manager.dto.PagedResponse;
import com.example.task_manager.dto.TaskResponse;
import com.example.task_manager.dto.UpdateTaskRequest;
import com.example.task_manager.exception.TaskNotFoundException;
import com.example.task_manager.model.Task;
import com.example.task_manager.model.TaskPriority;
import com.example.task_manager.model.TaskStatus;
import com.example.task_manager.repository.TaskRepository;
import com.example.task_manager.repository.TaskSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private static final int MAX_PAGE_SIZE = 100;

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "createdAt", "updatedAt", "dueDate", "priority", "status", "title"
    );
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority() != null ? request.priority() : TaskPriority.MEDIUM)
                .dueDate(request.dueDate())
                .build();

        Task saved = taskRepository.save(task);
        return TaskResponse.from(saved);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());

        Task updated = taskRepository.save(task);
        return TaskResponse.from(updated);
    }

    @Override
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskResponse.from(task);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public PagedResponse<TaskResponse> getAllTasks(
            TaskStatus status,
            TaskPriority priority,
            String sortBy,
            String sortDir,
            int page,
            int size
    ) {
        // Validation
        if (size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException(
                    "Page size must not exceed " + MAX_PAGE_SIZE + " (received " + size + ")"
            );
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1");
        }
        if (page < 0) {
            throw new IllegalArgumentException("Page number must not be negative");
        }

        // Apply defaults
        String safeSortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        String safeSortDir = (sortDir == null || sortDir.isBlank()) ? "asc" : sortDir;

        // Validate sort field
        if (!ALLOWED_SORT_FIELDS.contains(safeSortBy)) {
            throw new IllegalArgumentException(
                    "Invalid sortBy field: " + safeSortBy +
                            ". Allowed values: " + ALLOWED_SORT_FIELDS
            );
        }

        // Build the Sort
        Sort.Direction direction = "desc".equalsIgnoreCase(safeSortDir)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, safeSortBy);

        // Build the Pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Task> spec = (root, query, cb) -> cb.conjunction();
        if (status != null) {
            spec = spec.and(TaskSpecifications.hasStatus(status));
        }
        if (priority != null) {
            spec = spec.and(TaskSpecifications.hasPriority(priority));
        }

        // Execute the query
        Page<Task> taskPage = taskRepository.findAll(spec, pageable);

        // Convert to PagedResponse
        return PagedResponse.from(taskPage, TaskResponse::from);
    }
}
