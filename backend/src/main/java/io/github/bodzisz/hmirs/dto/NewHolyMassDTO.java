package io.github.bodzisz.hmirs.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record NewHolyMassDTO(LocalDate date, LocalTime startTime, int availableIntentions, int churchId) {
}
