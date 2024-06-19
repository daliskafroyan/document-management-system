package com.dms.service;

import com.dms.dto.GetFolderDetailsResponse;
import com.dms.model.Folder;
import com.dms.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    public List<Folder> getAllFoldersContainingText(String text) {
        return folderRepository.findByNameContaining(text);
    }

    public void saveFolder(Folder folder) {
        folderRepository.save(folder);
    }

    public GetFolderDetailsResponse getFolderDetails(Long id) {
        Folder folder = validateAndGetFolder(id);

        Set<GetFolderDetailsResponse.RecordResponse> records = folder.getRecords().stream()
                .map(this::convertToRecordResponse)
                .collect(Collectors.toSet());

        return GetFolderDetailsResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .subjectId(folder.getSubject() != null ? folder.getSubject().getId() : null)
                .records(records)
                .build();
    }

    private GetFolderDetailsResponse.RecordResponse convertToRecordResponse(com.dms.model.Record record) {
        return GetFolderDetailsResponse.RecordResponse.builder()
                .id(record.getId())
                .name(record.getName())
                .build();
    }

    public Folder validateAndGetFolder(Long id) {
        return folderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + id));
    }
}
