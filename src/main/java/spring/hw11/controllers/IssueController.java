package spring.hw11.controllers;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.hw11.model.Issue;
import spring.hw11.servies.IssueService;

import java.util.NoSuchElementException;
import java.util.TreeMap;

@Slf4j
@RestController
@RequestMapping("issue")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService service;
    private final Counter countIssue = Metrics.counter("count_issue");
    private final Counter errorIssue = Metrics.counter("count_error_issue");
    private final Counter maxCountIssue = Metrics.counter("max_count_issue");
    private final Counter returnedIssue = Metrics.counter("count_issue_returned");

    @PostMapping
    public ResponseEntity<Issue> issueBook(@RequestBody IssueRequest issueRequest) {
        log.info("Поступил запрос на выдачу: idReader={}, bookId={}"
                , issueRequest.getIdReader(), issueRequest.getBookId());

        try {
            Issue issue = service.createIssue(issueRequest);
            if (issue.getIdReader() != -1L && issue.getIdBook() != -1L) {
                countIssue.increment();
                return ResponseEntity.status(HttpStatus.CREATED).body(issue);
            } else {
                maxCountIssue.increment();
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

        } catch (NoSuchElementException e) {
            errorIssue.increment();
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<TreeMap<String, String>> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (NoSuchElementException e) {
            errorIssue.increment();
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{issueId}")
    public ResponseEntity<TreeMap<String, String>> returnedAt(@PathVariable Long issueId) {
        try {
            service.setReturnedAt(issueId);
            returnedIssue.increment();
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(issueId));
        } catch (NoSuchElementException e) {
            errorIssue.increment();
            return ResponseEntity.notFound().build();
        }
    }

}
