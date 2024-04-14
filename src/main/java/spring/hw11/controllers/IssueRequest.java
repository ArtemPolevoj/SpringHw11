package spring.hw11.controllers;

import lombok.Data;

@Data
public class IssueRequest {
    private Long idReader;
    private Long bookId;
}
