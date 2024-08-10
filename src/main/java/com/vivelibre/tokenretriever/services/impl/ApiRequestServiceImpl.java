package com.vivelibre.tokenretriever.services.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivelibre.tokenretriever.dtos.TokenDto;
import com.vivelibre.tokenretriever.dtos.TokenRequestDto;
import com.vivelibre.tokenretriever.services.ApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiRequestServiceImpl implements ApiRequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.url}")
    private String url;

    private static final Logger log = LoggerFactory.getLogger(ApiRequestServiceImpl.class);

    public TokenDto generateToken(TokenRequestDto request) throws JsonProcessingException {
        log.info("Generate token service starts");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TokenRequestDto> entity = new HttpEntity<>(request, headers);
        ObjectMapper mapper = new ObjectMapper();

        log.info("Requesting to : {} ", url);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        return mapper.readValue(response.getBody(), TokenDto.class);
    }
}
