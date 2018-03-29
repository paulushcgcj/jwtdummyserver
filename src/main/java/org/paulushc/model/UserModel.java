package org.paulushc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.paulushc.parsers.LocalDateTimeCustomDeserializer;
import org.paulushc.parsers.LocalDateTimeCustomSerializer;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel {

    PersonGender gender;
    PersonName name;
    PersonLocation location;
    PersonLogin login;
    PersonId id;
    PersonPicture picture;

    String email;
    String phone;
    String cell;
    String nat;

    @JsonSerialize(using = LocalDateTimeCustomSerializer.class)
    @JsonDeserialize(using = LocalDateTimeCustomDeserializer.class)
    LocalDateTime dob;
    @JsonSerialize(using = LocalDateTimeCustomSerializer.class)
    @JsonDeserialize(using = LocalDateTimeCustomDeserializer.class)
    LocalDateTime registered;

    public enum PersonGender {
        male,
        female
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PersonName{
        String title;
        String first;
        String last;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PersonLocation {
        String street;
        String city;
        String state;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PersonLogin{
        String username;
        String password;
        String salt;
        String md5;
        String sha1;
        String sha256;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PersonId{
        String name;
        String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PersonPicture{
        String large;
        String medium;
        String thumbnail;
    }
}
