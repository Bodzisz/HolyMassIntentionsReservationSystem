package io.github.bodzisz.hmirs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "intentions")
public class Intention {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "holy_mass_id")
    private HolyMass holyMass;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
