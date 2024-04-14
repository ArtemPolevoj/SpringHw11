package spring.hw11.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "issues")
@NoArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_reader")
    private Long idReader;
    @Column(name = "id_book")
    private Long idBook;
    @Column(name = "issued_at")
    private LocalDateTime issuedAt;
    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    public Issue(Long idReader, Long idBook){
        this.idBook = idBook;
        this.idReader = idReader;
        issuedAt = LocalDateTime.now();
        returnedAt = null;
    }
}
