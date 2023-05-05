package io.github.bodzisz.hmirs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "holyMasses")
public class HolyMass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime startTime;
    private int availableIntentions;
    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;
}
