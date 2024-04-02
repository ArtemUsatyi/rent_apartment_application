package com.example.rent_apartment.service;

import com.example.rent_apartment.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RentApartmentService {
    public String createApartment(ApartmentModel apartment);

    public String addPhoto(Long id, MultipartFile file);

    public List<ApartmentModel> findApartmentByCity(String nameCity);

    public List<ApartmentModel> findApartmentByLoc(LocationInfo location);

    public BookingDateResponse showApartmentWithOutBookingId(Long id);

    public BookingDateResponse bookingApartment(Long id, BookingDateRequest dateInfo, String token);
}
