package com.newidea.pedido.controllers;

import com.newidea.pedido.models.Pedido;
import com.newidea.pedido.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pedido> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> findById(@PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pedido create(@RequestBody Pedido Pedido) {
        return service.create(Pedido);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> update(@RequestBody Pedido Pedido) {
        try {
            return ResponseEntity.ok(service.update(Pedido));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
