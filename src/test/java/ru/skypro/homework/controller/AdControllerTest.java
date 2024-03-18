package ru.skypro.homework.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.skypro.homework.dto.ad.AdDto;
import ru.skypro.homework.dto.ad.AdsDto;
import ru.skypro.homework.dto.ad.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ad.ExtendedAdDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static ru.skypro.homework.constants.AdConstants.*;
import static ru.skypro.homework.constants.UserConstants.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private AdController adController;

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void beforeEach() {
        HttpEntity<MultiValueMap<String, Object>> requestEntity1 = getRequestEntity(AD_PATH_TO_IMAGE_1, AD_TITLE_1, AD_PRICE_1, AD_DESCRIPTION_1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity2 = getRequestEntity(AD_PATH_TO_IMAGE_2, AD_TITLE_2, AD_PRICE_2, AD_DESCRIPTION_2);
        restTemplate.postForEntity("http://localhost:" + port + "/register", REGISTER_DTO_1, ResponseEntity.class);
        restTemplate.postForEntity("http://localhost:" + port + "/register", REGISTER_DTO_2, ResponseEntity.class);
        restTemplate.withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .postForEntity("http://localhost:" + port + "/ads", requestEntity1, AdDto.class).getBody();
        restTemplate.withBasicAuth(USER_EMAIL_2, USER_PASSWORD_2)
                .postForEntity("http://localhost:" + port + "/ads", requestEntity2, AdDto.class).getBody();
    }

    @AfterEach
    @Transactional
    public void afterEach() {
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.execute("TRUNCATE TABLE ads RESTART IDENTITY");
        jdbcTemplate.execute("TRUNCATE TABLE users RESTART IDENTITY");
        jdbcTemplate.execute("TRUNCATE TABLE comments RESTART IDENTITY");
        jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    void contextLoad() {
        assertNotNull(adController);
    }

    @Test
    void testCreateAd() {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = getRequestEntity(AD_PATH_TO_IMAGE_3, AD_TITLE_3, AD_PRICE_3, AD_DESCRIPTION_3);
        ResponseEntity<?> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .postForEntity("http://localhost:" + port + "/ads", requestEntity, AdDto.class);

        assertEquals(AD_DTO_3, responseEntity.getBody());
    }

    @Test
    void testGetAllAds() {
        ResponseEntity<?> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads", AdsDto.class);

        assertEquals(ADS_DTO_ALL, responseEntity.getBody());
    }

    @Test
    void testGetInfoAboutAd() {
        ResponseEntity<?> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads/" + AD_ID_1, ExtendedAdDto.class);

        assertEquals(EXTENDED_AD_DTO_1, responseEntity.getBody());

        responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads/" + AD_ID_INCORRECT, String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Ad with id = " + AD_ID_INCORRECT + " not found!", responseEntity.getBody());
    }

    @Test
    void testGetMyAds() {
        ResponseEntity<?> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads/me", AdsDto.class);

        assertEquals(ADS_DTO_1, responseEntity.getBody());
    }

    @Test
    void testGetImage() throws IOException {
        byte[] expected = Files.readAllBytes(Path.of(AD_PATH_TO_IMAGE_1));
        ResponseEntity<byte[]> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads/" + AD_ID_1 + "/image", byte[].class);

        assertArrayEquals(expected, responseEntity.getBody());

        HttpStatus notFoundHttpStatus = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .getForEntity("http://localhost:" + port + "/ads/" + AD_ID_INCORRECT + "/image", byte[].class)
                .getStatusCode();

        assertEquals(HttpStatus.NOT_FOUND, notFoundHttpStatus);
    }

    @Test
    void testUpdateAd() {
        ResponseEntity<?> responseEntity = restTemplate
                .withBasicAuth(USER_EMAIL_1, USER_PASSWORD_1)
                .exchange("http://localhost:" + port + "/ads/" + AD_ID_1,
                        HttpMethod.PATCH,
                        new HttpEntity<>(CREATE_OR_UPDATE_AD_DTO_EDIT),
                        AdDto.class);

        assertEquals(AD_DTO_EDIT, responseEntity.getBody());
    }

    private HttpEntity<MultiValueMap<String, Object>> getRequestEntity(String image, String title, Integer price, String description) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(Path.of(image)));
        body.add("properties", new CreateOrUpdateAdDto(title, price, description));
        return new HttpEntity<>(body, headers);
    }
}