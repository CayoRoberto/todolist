package br.com.cayoroberto.todolist.domain.task;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDTO(String title, String description, LocalDateTime startAt, LocalDateTime endAt, Priority priority, UUID idUser) {
}
