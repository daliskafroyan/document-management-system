package com.dms.controller;

import com.dms.dto.CreateSubjectRequest;
import com.dms.dto.GetAllSubjectEager;
import com.dms.dto.GetAllSubjectResponse;
import com.dms.dto.GetAllSubjectResponse.ChildSubjectResponse;
import com.dms.dto.GetAllSubjectResponse.FolderResponse;
import com.dms.dto.UpdateSubjectRequest;
import com.dms.model.Subject;
import com.dms.model.Folder;
import com.dms.repository.SubjectRepository;
import com.dms.security.CustomUserDetails;
import com.dms.service.SubjectService;
import com.dms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dms.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final UserService userService;
    private final SubjectRepository subjectRepository;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetAllSubjectResponse> getAllSubject(@RequestParam(value = "text", required = false) String text) {
        List<Subject> allSubjects = (text == null) ? subjectService.getAllSubject() : subjectService.getAllSubjectContainingText(text);

        Set<Long> childSubjectIds = new HashSet<>();
        allSubjects.forEach(subject -> gatherChildSubjectIds(subject, childSubjectIds));

        return allSubjects.stream()
                .filter(subject -> !childSubjectIds.contains(subject.getId()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetAllSubjectResponse getSubjectDetails(@PathVariable Long id) {
        Subject subject = subjectService.validateAndGetSubject(id.toString());
        return convertToDTO(subject);
    }

    private void gatherChildSubjectIds(Subject subject, Set<Long> childSubjectIds) {
        subject.getChildSubjects().forEach(child -> {
            childSubjectIds.add(child.getId());
            gatherChildSubjectIds(child, childSubjectIds);
        });
    }

    private GetAllSubjectResponse convertToDTO(Subject subject) {
        boolean hasChildren = !subject.getChildSubjects().isEmpty() || !subject.getFolders().isEmpty();

        return GetAllSubjectResponse.builder()
                .id(subject.getId())
                .name(subject.getName())
                .parentSubjectId(subject.getParentSubject() != null ? subject.getParentSubject().getId() : null)
                .hasChildren(hasChildren)
                .childSubjects(subject.getChildSubjects().stream().map(this::convertToChildDTO).collect(Collectors.toSet()))
                .folders(subject.getFolders().stream().map(this::convertToFolderDTO).collect(Collectors.toSet()))
                .build();
    }

    private ChildSubjectResponse convertToChildDTO(Subject subject) {
        return ChildSubjectResponse.builder()
                .id(subject.getId())
                .name(subject.getName())
                .hasChildren(!subject.getChildSubjects().isEmpty() || !subject.getFolders().isEmpty())
                .build();
    }

    private FolderResponse convertToFolderDTO(Folder folder) {
        return FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .hasChildren(false)
                .build();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createSubject(@AuthenticationPrincipal CustomUserDetails currentUser,
                              @Valid @RequestBody CreateSubjectRequest createSubjectRequest) {
        userService.validateAndGetUserByUsername(currentUser.getUsername());

        Subject parentSubject = null;
        if (createSubjectRequest.getParentSubjectId() != null) {
            Optional<Subject> optionalParentSubject = subjectRepository.findById(String.valueOf(createSubjectRequest.getParentSubjectId()));
            if (optionalParentSubject.isPresent()) {
                parentSubject = optionalParentSubject.get();
            } else {
                throw new IllegalArgumentException("Parent subject not found");
            }
        }

        Subject subject = new Subject();
        subject.setName(createSubjectRequest.getName());
        subject.setParentSubject(parentSubject);
        subject.setChildSubjects(new HashSet<>());
        subject.setFolders(new HashSet<>());

        subjectService.saveSubject(subject);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        Subject subject = subjectService.validateAndGetSubject(id.toString());
        subjectRepository.delete(subject);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PutMapping("/{id}")
    public ResponseEntity<GetAllSubjectResponse> updateSubject(@AuthenticationPrincipal CustomUserDetails currentUser,
                                                               @PathVariable Long id,
                                                               @Valid @RequestBody UpdateSubjectRequest updateSubjectRequest) {
        updateSubjectRequest.setId(id);
        Subject updatedSubject = subjectService.updateSubject(updateSubjectRequest);
        GetAllSubjectResponse response = convertToDTO(updatedSubject);
        return ResponseEntity.ok(response);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/eager")
    public List<GetAllSubjectEager> getEagerData() {
        return subjectService.getEagerSubjects();
    }
}
