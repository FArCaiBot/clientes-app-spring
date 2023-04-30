package com.farcai.clientes.controllers;

import com.farcai.clientes.models.entity.Cliente;
import com.farcai.clientes.models.services.IClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> show(@PathVariable(name = "idCliente") Long id) {

        Map<String, Object> response = new HashMap<>();
        Cliente cliente = null;
        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        if (cliente == null) {
            response.put("mensaje", "El cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        cliente.setCreateAt(new Date());
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clienteNew = clienteService.save(cliente);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido creado con exito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> update(@PathVariable(name = "idCliente") Long id, @RequestBody Cliente cliente) {
        Cliente clienteActual = clienteService.findById(id);
        Cliente clienteUpdated = null;

        Map<String, Object> response = new HashMap<>();

        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos!"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setEmail(cliente.getEmail());
            clienteUpdated = clienteService.save(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> delete(@PathVariable(name = "idCliente") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {

            clienteService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente en la base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cliente eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
