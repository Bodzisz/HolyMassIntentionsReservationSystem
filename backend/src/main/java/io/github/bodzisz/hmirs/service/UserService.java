package io.github.bodzisz.hmirs.service;

import io.github.bodzisz.hmirs.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    
    User getUser(final int id);
    
    User addUser(final User user);
    
    User deleteUser(final int id);
    
    void updateUser(final int id, final User user);
}
