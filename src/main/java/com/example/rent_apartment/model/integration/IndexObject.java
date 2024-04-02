package com.example.rent_apartment.model.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true, value = {"error"})
public class IndexObject {

    @JsonProperty(value = "components")
    private ComponentsObject components;
}
