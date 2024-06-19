package com.dms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateFolderRequest {
    private String name;
    private Long subjectId;
}
