package org.chernatkin.accounting.transfer.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BalanceOverflowException extends WebApplicationException {

    public BalanceOverflowException(long accountId) {
        super(Response.status(Status.BAD_REQUEST).entity("Account " + accountId + " has too much money").build());
    }
}
