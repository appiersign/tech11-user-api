package com.appiersign.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class JsonResponse {

    public Response ok(Object object) {
        return Response.status(Response.Status.OK).entity(object).build();
    }

    public Response badRequest(Object object) {
        return Response.status(Response.Status.BAD_REQUEST).entity(object).build();
    }

    public Response notFound(Object object) {
        return Response.status(Response.Status.NOT_FOUND).entity(object).build();
    }

    public Response internalServerError(Object object) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(object).build();
    }

    public Response created(Object object) {
        return Response.status(Response.Status.CREATED).entity(object).build();
    }

    public Response noContent() {
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

