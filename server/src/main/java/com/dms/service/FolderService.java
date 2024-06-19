package com.dms.service;

import com.dms.model.Folder;
import com.dms.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Folder validateAndGetFolder(Long id) {
        return folderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Folder not found"));
    }
}
