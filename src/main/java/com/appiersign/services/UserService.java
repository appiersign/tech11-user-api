package com.appiersign.services;

import com.appiersign.dtos.UpdateUserRequest;
import com.appiersign.dtos.UserRequest;
import com.appiersign.dtos.UserResource;
import com.appiersign.entities.User;
import com.appiersign.util.NullAwareBeanUtil;
import com.appiersign.util.PasswordHasher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserResource userResource;

    @Inject
    PasswordHasher passwordHasher;

    @Inject
    NullAwareBeanUtil nullAwareBeanUtil;

    @PersistenceContext(unitName = "myInMemoryPU")
    private EntityManager entityManager;

    public List<Map<String, String>> getUsers() {
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return users.stream().map(user -> userResource.create(user)).collect(Collectors.toList());
    }

    @Transactional
    public User createUser(UserRequest userRequest) {
        User user = new User(
                UUID.randomUUID(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                passwordHasher.hashPassword(userRequest.getPassword()),
                LocalDate.parse(userRequest.getBirthday().toString())
        );
        entityManager.persist(user);
        return user;
    }

    public User getUser(UUID uuid) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.uuid = :uuid", User.class)
                .setParameter("uuid", uuid)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public Boolean updateUser(UUID uuid, UpdateUserRequest userRequest) {
        User user = getUser(uuid);
        try {
            nullAwareBeanUtil.copyProperties(user, userRequest);
            if (userRequest.hasPassword()) {
                user.setPassword(passwordHasher.hashPassword(userRequest.getPassword()));
            }
            entityManager.merge(user);

            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error updating user", e);
        }
    }

    public Boolean deleteUser(UUID uuid) {
        User user = getUser(uuid);
        if (user != null) {
            entityManager.remove(user);
            return true;
        }
        return false;
    }

    public Boolean emailExists(String email) {
        return !entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList().isEmpty();
    }
}
