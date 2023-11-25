package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "points")
public class Point {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "lat", nullable = false, scale = 10)
    BigDecimal lat;

    @Column(name = "lon", nullable = false, scale = 10)
    BigDecimal lon;
}
