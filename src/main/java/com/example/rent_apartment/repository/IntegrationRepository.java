package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.IntegrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegrationRepository extends JpaRepository<IntegrationEntity, String> {
}
