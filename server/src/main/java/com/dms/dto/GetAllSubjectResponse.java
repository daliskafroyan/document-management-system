package com.dms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GetAllSubjectResponse {
    private Long id;
    private String name;
    private Long parentSubjectId;
    private boolean hasChildren;
    private Set<ChildSubjectResponse> childSubjects;
    private Set<FolderResponse> folders;

    @Data
    @Builder
    public static class ChildSubjectResponse {
        private Long id;
        private String name;
        private boolean hasChildren;
    }

    @Data
    @Builder
    public static class FolderResponse {
        private Long id;
        private String name;
        private boolean hasChildren;
    }
}
