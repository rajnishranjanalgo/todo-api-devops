package com.example.todo;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class TodoController {
    private final List<Todo> todos = new CopyOnWriteArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger();
    public record Todo(int id, String title, boolean done) {}
    public record TodoRequest(String title) {}
    @GetMapping("/health") public Map<String, String> health() { return Map.of("status", "ok"); }
    @GetMapping("/todos") public List<Todo> list() { return todos; }
    @PostMapping("/todos") public ResponseEntity<?> add(@RequestBody(required = false) TodoRequest request) {
        if (request == null || request.title() == null || request.title().isBlank()) return ResponseEntity.badRequest().body(Map.of("error", "'title' is required"));
        Todo todo = new Todo(nextId.incrementAndGet(), request.title().trim(), false);
        todos.add(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }
}
