package com.example.rent_apartment.mapper;

import com.example.rent_apartment.model.ApartmentInfoResponseModel;
import com.example.rent_apartment.model.ApartmentModel;
import com.example.rent_apartment.model.entity.AddressEntity;
import com.example.rent_apartment.model.entity.ApartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RentApartmentMapper {

    public List<ApartmentModel> listApartmentEntityToApartmentModel(List<AddressEntity> addressEntityList);

    @Mapping(target = "city", source = "addressEntity.nameCity")
    @Mapping(target = "price", source = "apartmentEntity.amountApartment")
    @Mapping(target = "street", source = "addressEntity.nameStreet")
    @Mapping(target = "buildNumber", source = "addressEntity.numberHouse")
    @Mapping(target = "roomsCount", source = "apartmentEntity.valueRoomsApartment")
    @Mapping(target = "status", source = "apartmentEntity.availability")
    public ApartmentInfoResponseModel apartmentModel(ApartmentEntity apartmentEntity, AddressEntity addressEntity);
}
