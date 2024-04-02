package com.example.rent_apartment.controller_method_test;

import com.example.rent_apartment.model.BookingDateRequest;

import java.time.LocalDateTime;

public class PrepareTestObject {

    public static BookingDateRequest prepareToTest(){
        return new BookingDateRequest("2024-02-10T18:00:00", "2024-02-14T12:00:00");
    }
}
