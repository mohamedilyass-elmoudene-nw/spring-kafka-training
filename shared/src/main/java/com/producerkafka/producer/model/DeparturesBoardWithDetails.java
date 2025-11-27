package com.producerkafka.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeparturesBoardWithDetails {
    @JsonProperty("departures")
    private List<DepartureItemWithCallingPoints> departures;
    
    @JsonProperty("Xmlns")
    private XmlSerializerNamespaces xmlns;
    
    @JsonProperty("generatedAt")
    private LocalDateTime generatedAt;
    
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("crs")
    private String crs;
    
    @JsonProperty("filterLocationName")
    private String filterLocationName;
    
    @JsonProperty("filtercrs")
    private String filtercrs;
    
    @JsonProperty("filterType")
    private String filterType; // "to", "from"
    
    @JsonProperty("nrccMessages")
    private List<NRCCMessage> nrccMessages;
    
    @JsonProperty("platformAvailable")
    private Boolean platformAvailable;
    
    @JsonProperty("areServicesAvailable")
    private Boolean areServicesAvailable;
}

