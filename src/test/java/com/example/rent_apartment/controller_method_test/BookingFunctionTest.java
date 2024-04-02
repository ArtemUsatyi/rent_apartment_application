package com.example.rent_apartment.controller_method_test;

import com.example.rent_apartment.model.BookingDateRequest;
import com.example.rent_apartment.repository.ApartmentRepository;
import com.example.rent_apartment.repository.BookingRepository;
import com.example.rent_apartment.repository.ClientRepository;
import com.example.rent_apartment.service.AuthService;
import com.example.rent_apartment.service.IntegrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.example.rent_apartment.constans.RentApartmentConst.BOOKING_APARTMENT;
import static com.example.rent_apartment.controller_method_test.TestConstance.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@Testcontainers
@Transactional
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BookingFunctionTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private IntegrationService integrationService;

    @Test
    public void checkBookingValidLogic() throws Exception {
        Mockito.doNothing().when(authService).checkValidToken(TEST_TOKEN_CONST);
        Mockito.when(integrationService.selectionProduct(TEST_ID_BOOKING)).thenReturn(PRODUCT_SEARCH_DONE);

        BookingDateRequest bookingDateRequest = PrepareTestObject.prepareToTest();

        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post(BOOKING_APARTMENT)
                .param("id", String.valueOf(1))
                .header("token", TEST_TOKEN_CONST)
                .content(asJSONstring(bookingDateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        perform
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String asJSONstring(final Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
