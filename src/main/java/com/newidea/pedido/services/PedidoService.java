package com.newidea.pedido.services;

import com.newidea.pedido.exceptions.ResourceNotFoundException;
import com.newidea.pedido.models.Pedido;
import com.newidea.pedido.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PedidoService {

    private Logger logger = Logger.getLogger(PedidoService.class.getName());

    @Autowired
    PedidoRepository repository;

    public List<Pedido> findAll() {

        logger.info("Finding all pedidos!");

        return repository.findAll();
    }

    public Pedido findById(Long id) {

        logger.info("Finding one Pedido!");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public Pedido create(Pedido Pedido) {

        logger.info("Creating one Pedido!");

        Optional<Pedido> savedPedido = repository.findById(Pedido.getId());
        if(savedPedido.isPresent()) {
            throw new ResourceNotFoundException(
                    "Pedido already exist with given pedido id: " + Pedido.getId());
        }
        return repository.save(Pedido);
    }

    public Pedido update(Pedido Pedido) {

        logger.info("Updating one Pedido!");

        var entity = repository.findById(Pedido.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setId(Pedido.getId());
        entity.setStatus(Pedido.getStatus());
        entity.setDataEntrada(Pedido.getDataEntrada());

        return repository.save(Pedido);
    }

    public void delete(Long id) {

        logger.info("Deleting one Pedido!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }
}
