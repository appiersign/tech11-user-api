package com.appiersign.dtos;

import com.appiersign.entities.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@ApplicationScoped
public class UserResource {

    public Map<String, String> create(User user) {
        return Map.of(
                "uuid", user.getUuid().toString(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "birthday", user.getBirthday().toString(),
                "createdAt", user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                "updatedAt", user.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
