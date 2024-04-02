package com.example.rent_apartment.service.impl;

import com.example.rent_apartment.exception.AuthException;
import com.example.rent_apartment.exception.TokenException;
import com.example.rent_apartment.model.AuthModel;
import com.example.rent_apartment.model.RegisterUserInfoModel;
import com.example.rent_apartment.model.entity.ClientApplicationEntity;
import com.example.rent_apartment.model.entity.TokenClientEntity;
import com.example.rent_apartment.repository.ClientRepository;
import com.example.rent_apartment.repository.TokenRepository;
import com.example.rent_apartment.service.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.rent_apartment.constans.RentApartmentConst.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;
    private final EntityManager entityManager;

    @Override
    public String getRegistration(RegisterUserInfoModel registerModel) {

        if (checkValidFields(registerModel)) {
            return NON_VALID_REGISTRATION_MESSAGE;
        }

        ClientApplicationEntity clientWithNikeName = clientRepository.findClientByNikeName(registerModel.getNickName());
        if (checkNonUniqueNikeName(clientWithNikeName)) {
            return NON_UNIQUE_NIKE_NAME_CLIENT;
        }

        List<ClientApplicationEntity> clientList = findClientBtNikeNameWithCriteria(registerModel.getNickName());
        if (!clientList.isEmpty()) {
            return NON_UNIQUE_NIKE_NAME_CLIENT;
        }

        ClientApplicationEntity clientWithEmail = clientRepository.findClientApplicationByEmail(registerModel.getEmail());
        if (checkNonUniqueEmail(clientWithEmail)) {
            return NON_UNIQUE_EMAIL_CLIENT;
        }

        clientRepository.save(new ClientApplicationEntity(registerModel.getNickName(), registerModel.getEmail(), registerModel.getPassword()));

        return VALID_REGISTRATION_MESSAGE;
    }

    private List<ClientApplicationEntity> findClientBtNikeNameWithCriteria(String value) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientApplicationEntity> query = criteriaBuilder.createQuery(ClientApplicationEntity.class);
        Root<ClientApplicationEntity> root = query.from(ClientApplicationEntity.class);

        query.select(root)
                .where(criteriaBuilder.equal(root.get("nikeName"), value));

        List<ClientApplicationEntity> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    @Override
    public String getAuth(AuthModel model) {
        ClientApplicationEntity client = clientRepository.findClientApplicationByEmail(model.getLogin());
        if (isNull(client)) {
            throw new AuthException(USER_NOT_FIND_EXCEPTION);
        }
        if (!client.getPassword().equals(model.getPassword())) {
            throw new AuthException(NON_VALID_PASSWORD_EXCEPTION);
        }
        String token = generationToken();
        TokenClientEntity tokenEntity = new TokenClientEntity(token);
        tokenRepository.save(tokenEntity);
        client.setTokenEntity(tokenEntity);
        clientRepository.save(client);
        return token;
    }

    public void checkValidToken(String token) {
        TokenClientEntity tokenClientEntityByToken = tokenRepository.findTokenClientEntityByToken(token);
        if (isNull(tokenClientEntityByToken)) {
            throw new TokenException(NON_ACTIVE_SESSION_EXCEPTION);
        }
    }

    private String generationToken() {
        String uniqueToken = UUID.randomUUID().toString();
        return uniqueToken + "|" + LocalDateTime.now().plusDays(1);
    }

    private Boolean checkValidFields(RegisterUserInfoModel registerModel) {
        return isNull(registerModel.getEmail()) || isNull(registerModel.getNickName()) || isNull(registerModel.getPassword());
    }

    private Boolean checkNonUniqueNikeName(ClientApplicationEntity clientWithNikeName) {
        return !isNull(clientWithNikeName);
    }

    private Boolean checkNonUniqueEmail(ClientApplicationEntity clientWithEmail) {
        return !isNull(clientWithEmail);
    }
}
