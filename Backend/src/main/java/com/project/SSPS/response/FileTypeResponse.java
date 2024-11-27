package com.project.SSPS.response;

import com.project.SSPS.model.FileType;
import com.project.SSPS.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileTypeResponse {
    private Long id;
    private String type;
    private String description;
    private String createBy;

    public static FileTypeResponse fromFileType(FileType fileType) {
        return FileTypeResponse.builder()
                .id(fileType.getId())
                .type(fileType.getType())
                .description(fileType.getDescription())
                .createBy(fileType.getUser().getUsername())
                .build();
    }
}
