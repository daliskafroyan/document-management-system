package com.dms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFolderRequest {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long subjectId;
}
