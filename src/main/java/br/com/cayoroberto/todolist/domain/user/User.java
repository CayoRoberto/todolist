package br.com.cayoroberto.todolist.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tb_users")

@Data //Add Getters e Setters
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(UserDTO user){
        this.username = user.username();
        this.name = user.name();
        this.password = user.password();
    }
}
