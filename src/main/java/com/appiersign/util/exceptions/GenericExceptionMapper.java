package com.appiersign.util.exceptions;

import com.appiersign.dtos.ResponseDTO;
import com.appiersign.util.JsonResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    private JsonResponse jsonResponse;

    @Override
    public Response toResponse(Throwable throwable) {

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(jsonResponse.internalServerError(new ResponseDTO(false, "Something went wrong, please try again later.", null)))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
