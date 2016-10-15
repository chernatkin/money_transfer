package org.chernatkin.accounting.transfer.service;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.chernatkin.accounting.transfer.exception.AccountNotFoundException;

@Path("/accounting")
public class TransferResource {
    
    @Inject
    private MoneyTransferDao moneyTransferDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/status")
    public String status() {
        return "OK";
    }
    
    @POST
    @Path("/transfer")
    public Response transfer(@FormParam("from") final long fromId, @FormParam("to") final long toId, @FormParam("amount") final long amount) {
        checkId(fromId);
        checkId(toId);
        
        moneyTransferDao.transfer(fromId, toId, amount);
        
        return Response.noContent().build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Account create() {
        
        return moneyTransferDao.createAccount();
    }
    
    @POST
    @Path("/add/money")
    public Response addMoney(@FormParam("id") final long accountId, @FormParam("amount") final long amount) {
        checkId(accountId);
        
        return Response.ok(moneyTransferDao.addMoney(accountId, amount)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/account")
    public Account getAccount(@QueryParam("id") final long accountId) {
        checkId(accountId);
        
        return moneyTransferDao.getAccount(accountId);
    }
    
    private void checkId(final long accountId){
        if(accountId <= 0){
            throw new AccountNotFoundException(accountId);
        }
    }
}
