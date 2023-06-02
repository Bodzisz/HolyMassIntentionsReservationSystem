package io.github.bodzisz.hmirs.serviceimpl;

import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        final User user = userService.getByLogin(login);
        if(user == null) {
            throw new UsernameNotFoundException("No user was found");
        }

        return user;
    }
}
