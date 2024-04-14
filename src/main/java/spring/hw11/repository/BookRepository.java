package spring.hw11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hw11.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByName(String name);
}
