package com.example.rent_apartment.model.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class ComponentsObject {

    @JsonProperty(value = "city")
    private String city;

    @JsonProperty(value = "town")
    private String town;
}
