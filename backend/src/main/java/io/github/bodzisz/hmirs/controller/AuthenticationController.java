package io.github.bodzisz.hmirs.controller;

import io.github.bodzisz.hmirs.conifg.JwtUtils;
import io.github.bodzisz.hmirs.dto.AuthenticationRequest;
import io.github.bodzisz.hmirs.dto.AuthenticationResponse;
import io.github.bodzisz.hmirs.entity.User;
import io.github.bodzisz.hmirs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtUtils jwtUtils;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.login());
        if(userDetails != null) {
            User user = userService.getByLogin(request.login());
            return ResponseEntity.ok(new AuthenticationResponse(jwtUtils.generateToken(userDetails), userDetails.getUsername(),
                    user.getFirstName(), user.getLastName(), user.getRole()));
        }
        return ResponseEntity.status(400).build();
    }
}
