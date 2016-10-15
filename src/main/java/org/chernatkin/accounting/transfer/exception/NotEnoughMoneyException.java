package org.chernatkin.accounting.transfer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotEnoughMoneyException extends WebApplicationException {

    public NotEnoughMoneyException(long accountId) {
        super(Response.status(Status.BAD_REQUEST).entity("Account " + accountId + " has not enough money").build());
    }
}
