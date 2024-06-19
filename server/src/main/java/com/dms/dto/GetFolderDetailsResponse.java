package com.dms.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class GetFolderDetailsResponse {
    private Long id;
    private String name;
    private Long subjectId;
    private Set<RecordResponse> records;

    @Data
    @Builder
    public static class RecordResponse {
        private Long id;
        private String name;
    }
}
