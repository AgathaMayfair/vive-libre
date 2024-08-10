package com.vivelibre.tokenretriever.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivelibre.tokenretriever.dtos.ResponseObjectDto;
import com.vivelibre.tokenretriever.dtos.TokenDto;
import com.vivelibre.tokenretriever.services.ApiRequestService;
import com.vivelibre.tokenretriever.services.impl.EncryptorServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccessTokenControllerTests {

    @Mock
    private ApiRequestService apiRequestService;
    @Mock
    private EncryptorServiceImpl encryptorService;

    @InjectMocks
    private AccessTokenController accessTokenController;

    @BeforeTestClass
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void whenGetTokenThenReturnOK() throws JsonProcessingException {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken("resultToken");
        when(apiRequestService.generateToken(any())).thenReturn(tokenDto);
        ResponseEntity<ResponseObjectDto> response = accessTokenController.getToken();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void whenGetTokenThenReturnBadRequest() throws JsonProcessingException {
        when(apiRequestService.generateToken(any())).thenThrow(JsonProcessingException.class);
        ResponseEntity<ResponseObjectDto> response = accessTokenController.getToken();
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
