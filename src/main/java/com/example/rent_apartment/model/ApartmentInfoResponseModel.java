package com.example.rent_apartment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentInfoResponseModel {
    private String city;
    private String street;
    private String buildNumber;
    private Integer roomsCount;
    private Integer price;
    private Double globalRating;
    private Boolean status;
}
