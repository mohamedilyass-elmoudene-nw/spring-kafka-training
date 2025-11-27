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
public class DepartureItemWithCallingPoints {
    @JsonProperty("service")
    private ServiceItemWithCallingPoints service;
    
    @JsonProperty("crs")
    private String crs;
}

