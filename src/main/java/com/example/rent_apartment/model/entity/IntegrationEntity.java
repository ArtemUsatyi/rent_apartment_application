package com.example.rent_apartment.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "integration_info")
@Data
public class IntegrationEntity {

    @Id
    @Column(name = "tag")
    private String tag;

    @Column(name = "url")
    private String url;

    @Column(name = "permission_key")
    private String key;
}
