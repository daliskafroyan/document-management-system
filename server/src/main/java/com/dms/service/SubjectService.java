package com.dms.service;
import com.dms.dto.GetAllSubjectEager;
import com.dms.dto.UpdateSubjectRequest;
import com.dms.exception.SubjectNotFoundException;
import com.dms.model.Subject;
import com.dms.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }

    public List<Subject> getAllSubjectContainingText(String text) {
        return subjectRepository.findByNameContainingIgnoreCase(text);
    }

    public Subject validateAndGetSubject(String id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(String.format("Subject with id %s not found", id)));
    }

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteSubject(Subject subject) {
        subjectRepository.delete(subject);
    }


    public List<GetAllSubjectEager> getEagerSubjects() {
        List<Subject> subjects = subjectRepository.findByParentSubjectIsNull();
        return subjects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private GetAllSubjectEager convertToDto(Subject subject) {
        return GetAllSubjectEager.builder()
                .id(subject.getId())
                .name(subject.getName())
                .parentSubjectId(subject.getParentSubject() != null ? subject.getParentSubject().getId() : null)
                .childSubjects(subject.getChildSubjects().stream().map(this::convertToDto).collect(Collectors.toList()))
                .folders(subject.getFolders().stream().map(folder ->
                        GetAllSubjectEager.FolderDto.builder()
                                .id(folder.getId())
                                .name(folder.getName())
                                .build()).collect(Collectors.toList()))
                .build();
    }

    public Subject updateSubject(UpdateSubjectRequest updateSubjectRequest) {
        Subject subject = validateAndGetSubject(updateSubjectRequest.getId().toString());

        Subject parentSubject = null;
        if (updateSubjectRequest.getParentSubjectId() != null) {
            Optional<Subject> optionalParentSubject = subjectRepository.findById(String.valueOf(updateSubjectRequest.getParentSubjectId()));
            if (optionalParentSubject.isPresent()) {
                parentSubject = optionalParentSubject.get();
            } else {
                throw new IllegalArgumentException("Parent subject not found");
            }
        }

        subject.setName(updateSubjectRequest.getName());
        subject.setParentSubject(parentSubject);

        return subjectRepository.save(subject);
    }
}
