// src/main/java/com/epam/gym/domain/Training.java
package com.epam.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "training")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** maps to training.name */
    @Column(nullable = false, length = 100)
    private String name;

    /** maps to training.trainee_id */
    @ManyToOne(optional = false)
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    /** maps to training.trainer_id */
    @ManyToOne(optional = false)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    /** maps to training.type_id */
    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private TrainingType type;

    /** maps to training.date */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /** maps to training.duration */
    @Column(name = "duration", nullable = false)
    private int duration;

    /** maps to training.active */
    @Column(nullable = false)
    private boolean active;
}
