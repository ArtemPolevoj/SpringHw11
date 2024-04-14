package spring.hw11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hw11.model.Reader;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Reader findByName(String name);
}
