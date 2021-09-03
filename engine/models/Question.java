package engine.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Entity
public class Question {
    private static int currentId = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @ElementCollection
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @ElementCollection
    private List<Integer> answer = new ArrayList<>(0);

    @JsonIgnore
    private String author;

    public Question() {
        this.id = currentId;
        currentId++;
    }

    public boolean analyzeAnswer(List<Integer> chosen) {
        if (chosen == null) return false;

        if (chosen.size() != answer.size()) return false;

        return Stream.concat(answer.stream(), chosen.stream()).distinct().count() == chosen.size();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
