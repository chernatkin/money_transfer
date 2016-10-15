package org.chernatkin.accounting.transfer;

import javax.ws.rs.NotFoundException;

import org.chernatkin.accounting.transfer.service.Account;
import org.junit.Assert;
import org.junit.Test;

public class GetAccountTest extends AbstractMoneyTransferTest {

    @Test
    public void successfulGetAccountTest(){
        
        Account acc1 = createAccount();
        Assert.assertEquals(0, acc1.getBalance());
        
        acc1 = getAccount(acc1.getId());
        Assert.assertEquals(0, acc1.getBalance());
        
        Assert.assertEquals(1000, addMoney(acc1, 1000));
        
        acc1 = getAccount(acc1.getId());
        Assert.assertEquals(1000, acc1.getBalance());
        
        Assert.assertEquals(0, addMoney(acc1, -1000));

    }
    
    @Test
    public void successfulGetAccountWithMaxBalanceTest(){
        Account acc1 = createAccount();
        Assert.assertEquals(0, acc1.getBalance());
        
        Assert.assertEquals(Long.MAX_VALUE, addMoney(acc1, Long.MAX_VALUE));
        
        acc1 = getAccount(acc1.getId());
        Assert.assertEquals(Long.MAX_VALUE, acc1.getBalance());
    }
    
    @Test(expected = NotFoundException.class)
    public void accountNotFoundTest(){
        
        getAccount(Long.MAX_VALUE);
    }
    
    @Test(expected = NotFoundException.class)
    public void zeroIdTest(){
        
        getAccount(0);
    }
    
    
    @Test(expected = NotFoundException.class)
    public void negativeIdTest(){
        
        getAccount(-1);
    }
}
