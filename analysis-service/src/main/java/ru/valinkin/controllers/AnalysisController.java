package ru.valinkin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.valinkin.services.AnalyzeService;

@RestController
@RequestMapping("/api/analysis/")
public class AnalysisController {
    private final AnalyzeService analyzeService;
    private final RestTemplate restTemplate;

    @Autowired
    public AnalysisController(AnalyzeService analyzeService, RestTemplate restTemplate) {
        this.analyzeService = analyzeService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/get/analyse/{author}")
    public ResponseDTO analise(@PathVariable String author) {
        return new ResponseDTO(analyzeService.getPercentage(author));
    }

    @PostMapping("/post/analyze")
    public void saveAnalyze(@RequestBody RequestDTO requestDTO){
        System.out.println("I'm ready to save with " + requestDTO.getPercentage());
        analyzeService.save(requestDTO.getText(), requestDTO.getPercentage());
    }

    public static class ResponseDTO {
        private int percentage;

        public ResponseDTO() {}

        public ResponseDTO(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }

    public static class RequestDTO {
        private String text;
        private int percentage;

        public RequestDTO() {}

        public RequestDTO(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }
}
