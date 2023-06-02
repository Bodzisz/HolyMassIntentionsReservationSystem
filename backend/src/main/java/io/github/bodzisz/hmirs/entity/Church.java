package io.github.bodzisz.hmirs.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "churches")
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;
    private int minimalOffering;
    @ManyToOne
    @JoinColumn(name = "parish_id")
    private Parish parish;

    public Church(String name, String city, int minimalOffering, Parish parish) {
        this.name = name;
        this.city = city;
        this.minimalOffering = minimalOffering;
        this.parish = parish;
    }
}
