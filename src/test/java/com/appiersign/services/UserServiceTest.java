package com.appiersign.services;

import com.appiersign.dtos.UserRequest;
import com.appiersign.entities.User;
import com.appiersign.util.PasswordHasher;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PasswordHasher passwordHasher;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void createUser_ShouldSuccessfullyCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("John");
        userRequest.setLastName("Doe");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setPassword("securePassword123");
        userRequest.setBirthday(LocalDate.of(1990, 1, 1));

        String hashedPassword = "hashedPassword123";
        when(passwordHasher.hashPassword(userRequest.getPassword())).thenReturn(hashedPassword);

        User expectedUser = new User(
                UUID.randomUUID(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                hashedPassword,
                userRequest.getBirthday()
        );

        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setUuid(expectedUser.getUuid());
            return null;
        }).when(entityManager).persist(any(User.class));

        User createdUser = userService.createUser(userRequest);

        assertEquals(expectedUser.getFirstName(), createdUser.getFirstName());
        assertEquals(expectedUser.getLastName(), createdUser.getLastName());
        assertEquals(expectedUser.getEmail(), createdUser.getEmail());
        assertEquals(expectedUser.getPassword(), createdUser.getPassword());
        assertEquals(expectedUser.getBirthday(), createdUser.getBirthday());

        verify(entityManager).persist(any(User.class));
        verify(passwordHasher).hashPassword(userRequest.getPassword());
    }
}