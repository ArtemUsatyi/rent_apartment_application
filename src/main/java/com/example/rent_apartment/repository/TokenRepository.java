package com.example.rent_apartment.repository;

import com.example.rent_apartment.model.entity.TokenClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenClientEntity, Long> {
    public TokenClientEntity findTokenClientEntityByToken(String token);
}
