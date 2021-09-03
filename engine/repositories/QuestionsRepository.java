package engine.repositories;

import engine.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuestionsRepository extends PagingAndSortingRepository<Question, Integer> {
}
