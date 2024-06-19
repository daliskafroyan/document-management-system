package com.dms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllSubjectEager {
    private Long id;
    private String name;
    private Long parentSubjectId;
    private List<GetAllSubjectEager> childSubjects;
    private List<FolderDto> folders;

    @Data
    @Builder
    public static class FolderDto {
        private Long id;
        private String name;
    }
}
