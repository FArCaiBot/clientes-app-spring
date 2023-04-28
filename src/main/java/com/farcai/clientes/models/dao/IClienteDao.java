package com.farcai.clientes.models.dao;

import com.farcai.clientes.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
}
