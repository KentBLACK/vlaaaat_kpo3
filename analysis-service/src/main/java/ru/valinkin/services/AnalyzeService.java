package ru.valinkin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.valinkin.models.Analyze;
import ru.valinkin.repositories.AnalyzeRepository;

@Service
@Transactional(readOnly = true)
public class AnalyzeService {
    private final AnalyzeRepository analyzeRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public AnalyzeService(AnalyzeRepository analyzeRepository, RestTemplate restTemplate) {
        this.analyzeRepository = analyzeRepository;
        this.restTemplate = restTemplate;
    }
    public int getPercentage(String author){
        return analyzeRepository.findAllByText(author).getPercent_of_plagiate();
    }
    @Transactional
    public void save(String text, int percent_of_plagiate){
        analyzeRepository.save(new Analyze(text, percent_of_plagiate));
    }
}
