package com.example.rent_apartment.service;

import com.example.rent_apartment.model.LocationInfo;
import com.example.rent_apartment.model.integration.GeocoderResponse;

public interface IntegrationService {

    /**
     * Метод интеграции с сервисом локации Geocoder
     *
     * @param location - широта, долгота пользователя
     * @return - информация по городу
     */
    public GeocoderResponse findInfoByLocation(LocationInfo location);

    /**
     * Метод интеграции с микросервисом Product
     *
     * @param id - идентификатор номер бронирования
     * @return - информация о валидности подбора продукции
     */
    public String selectionProduct(Long id);

}
