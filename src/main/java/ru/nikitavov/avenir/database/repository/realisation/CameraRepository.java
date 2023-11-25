package ru.nikitavov.avenir.database.repository.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitavov.avenir.database.model.entity.Camera;
import ru.nikitavov.avenir.database.model.security.RedirectUrl;

import java.util.Optional;

public interface CameraRepository extends JpaRepository<Camera, Integer> {
}