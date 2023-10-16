package br.com.cayoroberto.todolist.domain.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    /**
     * ID
     * Usuário (ID_Usuario)
     * Descricao
     * Título
     * DataInicio
     * DataFim
     * Prioridade
     *
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;



    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Priority priority;
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;


    public Task(TaskDTO task){
        this.title = task.title();
        this.description = task.description();
        this.startAt = task.startAt();
        this.endAt = task.endAt();
        this.priority = task.priority();
        this.idUser = task.idUser();

    }

    public void setTitle(String title) throws Exception {
        if(title.length() > 50){
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }

}
