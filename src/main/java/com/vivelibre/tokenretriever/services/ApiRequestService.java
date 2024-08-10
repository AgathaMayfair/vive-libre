package com.vivelibre.tokenretriever.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivelibre.tokenretriever.dtos.TokenDto;
import com.vivelibre.tokenretriever.dtos.TokenRequestDto;

public interface ApiRequestService {

    TokenDto generateToken(TokenRequestDto request) throws JsonProcessingException;

}
