package io.github.bodzisz.hmirs.dto;

import io.github.bodzisz.hmirs.entity.Role;

public record NewUserDTO(String firstName, String lastName, String login, String password, Role role) {
}
