package com.producerkafka.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoachData {
    @JsonProperty("coachClass")
    private String coachClass;
    
    @JsonProperty("toilet")
    private ToiletAvailabilityType toilet;
    
    @JsonProperty("loading")
    private Integer loading;
    
    @JsonProperty("loadingSpecified")
    private Boolean loadingSpecified;
    
    @JsonProperty("number")
    private String number;
}

