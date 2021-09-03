package engine.components;

import engine.models.CompletionInfo;
import engine.repositories.CompletionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class CompletionsComponent {
    private final CompletionsRepository repository;

    @Autowired
    public CompletionsComponent(CompletionsRepository repository) {
        this.repository = repository;
    }

    public Page<CompletionInfo> findCompletionsByUser(String email, int page) {
        return repository.findByEmailOrderByCompletedAtDesc(email, PageRequest.of(page, 10));
    }

    public void add(CompletionInfo info) {
        repository.save(info);
    }
}
