package com.example.rent_apartment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingDateResponse {

    private ApartmentInfoResponseModel apartment;
    private String message;
}
