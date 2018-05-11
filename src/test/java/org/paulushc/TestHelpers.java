package org.paulushc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TestHelpers {

    public static void copyBulbasaur() throws IOException {
        File bulbasaurFile = ResourceUtils.getFile("classpath:bulbasaur.json");
        Paths.get("./data/bulbasaur.json").getParent().toFile().mkdirs();
        Files.copy(bulbasaurFile.toPath(), Paths.get("./data/bulbasaur.json"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String readBulbasaur() throws IOException {
        File bulbasaurFile = ResourceUtils.getFile("classpath:bulbasaur.json");
        return new ObjectMapper().readValue(bulbasaurFile,JsonNode.class).toString();
    }
}
