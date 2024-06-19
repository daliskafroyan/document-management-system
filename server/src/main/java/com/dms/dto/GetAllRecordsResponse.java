package com.dms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllRecordsResponse {
    private Long id;
    private String name;
    private Long folderId;
}
