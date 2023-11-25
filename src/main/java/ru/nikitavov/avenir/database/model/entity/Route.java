package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.nikitavov.avenir.web.message.realization.enitiy.enums.ERouteStatus;
import ru.nikitavov.avenir.web.message.realization.enitiy.enums.WasteType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
@Table(name = "routes")
public class Route {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    ERouteStatus status;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(
            name = "route_cameras",
            joinColumns = {@JoinColumn(name = "route_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "camera_id", referencedColumnName = "id")}
    )
    final Set<Camera> cameras = new LinkedHashSet<>();

    @Column(name = "date_departure", nullable = false)
    Date dateDeparture;

    @Column(name = "date_arrival", nullable = false)
    Date dateArrival;

    @NotBlank
    @Column(name = "waste_type", nullable = false)
    @Enumerated(EnumType.STRING)
    WasteType wasteType;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany
    final Set<Point> points = new LinkedHashSet<>();
}
