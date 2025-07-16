// src/main/java/com/epam/gym/domain/Trainer.java
package com.epam.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** maps to trainer.specialization */
    @Column(length = 100)
    private String specialization;

    /** one‑to‑one to users */
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** many‑to‑many back to trainees */
    @ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees;

    /** one‑to‑many to trainings */
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Training> trainings;
}
