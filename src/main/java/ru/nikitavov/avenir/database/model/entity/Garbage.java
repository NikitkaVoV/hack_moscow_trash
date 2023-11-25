package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.nikitavov.avenir.web.message.realization.enitiy.enums.GarbageType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "garbage")
public class Garbage {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank
    @Column(name = "photo", nullable = false)
    String photo;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "point_id", nullable = false)
    Point point;

    @Column(name = "garbage_type", nullable = false)
    @Enumerated(EnumType.STRING)
    GarbageType garbageType;

    @Column(name = "date_creation", nullable = false)
    Date dateCreation;
}
