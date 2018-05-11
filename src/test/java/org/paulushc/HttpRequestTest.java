package org.paulushc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.paulushc.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void dummyShouldReturnDefaultMessage() throws Exception {
        assertThat(
                this.restTemplate
                    .getForObject("http://localhost:" + port + "/",String.class)
        ).contains("Hi I'm a Dummy Call");
    }

    @Test
    public void dummyHomeShouldReturnDefaultMessageWithUsername(){

        ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:"+ port + "/home",
                        HttpMethod.GET,
                        new HttpEntity<>(buildAuthorizedHeaders()),
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Hi redfish816 welcome");
    }

    @Test
    public void apiShouldReturnBulbasaurWhenAuthenticated() throws IOException {

        TestHelpers.copyBulbasaur();

        ResponseEntity<String> response = this.restTemplate
                .exchange("http://localhost:"+ port + "/api/bulbasaur.json",
                        HttpMethod.GET,
                        new HttpEntity<>(buildAuthorizedHeaders()),
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(TestHelpers.readBulbasaur());
    }

    @Test
    public void openShouldReturnBulbasaurToAnyone() throws IOException {

        TestHelpers.copyBulbasaur();

        assertThat(this.restTemplate.getForObject("http://localhost:"+ port + "/open/bulbasaur.json",String.class))
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(TestHelpers.readBulbasaur());
    }

    private HttpHeaders buildAuthorizedHeaders(){
        String token = TokenAuthenticationService.generateJWT("redfish816");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return headers;
    }
}
