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
public class ServiceDetails {
    @JsonProperty("adhocAlerts")
    private List<String> adhocAlerts;
    
    @JsonProperty("formation")
    private FormationData formation;
    
    @JsonProperty("previousCallingPoints")
    private List<ArrayOfCallingPoints> previousCallingPoints;
    
    @JsonProperty("subsequentCallingPoints")
    private List<ArrayOfCallingPoints> subsequentCallingPoints;
    
    @JsonProperty("generatedAt")
    private LocalDateTime generatedAt;
    
    @JsonProperty("serviceType")
    private String serviceType; // "train", "bus", "ferry"
    
    @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("crs")
    private String crs;
    
    @JsonProperty("operator")
    private String operator;
    
    @JsonProperty("operatorCode")
    private String operatorCode;
    
    @JsonProperty("rsid")
    private String rsid;
    
    @JsonProperty("isCancelled")
    private Boolean isCancelled;
    
    @JsonProperty("cancelReason")
    private String cancelReason;
    
    @JsonProperty("delayReason")
    private String delayReason;
    
    @JsonProperty("overdueMessage")
    private String overdueMessage;
    
    @JsonProperty("length")
    private Integer length;
    
    @JsonProperty("detachFront")
    private Boolean detachFront;
    
    @JsonProperty("isReverseFormation")
    private Boolean isReverseFormation;
    
    @JsonProperty("platform")
    private String platform;
    
    @JsonProperty("sta")
    private String sta;
    
    @JsonProperty("eta")
    private String eta;
    
    @JsonProperty("ata")
    private String ata;
    
    @JsonProperty("std")
    private String std;
    
    @JsonProperty("etd")
    private String etd;
    
    @JsonProperty("atd")
    private String atd;
    
    @JsonProperty("Xmlns")
    private XmlSerializerNamespaces xmlns;
}

