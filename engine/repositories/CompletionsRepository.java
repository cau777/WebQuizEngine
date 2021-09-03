package engine.repositories;

import engine.models.CompletionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionsRepository extends PagingAndSortingRepository<CompletionInfo, Long> {
    Page<CompletionInfo> findByEmailOrderByCompletedAtDesc(String email, Pageable pageable);
}
