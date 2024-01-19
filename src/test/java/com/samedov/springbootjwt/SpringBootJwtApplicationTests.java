package com.samedov.springbootjwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootJwtApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGenerateToken() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/token/generate", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testValidateToken() {
        String token = restTemplate.getForObject("/api/v1/token/generate", String.class);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/token/validate?token=" + token, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token is valid", response.getBody());
    }

    @Test
    public void testInvalidToken() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/token/validate?token=invalidToken", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Token is invalid", response.getBody());
    }

}
