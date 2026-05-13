CREATE TABLE tasks
(
    id          UUID         NOT NULL,
    title       VARCHAR(200) NOT NULL,
    description TEXT,
    status      VARCHAR(20)  NOT NULL,
    priority    VARCHAR(20)  NOT NULL,
    due_date    TIMESTAMP WITHOUT TIME ZONE,
    user_id     UUID,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tasks PRIMARY KEY (id)
);

CREATE INDEX idx_tasks_due_date ON tasks (due_date);

CREATE INDEX idx_tasks_priority ON tasks (priority);

CREATE INDEX idx_tasks_status ON tasks (status);

ALTER TABLE tasks
    ADD CONSTRAINT FK_TASKS_USER_ID FOREIGN KEY (user_id) REFERENCES users (id);

CREATE INDEX idx_tasks_user_id ON tasks (user_id);