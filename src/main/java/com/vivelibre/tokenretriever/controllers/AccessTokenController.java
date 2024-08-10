package com.vivelibre.tokenretriever.controllers;

import com.vivelibre.tokenretriever.dtos.ResponseObjectDto;
import com.vivelibre.tokenretriever.dtos.TokenDto;
import com.vivelibre.tokenretriever.dtos.TokenRequestDto;
import com.vivelibre.tokenretriever.services.ApiRequestService;
import com.vivelibre.tokenretriever.services.impl.EncryptorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
public class AccessTokenController {

    @Autowired
    private ApiRequestService apiRequestService;

    @Value("${application.username}")
    private String username;

    @Value("${application.password}")
    private String password;

    @Autowired
    private EncryptorServiceImpl encryptorService;

    private static final Logger log = LoggerFactory.getLogger(AccessTokenController.class);

    @GetMapping("/get-token")
    @ResponseBody
    public ResponseEntity<ResponseObjectDto> getToken()  {
        log.info("Retrieving token.");
        TokenRequestDto request = new TokenRequestDto();
        TokenDto tokenDto = null;
        try {
            String usernameDecrypted = encryptorService.decrypt(username);
            log.info("usernameDecrypted: {} ", usernameDecrypted);

            String passwordDecrypted = encryptorService.decrypt(password);
            log.info("passwordDecrypted: {} ", passwordDecrypted);

            request.setUsername(usernameDecrypted);
            request.setPassword(passwordDecrypted);
            log.info("Connecting to viva-libre service");
            tokenDto = apiRequestService.generateToken(request);
        } catch (Exception e) {
            log.error("An error occur while retrieving token info from viva-libre.", e);
            ResponseObjectDto errorResponse = new ResponseObjectDto();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        ResponseObjectDto responseObjectDto = new ResponseObjectDto();
        responseObjectDto.setToken(tokenDto.getToken());
        log.info("Token object retrieved successfully, ResponseObject {}", responseObjectDto.toString());

        return ResponseEntity.ok(responseObjectDto);
    }

}
