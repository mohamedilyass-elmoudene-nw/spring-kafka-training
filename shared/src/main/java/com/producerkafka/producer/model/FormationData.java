package com.producerkafka.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormationData {
    @JsonProperty("loadingCategory")
    private LoadingCategory loadingCategory;
    
    @JsonProperty("coaches")
    private List<CoachData> coaches;
}

