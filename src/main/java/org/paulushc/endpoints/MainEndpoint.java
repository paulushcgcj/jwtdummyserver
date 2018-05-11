package org.paulushc.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MainEndpoint {

    @RequestMapping("/")
    public String dummy() { return "Hi I'm a Dummy Call"; }

    @RequestMapping("/home")
    public String dummyHome(Principal principal) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken)principal;
        return String.format("Hi %s welcome",usernamePasswordAuthenticationToken.getCredentials());
    }

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
            log.error("Error while trying to get some info");
        }


        //First I check if the file exists
        if(Paths.get(requiredFilePath).toFile().exists()){
            //If so, read it and return
            try {
                return ResponseEntity.ok( new ObjectMapper().readValue(Paths.get(requiredFilePath).toFile(),JsonNode.class));
            } catch (IOException e) {
                log.error("Error while trying to read file {}",requiredFilePath,e);
                return ResponseEntity.badRequest().body(e);
            }
        }

        //In case it didn't found what it was looking for
        return ResponseEntity.notFound().build();
    }
}
