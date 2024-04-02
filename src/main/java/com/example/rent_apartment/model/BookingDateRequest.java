package com.example.rent_apartment.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDateRequest {

    private String startDate;
    private String endDate;
}
