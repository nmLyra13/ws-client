package com.devsuperior.client.controllers;

import com.devsuperior.client.entities.Client;
import com.devsuperior.client.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientRepository repository;

    @GetMapping
    public String teste(){
        Optional<Client> result = repository.findById(1L);
        Client client = result.get();
        return client.getName();
    }
}
