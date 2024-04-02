package com.example.rent_apartment.model;

import lombok.Data;

@Data
public class ApartmentModel {
    private String valueRooms;
    private String rating;
    private String amount;
    private Boolean smoking;
    private Integer floor;

    private String nameStreet;
    private String nameCity;
    private String numberHouse;
}
