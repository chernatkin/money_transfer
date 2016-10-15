package org.chernatkin.accounting.transfer;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

public class StatusTest extends AbstractMoneyTransferTest {
    
    @Test
    public void statusTest(){
        final Response ok = accountingTarget().path("/status").request().get();
        
        Assert.assertEquals(Response.Status.OK.getStatusCode(), ok.getStatus());
        Assert.assertEquals("OK", ok.readEntity(String.class));
    }
}
