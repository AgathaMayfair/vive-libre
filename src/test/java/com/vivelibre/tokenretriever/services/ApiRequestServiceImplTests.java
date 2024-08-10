package com.vivelibre.tokenretriever.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibre.tokenretriever.config.ApiConfig;
import com.vivelibre.tokenretriever.dtos.TokenDto;
import com.vivelibre.tokenretriever.dtos.TokenRequestDto;
import com.vivelibre.tokenretriever.services.impl.ApiRequestServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApiRequestServiceImplTests {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiRequestServiceImpl apiRequestService;



    @BeforeTestClass
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void whenGenerateTokenThenSuccess() throws JsonProcessingException {
        // Arrange
        TokenRequestDto requestDto = new TokenRequestDto();
        requestDto.setUsername("userMock");
        requestDto.setPassword("passMock");

        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"token\":\"testToken\"}", HttpStatus.OK);

        when(restTemplate.exchange((String) isNull(), any(), any(), eq(String.class)))
                .thenReturn(responseEntity);

        TokenDto actualTokenDto = apiRequestService.generateToken(requestDto);

        assertNotNull(actualTokenDto);
        assertEquals("testToken", actualTokenDto.getToken());
    }

    @Test
    void whenGenerateTokenThenErrorParsingResponse() throws JsonProcessingException {
        TokenRequestDto requestDto = new TokenRequestDto();
        ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"invalidJson", HttpStatus.OK);
        when(restTemplate.exchange((String) isNull(), any(), any(), eq(String.class)))
                .thenReturn(responseEntity);

        assertThrows(JsonProcessingException.class, () -> apiRequestService.generateToken(requestDto));
    }

}
