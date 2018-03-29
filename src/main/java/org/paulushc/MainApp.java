package org.paulushc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class MainApp {

    public static void main(String[] args) { SpringApplication.run(MainApp.class, args); }

    @RequestMapping("/")
    public String dummy() { return "Hi I'm a Dummy Call"; }

    @RequestMapping("/home")
    public String dummyHome(Principal principal) { return String.format("Hi %s welcome",principal.getName()); }
}
