package com.producerkafka.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemWithCallingPoints extends ServiceItem {
    @JsonProperty("previousCallingPoints")
    private List<ArrayOfCallingPoints> previousCallingPoints;
    
    @JsonProperty("subsequentCallingPoints")
    private List<ArrayOfCallingPoints> subsequentCallingPoints;
    
    @Builder(builderMethodName = "serviceItemWithCallingPointsBuilder")
    public ServiceItemWithCallingPoints(
            FormationData formation,
            List<ServiceLocation> origin,
            List<ServiceLocation> destination,
            List<ServiceLocation> currentOrigins,
            List<ServiceLocation> currentDestinations,
            String rsid,
            String sta,
            String eta,
            String std,
            String etd,
            String platform,
            String operator,
            String operatorCode,
            Boolean isCircularRoute,
            Boolean isCancelled,
            Boolean filterLocationCancelled,
            String serviceType,
            Integer length,
            Boolean detachFront,
            Boolean isReverseFormation,
            String cancelReason,
            String delayReason,
            String serviceID,
            List<String> adhocAlerts,
            List<ArrayOfCallingPoints> previousCallingPoints,
            List<ArrayOfCallingPoints> subsequentCallingPoints) {
        super(formation, origin, destination, currentOrigins, currentDestinations,
                rsid, sta, eta, std, etd, platform, operator, operatorCode,
                isCircularRoute, isCancelled, filterLocationCancelled, serviceType,
                length, detachFront, isReverseFormation, cancelReason, delayReason,
                serviceID, adhocAlerts);
        this.previousCallingPoints = previousCallingPoints;
        this.subsequentCallingPoints = subsequentCallingPoints;
    }
}

