package io.github.bodzisz.hmirs.dto;

import io.github.bodzisz.hmirs.entity.Role;

public record AuthenticationResponse(String token, String username, String firstName, String lastName, Role role) {
}
