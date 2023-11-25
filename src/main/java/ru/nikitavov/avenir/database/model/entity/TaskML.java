package ru.nikitavov.avenir.database.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task_mls")
public class TaskML {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    @Column(name = "original_name", nullable = false)
    String originalName;

    @NotBlank
    @Column(name = "path", nullable = false, length = 500)
    String path;

    @NotNull
    @Column(name = "uuid", nullable = false)
    UUID uuid;

    @Column(name = "date_create", nullable = false)
    Date dateCreate;

    @Column(name = "is_video", nullable = false)
    Boolean isVideo;

}
