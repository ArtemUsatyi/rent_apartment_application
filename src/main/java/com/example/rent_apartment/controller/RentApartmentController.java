package com.example.rent_apartment.controller;

import com.example.rent_apartment.model.ApartmentModel;
import com.example.rent_apartment.model.BookingDateRequest;
import com.example.rent_apartment.model.BookingDateResponse;
import com.example.rent_apartment.model.LocationInfo;
import com.example.rent_apartment.service.AuthService;
import com.example.rent_apartment.service.RentApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.rent_apartment.constans.RentApartmentConst.*;
import static java.util.Objects.isNull;

@RestController
@RequiredArgsConstructor
public class RentApartmentController {

    private final RentApartmentService registrationApartment;
    private final AuthService authService;

    @GetMapping(FIND_BY_CITY)
    public List<ApartmentModel> findApartmentsByCity(@RequestHeader String token,
                                                     @RequestParam String nameCity) {
        authService.checkValidToken(token);
        return registrationApartment.findApartmentByCity(nameCity);
    }

    @PostMapping(FIND_BY_LOCATION)
    public List<ApartmentModel> findApartmentByLocation(@RequestBody LocationInfo location) {
        return registrationApartment.findApartmentByLoc(location);
    }

    @GetMapping(SEARCH_BY_ID)
    public String searchApartmentById(@PathVariable Long id) {

        return "квартира найдена по " + id;
    }

    @PostMapping(CREATE_NEW_APARTMENT)
    public String registration(@RequestBody ApartmentModel apartment) {

        return registrationApartment.createApartment(apartment);
    }

    @PostMapping(CREATE_PHOTO)
    public String registrationPhoto(@RequestParam Long apartmentId,
                                    @RequestParam MultipartFile file) {
        return registrationApartment.addPhoto(apartmentId, file);
    }

    @PostMapping(BOOKING_APARTMENT)
    public BookingDateResponse bookingApartment(@RequestParam Long id,
                                                @RequestHeader(required = false) String token,
                                                @RequestBody(required = false) BookingDateRequest bookingDate) {

        if (isNull(bookingDate)) {
            return registrationApartment.showApartmentWithOutBookingId(id);
        }
        authService.checkValidToken(token);
        return registrationApartment.bookingApartment(id, bookingDate, token);
    }

}
