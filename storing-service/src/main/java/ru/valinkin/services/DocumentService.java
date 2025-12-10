package ru.valinkin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.valinkin.exceptions.SaveDocumentException;
import ru.valinkin.models.Document;
import ru.valinkin.repositories.DocumentRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, RestTemplate restTemplate) {
        this.documentRepository = documentRepository;
        this.restTemplate = restTemplate;
    }

    Map<String, Integer> getSentences(String text){
        List<String> allSentences = Arrays.stream(text.split("[.!?]")).toList();
        Map<String, Integer> returnSentences  = new HashMap<>();
        for (String sentence : allSentences){
            if (returnSentences.containsKey(sentence)){
                returnSentences.put(sentence, returnSentences.get(sentence) + 1);
            } else {
                returnSentences.put(sentence, 1);
            }
        }
        return returnSentences;
    }

    public int getPercentOfPlagiate(String cur_text) {
        int max_percentage = 0;
        List<String> all_texts = documentRepository.findAll().stream().map(Document::getText).toList();
        Map<String, Integer> sentences = getSentences(cur_text);
        int all = 0;
        for (String sentence: sentences.keySet()){
            all += sentences.get(sentence);
        }
        for (String checked_text: all_texts){
            Map<String, Integer> checked_sentences = getSentences(checked_text);
            int now = 0;
            for (String sentence: sentences.keySet()){
                if (checked_sentences.containsKey(sentence)){
                    now += Math.min(checked_sentences.get(sentence), sentences.get(sentence));
                }
            }
            int percentage = now * 100 / all;
            max_percentage = Math.max(max_percentage, percentage);
        }
        System.out.println(max_percentage);
        return max_percentage;
    }

    @Transactional
    public void Save(Document document){
        if (document.getAuthor().isEmpty()) throw new SaveDocumentException("Автор не может быть пустым!");
        if (document.getText().isEmpty()) throw new SaveDocumentException("Текст не может быть пустым!");
        restTemplate.postForEntity("http://analysis-service/api/analysis/post/analyze",
                new RequestDTO(document.getAuthor(), getPercentOfPlagiate(document.getText())),
                Void.class);
        documentRepository.save(document);
    }

    public static class RequestDTO {
        public String text;
        public int percentage;

        public RequestDTO() {}

        public RequestDTO(String text, int percentage) {
            this.text = text;
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class EmptyResponseDTO {
        public EmptyResponseDTO() {}
    }
}
