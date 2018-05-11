package org.paulushc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.paulushc.endpoints.MainEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private MainEndpoint controller;

    @Test
    public void contexLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void dummyShouldReturnDummyContent(){
        assertThat(controller.dummy())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("Hi I'm a Dummy Call");
    }

}
