package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.repository.UserRepository;
import io.github.bodzisz.hmirs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){this.userRepository = userRepository;}

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        Optional<User> search = userRepository.findById(id);
        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", id));
        return search.get();
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(int id) {
        Optional<User> toDelete = userRepository.findById(id);
        if (toDelete.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User of id=%d was not found", id));
        userRepository.deleteById(id);
        return toDelete.get();
    }

    @Override
    public void updateUser(final int id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setTokenExpired(user.isTokenExpired());
            existingUser.setEnabled(user.isEnabled());
            existingUser.setPassword(user.getPassword());
            existingUser.setLogin(user.getLogin());
            userRepository.save(existingUser);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("User of id=%d was not found", id));
        }
    }
}
