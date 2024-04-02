package com.example.rent_apartment.service.impl;

import com.example.rent_apartment.exception.ApartmentException;
import com.example.rent_apartment.exception.BookingException;
import com.example.rent_apartment.exception.JsonException;
import com.example.rent_apartment.mapper.RentApartmentMapper;
import com.example.rent_apartment.model.*;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import com.example.rent_apartment.model.entity.BookingEntity;
import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import com.example.rent_apartment.model.integration.ComponentsObject;
import com.example.rent_apartment.model.integration.GeocoderResponse;
import com.example.rent_apartment.repository.AddressRepository;
import com.example.rent_apartment.repository.ApartmentRepository;
import com.example.rent_apartment.repository.BookingRepository;
import com.example.rent_apartment.repository.ClientRepository;
import com.example.rent_apartment.service.IntegrationService;
import com.example.rent_apartment.service.RentApartmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.beanvalidation.IntegrationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.rent_apartment.constans.RentApartmentConst.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class RentApartmentServiceImpl implements RentApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final AddressRepository addressRepository;
    private final RentApartmentMapper rentApartmentMapper;
    private final IntegrationService integrationService;
    private final ClientRepository clientRepository;
    private final BookingRepository bookingRepository;

    @Override
    public String createApartment(ApartmentModel apartment) {

        if (checkFieldsValid(apartment)) {
            return NON_VALID_CREATE_APARTMENT;
        }
        AddressEntity addressEntity = new AddressEntity(apartment.getNameStreet(), apartment.getNameCity(), apartment.getNumberHouse());
        addressRepository.save(addressEntity);

        ApartmentEntity apartmentEntity = new ApartmentEntity(apartment.getValueRooms(), apartment.getRating(), apartment.getAmount(), apartment.getSmoking(), apartment.getFloor(), addressEntity);
        apartmentRepository.save(apartmentEntity);

        return SAVE_NEW_APARTMENT;
    }

    @Override
    public String addPhoto(Long id, MultipartFile file) {
        ApartmentEntity apartment = apartmentRepository.findById(id).orElseThrow(() -> new ApartmentException(NON_APARTMENT_FIND_EXCEPTION));

        String originalFilename = file.getOriginalFilename();
        long sizeFile = file.getSize();
        try {
            byte[] bytesFile = file.getBytes();
            String encod = Base64EncoderDecoder.encod(bytesFile);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки фото, попробуйте еще раз");
        }

        return "Фото сохранено";
    }

    @Override
    public List<ApartmentModel> findApartmentByCity(String nameCity) {
        List<AddressEntity> addressList = addressRepository.findAddressEntitiesByNameCity(nameCity);
        return rentApartmentMapper.listApartmentEntityToApartmentModel(addressList);
    }

    @Override
    public List<ApartmentModel> findApartmentByLoc(LocationInfo location) {
        GeocoderResponse geocoderResponse = integrationService.findInfoByLocation(location);
//        parseInfo(fullInfoByLocation);
        return findByCityInfo(geocoderResponse);
    }

    @Override
    public BookingDateResponse showApartmentWithOutBookingId(Long id) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(id).get();
        ApartmentInfoResponseModel apartmentModel = rentApartmentMapper.apartmentModel(apartmentEntity, apartmentEntity.getAddressEntity());
        return new BookingDateResponse(apartmentModel, APARTMENT_FREE_BOOKING);
    }

    @Override
    public BookingDateResponse bookingApartment(Long id, BookingDateRequest dateInfo, String token) {
        ApartmentEntity apartmentEntity = apartmentRepository.findById(id).get();

        createApartmentStatus(apartmentEntity);

        ClientApplicationEntity clientByToken = clientRepository.findClientByToken(token);

        BookingEntity bookingEntity = prepareBookingEntity(apartmentEntity, dateInfo, clientByToken);

        ApartmentInfoResponseModel apartmentModel = rentApartmentMapper.apartmentModel(bookingEntity.getApartment(), bookingEntity.getApartment().getAddressEntity());

        String messageResult = checkIntegrationConnect(bookingEntity.getId());//заменить передаваемую модель на Entity

        return new BookingDateResponse(apartmentModel, messageResult);
    }

    private String checkIntegrationConnect(Long id) {
        try {
            return integrationService.selectionProduct(id);
        } catch (Exception e) {
            return CHECK_INTEGRATION;
        }
    }

    private BookingEntity prepareBookingEntity(ApartmentEntity apartment, BookingDateRequest dateInfo, ClientApplicationEntity client) {
        BookingEntity bookingEntity = new BookingEntity(LocalDateTime.parse(dateInfo.getStartDate()), LocalDateTime.parse(dateInfo.getEndDate()), apartment, client, null);
        bookingRepository.save(bookingEntity);
        return bookingEntity;
    }

    private void createApartmentStatus(ApartmentEntity apartmentEntity) {
        if (!apartmentEntity.getAvailability()) {
            new BookingException(BOOKING_NOT_AVAILABILITY);
        }
        apartmentEntity.setAvailability(false);
        apartmentRepository.save(apartmentEntity);
    }

    private List<ApartmentModel> findByCityInfo(GeocoderResponse geocoderResponse) {
        String city = findNameCity(geocoderResponse);
        List<AddressEntity> addressList = addressRepository.findAddressEntitiesByNameCity(city)
                .stream()
                .filter(o -> o.getApartmentEntity().getAvailability().equals(true))
                .collect(Collectors.toList());

        return rentApartmentMapper.listApartmentEntityToApartmentModel(addressList);
    }

    private String findNameCity(GeocoderResponse geocoderResponse) {
        ComponentsObject components = geocoderResponse.getResultsList().get(0).getComponents();
        if (!isNull(components.getTown())) return components.getTown();
        if (!isNull(components.getCity())) return components.getCity();
        throw new IntegrationException(NON_FIND_CITY);
    }

    private String parseInfo(String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(value);
            String city = jsonNode.get("results").get(0).get("components").get("town").asText();
            return city;
        } catch (JsonProcessingException e) {
            throw new JsonException("Возникла ошибка в получении интеграции");
        }
    }

    private Boolean checkFieldsValid(ApartmentModel apartment) {
        return isNull(apartment.getValueRooms()) || isNull(apartment.getRating()) || isNull(apartment.getAmount()) || isNull(apartment.getSmoking()) || isNull(apartment.getFloor());
    }
}
