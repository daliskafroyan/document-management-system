package com.dms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubjectRequest {

    @Schema(example = "Mechanical Engineering")
    @NotBlank
    private String name;

    private Long parentSubjectId;
}
