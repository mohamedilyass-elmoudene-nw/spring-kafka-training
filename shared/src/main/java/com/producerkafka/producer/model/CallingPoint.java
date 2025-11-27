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
public class CallingPoint {
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("crs")
    private String crs;
    
    @JsonProperty("st")
    private String st;
    
    @JsonProperty("et")
    private String et;
    
    @JsonProperty("at")
    private String at;
    
    @JsonProperty("isCancelled")
    private Boolean isCancelled;
    
    @JsonProperty("length")
    private Integer length;
    
    @JsonProperty("detachFront")
    private Boolean detachFront;
    
    @JsonProperty("formation")
    private FormationData formation;
    
    @JsonProperty("adhocAlerts")
    private List<String> adhocAlerts;
}

