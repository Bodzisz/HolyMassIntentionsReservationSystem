package io.github.bodzisz.hmirs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "parishes")
public class Parish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "main_priest_id")
    private User mainPriest;

    public Parish(final String name, final User mainPriest) {
        this.name = name;
        this.mainPriest = mainPriest;
    }
}
