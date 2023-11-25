package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.nikitavov.avenir.web.message.realization.enitiy.enums.WasteType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detection_element")
public class DetectionElement {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "camera_offence_id", nullable = false)
    CameraOffense cameraOffense;

    @Column(name = "waste_type", nullable = false)
    @Enumerated(EnumType.STRING)
    WasteType wasteType;

    @Column(name = "amount", nullable = false)
    Integer amount;
}
