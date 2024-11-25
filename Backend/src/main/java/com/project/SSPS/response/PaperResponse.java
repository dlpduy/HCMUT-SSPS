package com.project.SSPS.response;

import com.project.SSPS.model.Paper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperResponse {
    private Long id;
    private String type;
    private Long width;
    private Long height;
    private Long price;
    private String createBy;

    public static PaperResponse fromPaper(Paper paper) {
        return PaperResponse.builder()
//                .id(paper.getId())
                .type(paper.getType())
                .width(paper.getWidth())
                .height(paper.getHeight())
                .price(paper.getPrice())
                .createBy(paper.getUser().getUsername())
                .build();
    }
}
