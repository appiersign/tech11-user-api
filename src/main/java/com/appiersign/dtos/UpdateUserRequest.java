package com.appiersign.dtos;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@RequestScoped
public class UpdateUserRequest {

    @Size(min = 3, message = "First name is too short")
    @Size(max = 30, message = "First name is too long")
    private String firstName;

    @Size(min = 3, message = "last name is too short")
    @Size(max = 30, message = "last name is too long")
    private String lastName;

    @Email(message = "The email is not valid")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

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

    public Boolean hasPassword() {
        return password != null && !password.isBlank();
    }
}
