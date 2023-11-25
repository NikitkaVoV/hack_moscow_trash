package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cameras")
public class Camera {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank
    @Column(name = "address", nullable = false)
    String address;

    @NotBlank
    @Column(name = "camera_url", nullable = false)
    String cameraUrl;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "point_id", nullable = false)
    Point point;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany
    @JoinColumn(name = "camera_id")
    final Set<CameraOffense> cameraOffenses = new LinkedHashSet<>();
}
