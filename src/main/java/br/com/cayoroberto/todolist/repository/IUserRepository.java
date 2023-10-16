package br.com.cayoroberto.todolist.repository;

import br.com.cayoroberto.todolist.domain.user.UserDTO;
import br.com.cayoroberto.todolist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    UserDTO findByUsername(String username);

    User findUserByUsername(String username);


}
