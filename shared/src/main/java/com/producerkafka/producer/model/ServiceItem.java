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
public class ServiceItem {
    @JsonProperty("formation")
    private FormationData formation;
    
    @JsonProperty("origin")
    private List<ServiceLocation> origin;
    
    @JsonProperty("destination")
    private List<ServiceLocation> destination;
    
    @JsonProperty("currentOrigins")
    private List<ServiceLocation> currentOrigins;
    
    @JsonProperty("currentDestinations")
    private List<ServiceLocation> currentDestinations;
    
    @JsonProperty("rsid")
    private String rsid;
    
    @JsonProperty("sta")
    private String sta;
    
    @JsonProperty("eta")
    private String eta;
    
    @JsonProperty("std")
    private String std;
    
    @JsonProperty("etd")
    private String etd;
    
    @JsonProperty("platform")
    private String platform;
    
    @JsonProperty("operator")
    private String operator;
    
    @JsonProperty("operatorCode")
    private String operatorCode;
    
    @JsonProperty("isCircularRoute")
    private Boolean isCircularRoute;
    
    @JsonProperty("isCancelled")
    private Boolean isCancelled;
    
    @JsonProperty("filterLocationCancelled")
    private Boolean filterLocationCancelled;
    
    @JsonProperty("serviceType")
    private String serviceType; // "train", "bus", "ferry"
    
    @JsonProperty("length")
    private Integer length;
    
    @JsonProperty("detachFront")
    private Boolean detachFront;
    
    @JsonProperty("isReverseFormation")
    private Boolean isReverseFormation;
    
    @JsonProperty("cancelReason")
    private String cancelReason;
    
    @JsonProperty("delayReason")
    private String delayReason;
    
    @JsonProperty("serviceID")
    private String serviceID;
    
    @JsonProperty("adhocAlerts")
    private List<String> adhocAlerts;
}

