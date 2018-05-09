package org.paulushc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rollbar.notifier.config.ConfigBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.stream.Collectors;
import static com.rollbar.notifier.config.ConfigBuilder.withAccessToken;
import com.rollbar.notifier.Rollbar;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@Slf4j
public class MainApp {

    public static void main(String[] args) { SpringApplication.run(MainApp.class, args); }


    private Rollbar rollbar;

    @PostConstruct
    public void setUp(){
        rollbar = Rollbar.init(
                ConfigBuilder
                        .withAccessToken("94b25d9b74c2402ea4716f937b374235")
                        .environment("development")
                        .codeVersion("1.0.0")
                        .build()
        );

        rollbar.log("Project Started");
    }


    @RequestMapping("/")
    public String dummy() { return "Hi I'm a Dummy Call"; }

    @RequestMapping("/home")
    public String dummyHome(Principal principal) { return String.format("Hi %s welcome",principal.getName()); }

    @RequestMapping(value = "/api/**",produces = "application/json")
    public ResponseEntity bootData(HttpServletRequest httpServletRequest){
        return getResponseEntityFromFile(httpServletRequest, "/api/");
    }

    @RequestMapping(value = "/open/**",produces = "application/json")
    public ResponseEntity bootOpenData(HttpServletRequest httpServletRequest){
        return getResponseEntityFromFile(httpServletRequest, "/open/");
    }

    private ResponseEntity getResponseEntityFromFile(HttpServletRequest httpServletRequest, String targetURL) {

        //Get the path to the requested file, I replace the api path to data folder, but I keep the file extension
        //As you may need to load external stuff
        String requiredFilePath = httpServletRequest.getRequestURI().replace(targetURL,"./data/");

        log.info("Requesting data from URL {} and trying to fetch data from {}",httpServletRequest.getRequestURI(),requiredFilePath);

        try{ log.info("Requested by {} with {}",httpServletRequest.getRemoteAddr(),httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()))); }
        catch (Exception e){
            rollbar.error("Error while trying to get some info");
            log.error("Error while trying to get some info");
        }


        //First I check if the file exists
        if(Paths.get(requiredFilePath).toFile().exists()){
            //If so, read it and return
            try {
                return ResponseEntity.ok( new ObjectMapper().readValue(Paths.get(requiredFilePath).toFile(),JsonNode.class));
            } catch (IOException e) {
                rollbar.error(e,"Error while trying to read file "+requiredFilePath);
                log.error("Error while trying to read file {}",requiredFilePath,e);
                return ResponseEntity.badRequest().body(e);
            }
        }

        //In case it didn't found what it was looking for
        return ResponseEntity.notFound().build();
    }
}
