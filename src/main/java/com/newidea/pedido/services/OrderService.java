package com.newidea.pedido.services;



import com.newidea.pedido.exceptions.ResourceNotFoundException;
import com.newidea.pedido.models.OrderEntity;
import com.newidea.pedido.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OrderService {

    private Logger logger = Logger.getLogger(OrderService.class.getName());

    @Autowired
    OrderRepository repository;

    public List<OrderEntity> findAll() {

        logger.info("Finding all Orders!");

        return repository.findAll();
    }

    public OrderEntity findById(Long id) {

        logger.info("Finding one Order!");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public OrderEntity create(OrderEntity Order) {

        logger.info("Creating one Order!");

        Optional<OrderEntity> savedOrder = repository.findById(Order.getId());
        if(savedOrder.isPresent()) {
            throw new ResourceNotFoundException(
                    "Order already exist with given order id: " + Order.getId());
        }
        return repository.save(Order);
    }

    public OrderEntity update(OrderEntity Order) {

        logger.info("Updating one Order!");

        var entity = repository.findById(Order.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setId(Order.getId());
        entity.setStatus(Order.getStatus());
        entity.setDataEntrada(Order.getDataEntrada());

        return repository.save(Order);
    }

    public void delete(Long id) {

        logger.info("Deleting one Order!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
