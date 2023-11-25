package ru.nikitavov.avenir.database.repository.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitavov.avenir.database.model.entity.DetectionOffenderType;

public interface DetectionOffenderTypeRepository extends JpaRepository<DetectionOffenderType, Integer> {
}