// src/main/java/com/epam/gym/domain/TrainingType.java
package com.epam.gym.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "training_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** maps to training_type.name */
    @Column(nullable = false, length = 255)
    private String name;

    /** back‑ref from trainings */
    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Set<Training> trainings;
}
