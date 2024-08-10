package com.vivelibre.tokenretriever.services;

import com.vivelibre.tokenretriever.TokenRetrieverApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class EncryptorServiceTests {

    @Autowired
    private EncryptorService encryptorService;

    @Test
    public void testGetEncryptValue(){
        String username = "auth-vivelibre";
        String password = "password";
        try{
            String userEncrypted = encryptorService.encrypt(username);
            String passwordEncrypted = encryptorService.encrypt(password);
            System.out.println(userEncrypted);
            System.out.println(passwordEncrypted);

            userEncrypted = encryptorService.decrypt(userEncrypted);
            passwordEncrypted = encryptorService.decrypt(passwordEncrypted);

            Assertions.assertEquals(username, userEncrypted);
            Assertions.assertEquals(password, passwordEncrypted);
        }catch(Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }

}
