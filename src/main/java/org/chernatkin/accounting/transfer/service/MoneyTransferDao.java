package org.chernatkin.accounting.transfer.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.chernatkin.accounting.transfer.exception.AccountNotFoundException;
import org.chernatkin.accounting.transfer.exception.BalanceOverflowException;
import org.chernatkin.accounting.transfer.exception.NotEnoughMoneyException;

public class MoneyTransferDao {

    private static final int MIN_BALANCE = 0;
    
    private final AtomicLong sequence = new AtomicLong(1);
    
    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    
    public void transfer(final long fromId, final long toId, final long amount){
        final Account from = getAccount(fromId);
        final Account to = getAccount(toId);
        
        final Account acc1;
        final Account acc2;
        if(to.getId() < from.getId()){
            acc1 = to;
            acc2 = from;
        }
        else {
            acc1 = from;
            acc2 = to;
        }
        
        synchronized(acc1){
            synchronized(acc2){
                final long fromBalance = checkBalance(from, -amount);
                final long toBalance = checkBalance(to, amount);
                from.setBalance(fromBalance);
                to.setBalance(toBalance);
            }
        }
    }
    
    public long addMoney(final long accountId, final long amount){
        final Account account = getAccount(accountId);
        
        synchronized(account){
            account.setBalance(checkBalance(account, amount));
        }

        return account.getBalance();
    }
    
    public Account createAccount(){
        final long accountId = sequence.getAndIncrement();
        accounts.put(accountId, new Account(accountId, 0));
        return getAccount(accountId);
    }
    
    public Account getAccount(final long accountId){
        return Optional.ofNullable(accounts.get(accountId)).orElseThrow(() -> new AccountNotFoundException(accountId));
    }
    
    private long checkBalance(final Account account, final long amount){
        try{
            
            return throwIfLessThan(account.getId(), Math.addExact(account.getBalance(), amount));
            
        } catch(ArithmeticException ae){
            throw new BalanceOverflowException(account.getId());
        }
    }
    
    private static long throwIfLessThan(final long accountId, final long balance){
        if(balance < MIN_BALANCE){
            throw new NotEnoughMoneyException(accountId);
        }
        return balance;
    }   
}