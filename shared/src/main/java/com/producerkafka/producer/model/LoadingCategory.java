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
public class LoadingCategory {
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("colour")
    private String colour;
    
    @JsonProperty("image")
    private String image;
    
    @JsonProperty("Value")
    private String value;
}

