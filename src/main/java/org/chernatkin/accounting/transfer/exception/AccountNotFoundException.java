package org.chernatkin.accounting.transfer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class AccountNotFoundException extends WebApplicationException {

    public AccountNotFoundException(long accountId) {
        super(Response.status(Status.NOT_FOUND).entity("Account " + accountId + " not found").build());
    }

}
