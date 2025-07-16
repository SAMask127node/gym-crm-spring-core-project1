// src/main/java/com/epam/gym/domain/Trainee.java
package com.epam.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "trainee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** maps to trainee.date_of_birth */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /** maps to trainee.address */
    @Column(length = 255)
    private String address;

    /** one‑to‑one to users */
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** many‑to‑many via trainer_trainee */
    @ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns        = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers;

    /** one‑to‑many to trainings */
    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Training> trainings;
}
