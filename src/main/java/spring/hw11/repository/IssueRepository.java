package spring.hw11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.hw11.model.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

     default int findCountBooksByIdReader(Long idReader){
        int count = 0;
        for (Issue issue:findAll()){
            if (issue.getIdReader().equals(idReader) && issue.getReturnedAt() == null){
                count++;
            }
        }
        return count;
    }
    default List<Issue> findIssueAllByIdReader(Long idReader){
        List<Issue> temp = new ArrayList<>();
        for (Issue issue:findAll()){
            if (issue.getIdReader().equals(idReader)){
                temp.add(issue);
            }
        }
        return temp;
    }

    default void setReturnedAt(Long id){
        for (Issue issue:findAll()){
            if (issue.getId().equals(id) && issue.getReturnedAt() == null){
                issue.setReturnedAt(LocalDateTime.now());
                save(issue);
            }
        }
    }
}
