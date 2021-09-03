package engine.controllers;

import engine.components.CompletionsComponent;
import engine.components.QuestionsComponent;
import engine.models.CompletionInfo;
import engine.models.Question;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
public class QuizController {
    private final CompletionsComponent completions;
    private final QuestionsComponent questions;
    private final Supplier<RuntimeException> notFoundSupplier = () -> new ResponseStatusException(HttpStatus.NOT_FOUND);

    @Autowired
    public QuizController(CompletionsComponent completions, QuestionsComponent questions) {
        this.completions = completions;
        this.questions = questions;
    }

    @GetMapping("/api/quizzes")
    public Object getQuizzes(@RequestParam int page) {
        return questions.getQuestions(page);
    }

    @GetMapping("/api/quizzes/{id}")
    public Object getQuiz(@PathVariable int id) {
        return questions.findById(id).orElseThrow(notFoundSupplier);
    }

    @PostMapping("/api/quizzes")
    public Object postQuestion(@Valid @RequestBody Question data, Principal principal) {
        data.setAuthor(principal.getName());
        return questions.add(data);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Object postAnswer(@PathVariable int id, @RequestBody Map<String, ?> data, Principal principal) {
        Question question = questions.findById(id).orElseThrow(notFoundSupplier);
        List<Integer> answers = (List<Integer>) data.get("answer");

        if (question.analyzeAnswer(answers)) {
            completions.add(new CompletionInfo(question.getId(), principal.getName()));
            return Map.of("success", true, "feedback", "Congratulations, you're right!");
        } else {
            return Map.of("success", false, "feedback", "Wrong answer! Please, try again.");
        }
    }

    @DeleteMapping("/api/quizzes/{id}")
    public Object deleteQuiz(@PathVariable int id, Principal principal) {
        Question question = questions.findById(id).orElseThrow(notFoundSupplier);

        if (!principal.getName().equals(question.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        questions.remove(question);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/quizzes/completed")
    public Object getCompleted(@RequestParam int page, Principal principal) {
        return completions.findCompletionsByUser(principal.getName(), page);
    }
}
