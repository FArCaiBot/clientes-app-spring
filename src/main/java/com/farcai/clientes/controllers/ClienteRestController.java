package com.farcai.clientes.controllers;

import com.farcai.clientes.models.entity.Cliente;
import com.farcai.clientes.models.services.IClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "CRUD Cliente", description = "Operaciones crud de clientes")
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/cliente")
public class ClienteRestController {
    @Autowired
    private IClienteService clienteService;


    @GetMapping
    public List<Cliente> index() {
        return clienteService.findAll();
    }

    @GetMapping("/{idCliente}")
    public Cliente show(@PathVariable(name = "idCliente") Long id) {
        return clienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        cliente.setCreateAt(new Date());
        return clienteService.save(cliente);
    }

    @PutMapping("/{idCliente}")
    public Cliente update(@PathVariable(name = "idCliente") Long id, @RequestBody Cliente cliente) {
        Cliente clienteActual = clienteService.findById(id);
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setEmail(cliente.getEmail());

        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "idCliente") Long id) {
        clienteService.delete(id);
    }
}
