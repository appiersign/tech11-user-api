package com.appiersign.dtos;

import com.appiersign.util.annotations.UniqueEmail;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@RequestScoped
public class UserRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 3, message = "First name is too short")
    @Size(max = 30, message = "First name is too long")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, message = "last name is too short")
    @Size(max = 30, message = "last name is too long")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "The email is not valid")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be a past date")
    private LocalDate birthday;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
