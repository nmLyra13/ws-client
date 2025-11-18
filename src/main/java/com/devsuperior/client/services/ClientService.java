package com.devsuperior.client.services;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.exceptions.ResourceNotFoundException;
import com.devsuperior.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client client = clientRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Client not found. Id = " + id));

        return new ClientDTO(
                client.getId(),
                client.getName(),
                client.getCpf(),
                client.getIncome(),
                client.getBirthDate(),
                client.getChildren()
        );
    }

    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        List<Client> list = clientRepository.findAll();
        return list.stream()
                .map(client -> new ClientDTO(
                        client.getId(),
                        client.getName(),
                        client.getCpf(),
                        client.getIncome(),
                        client.getBirthDate(),
                        client.getChildren()))
                .toList();
    }
}
