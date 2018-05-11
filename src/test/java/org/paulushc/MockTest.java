package org.paulushc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.paulushc.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc
            .perform(get("/"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content()
                        .string(containsString("Hi I'm a Dummy Call"))
            );
    }

    @Test
    public void authorizedUserCanRequestHome() throws Exception {
        String token = TokenAuthenticationService.generateJWT("redfish816");
        this.mockMvc.perform(get("/home")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content()
                            .string((containsString("Hi redfish816 welcome")))
                );
    }

    @Test
    public void authorizedUserCanRequestAPI() throws Exception {

        TestHelpers.copyBulbasaur();

        String token = TokenAuthenticationService.generateJWT("redfish816");
        this.mockMvc.perform(get("/api/bulbasaur.json")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().json(TestHelpers.readBulbasaur())
                );
    }

    @Test
    public void anyUserCanRequestOpen() throws Exception {

        TestHelpers.copyBulbasaur();

        this.mockMvc.perform(get("/open/bulbasaur.json"))
                .andExpect(status().isOk())
                .andExpect(content().json(TestHelpers.readBulbasaur())
                );
    }

}
