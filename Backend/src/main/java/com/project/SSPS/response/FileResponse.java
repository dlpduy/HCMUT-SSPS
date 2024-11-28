package com.project.SSPS.response;

import com.project.SSPS.model.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {
    private Long fileId;
    private String fileName;
    private String fileType;

    public static FileResponse fromFile(File file) {
        FileResponse response = new FileResponse();
        response.setFileId(file.getId());
        response.setFileName(file.getFileName());
        response.setFileType(file.getFileType());
        return response;
    }
}
