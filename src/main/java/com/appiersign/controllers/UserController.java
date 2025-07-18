package com.appiersign.controllers;

import com.appiersign.dtos.*;
import com.appiersign.entities.User;
import com.appiersign.services.UserService;
import com.appiersign.util.JsonResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserController {

    @Inject
    private JsonResponse jsonResponse;

    @Inject
    private UserService userService;

    @Inject
    private UserResource userResource;

    @Inject
    private Logger log;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedResource<User> getUsers(
            @QueryParam("pageNumber") @DefaultValue("1") int pageNumber,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        log.info("Get users request received.");
        List<User> users = this.userService.getUsers(pageNumber, pageSize);
        Long total = this.userService.getUsersCount();
        return new PaginatedResource<>(users, total, pageSize, pageNumber);
    }

    @GET
    @Path("/{userUuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userUuid") UUID uuid) {
        log.info("Get user request received.");
        User user = this.userService.getUser(uuid);
        return (user == null) ?
                jsonResponse.notFound(new ResponseDTO(false, "User not found.", null)) :
                jsonResponse.ok(new ResponseDTO(true, "User retrieved.", user));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid UserRequest userRequest) {
        log.info("Create user request received.");
        User user = this.userService.createUser(userRequest);
        return jsonResponse.created(
                new ResponseDTO(true, "User created.", user)
        );
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("uuid") UUID uuid, UpdateUserRequest updateUserRequest) {
        log.info("Update user request received.");
        User user = this.userService.getUser(uuid);

        if (user == null) {
            return jsonResponse.notFound(new ResponseDTO(false, "User not found.", null));
        }

        if (this.userService.updateUser(uuid, updateUserRequest)) {
            return jsonResponse.noContent();
        }

        return jsonResponse.badRequest(new ResponseDTO(false, "User could not be updated.", null));
    }
}
