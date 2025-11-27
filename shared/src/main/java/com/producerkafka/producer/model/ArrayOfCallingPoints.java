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
public class ArrayOfCallingPoints {
    @JsonProperty("callingPoint")
    private List<CallingPoint> callingPoint;
    
    @JsonProperty("serviceType")
    private String serviceType; // "train", "bus", "ferry"
    
    @JsonProperty("serviceChangeRequired")
    private Boolean serviceChangeRequired;
    
    @JsonProperty("assocIsCancelled")
    private Boolean assocIsCancelled;
}

