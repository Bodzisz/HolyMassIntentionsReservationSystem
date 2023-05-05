package io.github.bodzisz.hmirs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "parish_id")
    private Parish parish;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int amount;
}
