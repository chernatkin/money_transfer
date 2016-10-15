package org.chernatkin.accounting.transfer;

import java.util.Properties;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.chernatkin.accounting.transfer.ApplicationConfig;
import org.chernatkin.accounting.transfer.service.Account;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;

public abstract class AbstractMoneyTransferTest extends JerseyTest {
    
    private Properties props;
    
    protected Properties getProperties(){
        if(props == null){
            props = new Properties();
        }
        
        return props;
    }
    
    @Override
    protected Application configure() {
        return new ApplicationConfig(getProperties());
    }
    
    protected WebTarget accountingTarget(){
        return target("accounting");
    }
    
    protected Account createAccount(){
        final Account account = accountingTarget().path("/create")
                                                  .request()
                                                  .post(Entity.form(new Form()), Account.class);
        Assert.assertNotNull(account);
        Assert.assertNotNull(account.getId());
        Assert.assertEquals(0, account.getBalance());
        
        return account;
    }
    
    protected long addMoney(Account acc, long amount){
        final Long balance = accountingTarget().path("/add/money")
                                                  .request()
                                                  .post(Entity.form(new Form("id", Long.toString(acc.getId()))
                                                              .param("amount", Long.toString(amount))),
                                                        Long.class);
        Assert.assertNotNull(balance);
        return balance;
    }
    
    protected Response transfer(Account from, Account to, long amount){
        return transfer(from.getId(), to.getId(), amount);
    }
    
    protected Response transfer(long fromId, long toId, long amount){
        Response response = accountingTarget().path("/transfer")
                                              .request()
                                              .post(Entity.form(new Form("from", Long.toString(fromId))
                                                          .param("to", Long.toString(toId))
                                                          .param("amount", Long.toString(amount))));
        
        return response;
    }

    protected Account getAccount(final long id){
        Account acc = accountingTarget().path("account")
                                        .queryParam("id", id)
                                        .request()
                                        .get(Account.class);
        
        Assert.assertNotNull(acc);
        Assert.assertEquals(id, acc.getId());
        return acc;
    }
}
