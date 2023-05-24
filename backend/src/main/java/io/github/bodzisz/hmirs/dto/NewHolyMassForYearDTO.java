package io.github.bodzisz.hmirs.dto;

import java.time.LocalTime;

public record NewHolyMassForYearDTO(LocalTime startTime, int availableIntentions, int churchId) {
}
