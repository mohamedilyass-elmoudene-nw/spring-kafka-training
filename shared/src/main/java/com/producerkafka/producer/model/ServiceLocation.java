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
public class ServiceLocation {
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("crs")
    private String crs;
    
    @JsonProperty("via")
    private String via;
    
    @JsonProperty("futureChangeTo")
    private String futureChangeTo;
    
    @JsonProperty("assocIsCancelled")
    private Boolean assocIsCancelled;
}

