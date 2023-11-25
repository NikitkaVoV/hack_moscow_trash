package ru.nikitavov.avenir.database.repository.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitavov.avenir.database.model.entity.DetectionElement;

public interface DetectionElementRepository extends JpaRepository<DetectionElement, Integer> {
}