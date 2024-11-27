package com.project.SSPS.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticResponse {
    @JsonProperty("total_printing")
    private long totalPrinting;
    @JsonProperty("total_purchasing")
    private long totalPurchasing;
}
