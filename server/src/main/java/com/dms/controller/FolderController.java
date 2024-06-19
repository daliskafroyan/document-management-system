package com.dms.controller;

import com.dms.dto.CreateFolderRequest;
import com.dms.dto.GetAllFolderResponse;
import com.dms.dto.GetFolderDetailsResponse;
import com.dms.model.Folder;
import com.dms.model.Subject;
import com.dms.repository.FolderRepository;
import com.dms.service.FolderService;
import com.dms.security.CustomUserDetails;
import com.dms.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.dms.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;
    private final SubjectService subjectService;
    private final FolderRepository folderRepository;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetAllFolderResponse> getAllFolders(@RequestParam(value = "text", required = false) String text) {
        List<Folder> allFolders = (text == null) ? folderService.getAllFolders() : folderService.getAllFoldersContainingText(text);
        return allFolders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private GetAllFolderResponse convertToDTO(Folder folder) {
        return GetAllFolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .subjectId(folder.getSubject() != null ? folder.getSubject().getId() : null)
                .build();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFolder(@AuthenticationPrincipal CustomUserDetails currentUser,
                             @Valid @RequestBody CreateFolderRequest createFolderRequest) {
        Subject subject = subjectService.validateAndGetSubject(String.valueOf(createFolderRequest.getSubjectId()));

        Folder folder = new Folder();
        folder.setName(createFolderRequest.getName());
        folder.setSubject(subject);

        folderService.saveFolder(folder);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable Long id) {
        Folder folder = folderService.validateAndGetFolder(id);
        folderRepository.delete(folder);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetFolderDetailsResponse getFolderDetails(@PathVariable Long id) {
        return folderService.getFolderDetails(id);
    }
}
