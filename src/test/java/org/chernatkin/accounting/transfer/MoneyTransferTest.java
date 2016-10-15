package org.chernatkin.accounting.transfer;

import javax.ws.rs.core.Response;

import org.chernatkin.accounting.transfer.service.Account;
import org.junit.Assert;
import org.junit.Test;

public class MoneyTransferTest extends AbstractMoneyTransferTest {

    
    @Test
    public void successfulTransferTest(){
        
        Account acc1 = createAccount();
        Account acc2 = createAccount();
        
        Assert.assertEquals(1000, addMoney(acc1, 1000));
        
        transfer(acc1, acc2, 100);
        
        Assert.assertEquals(900, getAccount(acc1.getId()).getBalance());
        Assert.assertEquals(100, getAccount(acc2.getId()).getBalance());
    }
    
    @Test
    public void successfulNegativeTransferTest(){
        
        Account acc1 = createAccount();
        Account acc2 = createAccount();
        
        Assert.assertEquals(1000, addMoney(acc1, 1000));
        
        transfer(acc2, acc1, -100);
        
        Assert.assertEquals(900, getAccount(acc1.getId()).getBalance());
        Assert.assertEquals(100, getAccount(acc2.getId()).getBalance());
    }
    
    @Test
    public void tooBigBalanceTransferTest(){
        
        Account acc1 = createAccount();
        Account acc2 = createAccount();
        
        Assert.assertEquals(1000, addMoney(acc1, 1000));
        Assert.assertEquals(Long.MAX_VALUE, addMoney(acc2, Long.MAX_VALUE));
        
        Response resp = transfer(acc1, acc2, 1);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(1000, getAccount(acc1.getId()).getBalance());
        Assert.assertEquals(Long.MAX_VALUE, getAccount(acc2.getId()).getBalance());
    }
    
    @Test
    public void notEnoughMoneyTransferTest(){
        
        Account acc1 = createAccount();
        Account acc2 = createAccount();
        
        Assert.assertEquals(1000, addMoney(acc1, 1000));
        
        Response resp = transfer(acc2, acc1, 1);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(1000, getAccount(acc1.getId()).getBalance());
        Assert.assertEquals(0, getAccount(acc2.getId()).getBalance());
    }
    
    @Test
    public void accountNotFoundTransferTest(){
        
        Account acc1 = createAccount();
        
        Response resp = transfer(Long.MAX_VALUE, acc1.getId(), 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
        
        
        resp = transfer(acc1.getId(), Long.MAX_VALUE, 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
    }
    
    @Test
    public void zeroAccountIdTransferTest(){
        
        Account acc1 = createAccount();
        
        Response resp = transfer(0, acc1.getId(), 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
        
        
        resp = transfer(acc1.getId(), 0, 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
    }
    
    @Test
    public void negativeAccountIdTransferTest(){
        
        Account acc1 = createAccount();
        
        Response resp = transfer(-1, acc1.getId(), 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
        
        
        resp = transfer(acc1.getId(), -1, 1);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), resp.getStatus());
        
        Assert.assertEquals(0, getAccount(acc1.getId()).getBalance());
    }
}
