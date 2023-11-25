package ru.nikitavov.avenir.database.repository.realisation;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitavov.avenir.database.model.entity.Garbage;

public interface GarbageRepository extends JpaRepository<Garbage, Integer> {
}