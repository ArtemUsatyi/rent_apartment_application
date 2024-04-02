package com.example.rent_apartment.constans;


public class RentApartmentConst {

    /**
     * PathConstant
     */
    public final static String BASE_URL = "/api";
    public final static String FIND_BY_CITY = BASE_URL + "/find_by_city";
    public final static String SEARCH_BY_ID = BASE_URL + "/search_by_id/{id}";
    public final static String REGISTRATION = BASE_URL + "/registration";
    public final static String AUTH = BASE_URL + "/auth";
    public final static String CREATE_PHOTO = BASE_URL + "/create_photo";
    public final static String FIND_BY_LOCATION = BASE_URL + "/find_apartment";
    public final static String BOOKING_APARTMENT = BASE_URL + "/booking_apartment";

    /**
     * MessageConstant
     */
    public final static String NON_VALID_REGISTRATION_MESSAGE = "Все поля должны быть заполнены";
    public final static String VALID_REGISTRATION_MESSAGE = "Пользователь успешно добавлен";
    public final static String NON_UNIQUE_NIKE_NAME_CLIENT = "Клиент с таким никнеймом уже существует";
    public final static String NON_UNIQUE_EMAIL_CLIENT = "Клиент с таким email уже существует";
    public final static String CHECK_INTEGRATION = "Информация о бронировании будет отправлена на почту в течении 24 часов";

    /**
     * PathConstantApartment
     */
    public final static String CREATE_NEW_APARTMENT = BASE_URL + "/new_create";

    /**
     * MessageApartment
     */
    public final static String NON_VALID_CREATE_APARTMENT = "Апартаменты не добавлены. Заполните все поля";
    public final static String SAVE_NEW_APARTMENT = "Добавлено новые апартаменты";
    public final static String APARTMENT_FREE_BOOKING = "Апартаменты не свободны";
    public final static String BOOKING_NOT_AVAILABILITY = "Апартаменты не доступны для бронирования";
    public final static String APARTMENT_BOOKED = "Апартаменты забронированы";

    /**
     * ExceptionMessageConstant
     */
    public final static String NON_ACTIVE_SESSION_EXCEPTION = "Сессия клиента не активна, авторизируйтесь";
    public final static String USER_NOT_FIND_EXCEPTION = "Пользователь в системе не существует. Зарегистрируйтесь";
    public final static String NON_VALID_PASSWORD_EXCEPTION = "Неверный пароль";
    public final static String NON_APARTMENT_FIND_EXCEPTION = "Апартаменты не найдены";
    public final static String NON_FIND_PARAM_EXCEPTION = "Параметры для интеграции не найдены";
    public final static String NON_FIND_CITY = "Информация по городу отсутствует";
}
