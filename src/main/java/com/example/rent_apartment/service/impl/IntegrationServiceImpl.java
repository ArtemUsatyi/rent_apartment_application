package com.example.rent_apartment.service.impl;

import com.example.rent_apartment.exception.PropertiesException;
import com.example.rent_apartment.model.LocationInfo;
import com.example.rent_apartment.model.entity.IntegrationEntity;
import com.example.rent_apartment.model.integration.GeocoderResponse;
import com.example.rent_apartment.repository.IntegrationRepository;
import com.example.rent_apartment.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.rent_apartment.constans.RentApartmentConst.NON_FIND_PARAM_EXCEPTION;
import static com.example.rent_apartment.service.impl.Base64EncoderDecoder.decod;

@Service
@RequiredArgsConstructor
public class IntegrationServiceImpl implements IntegrationService {

    private RestTemplate restTemplate = new RestTemplate();
    private final IntegrationRepository integrationRepository;

    @Override
    public GeocoderResponse findInfoByLocation(LocationInfo location) {
        IntegrationEntity param = prepareParam("GEO");
        return restTemplate.exchange(prepareUrlForGeocoder(param, location),
                HttpMethod.GET,
                new HttpEntity<>(null),
                GeocoderResponse.class).getBody();
    }

    @Override
    public String selectionProduct(Long id) {
        IntegrationEntity param = prepareParam("PRODUCT");
        return restTemplate.exchange(prepareUrlForProduct(param, id),
                HttpMethod.GET,
                new HttpEntity<>(null),
                String.class).getBody();
    }

    private IntegrationEntity prepareParam(String tag) {
        IntegrationEntity param = integrationRepository.findById(tag).orElseThrow(() -> new PropertiesException(NON_FIND_PARAM_EXCEPTION));
        param.setKey(new String(decod(param.getKey())));
        return param;
    }

    private String prepareUrlForGeocoder(IntegrationEntity param, LocationInfo location) {
        return String.format(param.getUrl(), location.getLat(), location.getLng(), param.getKey());
    }

    private String prepareUrlForProduct(IntegrationEntity param, Long id) {
        return String.format(param.getUrl(), id, param.getKey());
    }
}
