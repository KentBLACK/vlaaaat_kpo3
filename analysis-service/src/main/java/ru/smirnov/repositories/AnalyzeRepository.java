package ru.smirnov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.smirnov.models.Analyze;

@Repository
public interface AnalyzeRepository extends JpaRepository<Analyze, Long> {
    Analyze findAllByText(String author);
}
