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
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public List<User> getUsers(int pageNumber, int pageSize) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.firstName", User.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public Long getUsersCount() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class);
        return query.getSingleResult();
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
                .setParameter("uuid", uuid).getSingleResult();
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
