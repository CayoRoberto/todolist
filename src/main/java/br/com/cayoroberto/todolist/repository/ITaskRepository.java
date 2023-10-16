package br.com.cayoroberto.todolist.repository;

import br.com.cayoroberto.todolist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByIdUser(UUID idUser);

}
