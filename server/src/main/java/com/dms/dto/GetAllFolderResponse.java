package com.dms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllFolderResponse {
    private Long id;
    private String name;
    private Long subjectId;
}