package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "camera_offenses")
public class CameraOffense {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "date_detect", nullable = false)
    Date dateDetect;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "camera_id", nullable = false)
    Camera camera;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "offender_id", nullable = false)
    DetectionOffender offender;

    @NotBlank
    @Column(name = "violation_type", nullable = false)
    String violationType;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany
    @JoinColumn(name = "camera_offence_id")
    final Set<DetectionElement> detectionElements = new LinkedHashSet<>();
}
