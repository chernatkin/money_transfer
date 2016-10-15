package org.chernatkin.accounting.transfer;

import java.util.Properties;

import javax.inject.Singleton;

import org.chernatkin.accounting.transfer.service.MoneyTransferDao;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    private final Properties props;
    
    public ApplicationBinder(Properties props) {
        this.props = props;
    }

    @Override
    protected void configure() {
        bind(MoneyTransferDao.class).to(MoneyTransferDao.class).in(Singleton.class);
    }
}
