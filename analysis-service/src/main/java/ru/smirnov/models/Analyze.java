package ru.smirnov.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Analyzes")
public class Analyze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "percent")
    private int percent_of_plagiate;

    @Column(name = "text")
    @NotBlank(message = "Text is required!")
    private String text;

    public Analyze() {}

    public Analyze(String text, int percent_of_plagiate) {
        this.text = text;
        this.percent_of_plagiate = percent_of_plagiate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPercent_of_plagiate() {
        return percent_of_plagiate;
    }

    public void setPercent_of_plagiate(int percent_of_plagiate) {
        this.percent_of_plagiate = percent_of_plagiate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
