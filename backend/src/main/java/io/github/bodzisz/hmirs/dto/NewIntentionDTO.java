package io.github.bodzisz.hmirs.dto;

public record NewIntentionDTO(String content, boolean isPaid, int holyMassId, int userId) {
}
