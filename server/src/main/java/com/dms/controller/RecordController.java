package com.dms.controller;

import com.dms.dto.CreateRecordRequest;
import com.dms.dto.GetAllRecordsResponse;
import com.dms.model.Record;
import com.dms.model.Folder;
import com.dms.repository.RecordRepository;
import com.dms.service.RecordService;
import com.dms.service.FolderService;
import com.dms.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.dms.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/records")
public class RecordController {

    private final RecordService recordService;
    private final FolderService folderService;
    private final RecordRepository recordRepository;

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<GetAllRecordsResponse> getAllRecords(@RequestParam(value = "text", required = false) String text) {
        List<Record> allRecords = (text == null) ? recordService.getAllRecords() : recordService.getAllRecordsContainingText(text);
        return allRecords.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private GetAllRecordsResponse convertToDTO(Record record) {
        return GetAllRecordsResponse.builder()
                .id(record.getId())
                .name(record.getName())
                .folderId(record.getFolder() != null ? record.getFolder().getId() : null)
                .build();
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createRecord(@AuthenticationPrincipal CustomUserDetails currentUser,
                             @Valid @RequestBody CreateRecordRequest createRecordRequest) {
        Folder folder = folderService.validateAndGetFolder(createRecordRequest.getFolderId());

        Record record = new Record();
        record.setName(createRecordRequest.getName());
        record.setFolder(folder);

        recordService.saveRecord(record);
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        Record record = recordService.validateAndGetRecord(id);
        recordRepository.delete(record);
    }
}
