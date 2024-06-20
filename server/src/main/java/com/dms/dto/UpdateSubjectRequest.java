package com.dms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSubjectRequest {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}
