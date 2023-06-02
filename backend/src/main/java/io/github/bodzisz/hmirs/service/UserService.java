package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.dto.NewUserDTO;
import io.github.bodzisz.hmirs.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    
    User getUser(final int id);
    
    User addUser(final NewUserDTO user);

    User getByLogin(final String login);
    
    User deleteUser(final int id);
    
    void updateUser(final int id, final User user);

    boolean userExists(final String login);

    long getUsersCount();
}
