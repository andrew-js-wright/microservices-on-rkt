package com.example.authorisation.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/authorisations")
@Produces({MediaType.TEXT_PLAIN})
public class AuthorisationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisationResource.class);

    @GET
    @Path("/{permission}")
    public Boolean hasAuthorisation(@HeaderParam("callerId") String callerId, @PathParam("permission") String permission){
        LOGGER.info("checking if callerId: " + callerId + " has permission: " + permission);

        Boolean callerHasPermission = false;

        // in real application this is where persisted RBAC would queried
        switch (permission) {
            case "personView"  : callerHasPermission = true; break;
            case "personEdit"  : callerHasPermission = "1".equals(callerId) ? true : false; break; // Only admin can edit/add/delete persons
            case "personAdd"   : callerHasPermission = "1".equals(callerId) ? true : false; break;
            case "personDelete": callerHasPermission = "1".equals(callerId) ? true : false; break;
        }

        if (callerHasPermission) {
            return true;
        }

        throw new WebApplicationException(404);
    }
}