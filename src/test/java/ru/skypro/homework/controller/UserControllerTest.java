package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import ru.skypro.homework.dto.user.UserDto;

import javax.annotation.PostConstruct;

import static ru.skypro.homework.constants.UserConstants.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AdController adController;

    @Autowired
    TestRestTemplate restTemplate;

    @PostConstruct
    public void postConstruct() {
        restTemplate.postForEntity("http://localhost:" + port + "/register", REGISTER_DTO_1, ResponseEntity.class);
    }

    @Test
    @WithUserDetails("email@email.com")
    void testGetUserInfo() {
        UserDto userDto = restTemplate.withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1).
                getForObject("http://localhost:" + port + "/users/me", UserDto.class);
        System.out.println(userDto);
    }
}