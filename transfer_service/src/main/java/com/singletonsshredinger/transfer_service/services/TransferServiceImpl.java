package com.singletonsshredinger.transfer_service.services;

import com.singletonsshredinger.transfer_service.broker.EventConsumer;
import com.singletonsshredinger.transfer_service.dao.TransferDAO;
import com.singletonsshredinger.transfer_service.exceptions.AppException;
import com.singletonsshredinger.transfer_service.exceptions.BadIdentifierException;
import com.singletonsshredinger.transfer_service.model.Transfer;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TransferServiceImpl implements TransferService {
    private final Logger LOG = Logger.getLogger(EventConsumer.class);

    public void createTransfer() {
        LOG.info("Mocking here like we created transfer");
    }

    public void transfer_completion() {
        LOG.info("Mocking here like we finished transfer");
    }
}
