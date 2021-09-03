package engine.components;

import engine.models.Question;
import engine.repositories.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QuestionsComponent {
    private final QuestionsRepository repository;

    @Autowired
    public QuestionsComponent(QuestionsRepository repository) {
        this.repository = repository;
    }

    public Optional<Question> findById(int id) {
        return repository.findById(id);
    }

    public Page<Question> getQuestions(int page) {
        return repository.findAll(PageRequest.of(page, 10));
    }

    public Question add(Question question) {
        return repository.save(question);
    }

    public void remove(Question question) {
        repository.delete(question);
    }
}
