package com.dms.repository;

import com.dms.model.Subject;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, String> {
//    List<Subject> findAllBySubjectByCreatedAtDesc();
    List<Subject> findByNameContainingIgnoreCase(String name);

    @EntityGraph(attributePaths = {"childSubjects", "folders"})
    List<Subject> findByParentSubjectIsNull();

//    List<Subject> findByIdContainingOrDescriptionContainingIgnoreCaseOrderByCreatedAt(String id, String description);
}