package ru.valinkin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.valinkin.models.Analyze;

@Repository
public interface AnalyzeRepository extends JpaRepository<Analyze, Long> {
    Analyze findAllByText(String author);
}
