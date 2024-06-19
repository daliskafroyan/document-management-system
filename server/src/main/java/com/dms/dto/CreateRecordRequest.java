package com.dms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRecordRequest {
    private String name;
    private Long folderId;
}

