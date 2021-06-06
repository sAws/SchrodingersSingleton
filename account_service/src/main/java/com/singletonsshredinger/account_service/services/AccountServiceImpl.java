package com.singletonsshredinger.account_service.services;

import com.singletonsshredinger.account_service.broker.EventConsumer;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    private final Logger LOG = Logger.getLogger(EventConsumer.class);

    public void validate() {
        LOG.info("Mocking here like we validated account");
    }

}
