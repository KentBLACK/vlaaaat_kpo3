package ru.valinkin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.valinkin.exceptions.SaveDocumentException;
import ru.valinkin.models.Document;
import ru.valinkin.services.DocumentService;

@RestController
@RequestMapping("/api/storing/")
@Tag(name = "Сервис документов", description = "Работа с документами")
public class StoringController {
    private final DocumentService documentService;

    @Autowired
    public StoringController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/get/analyse/{author}")
    @Operation(summary = "Получить процент плагиата по автору")
    public ResponseDTO getPercentageOfPlagiate(@PathVariable String author){
        return new ResponseDTO(documentService.getPercentOfPlagiate(author));
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранить документ")
    public void saveDocument(@RequestBody RequestDTO requestDTO){
        documentService.Save(new Document(requestDTO.getText(), requestDTO.getAuthor()));
    }

    public static class ResponseDTO{
        private int percentage;

        public ResponseDTO(){}

        public ResponseDTO(int percentage){
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }

    public static class RequestDTO{
        private String author;
        private String text;

        public RequestDTO(){}

        public RequestDTO(String author, String text){
            this.author = author;
            this.text = text;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
