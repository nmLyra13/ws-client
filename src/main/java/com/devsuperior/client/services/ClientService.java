package com.devsuperior.client.services;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.exceptions.ResourceNotFoundException;
import com.devsuperior.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
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
        public Page<ClientDTO> findAll(Pageable pageable) {
            Page<Client> list = clientRepository.findAll(pageable);
            return list.map(client -> new ClientDTO(
                    client.getId(),
                    client.getName(),
                    client.getCpf(),
                    client.getIncome(),
                    client.getBirthDate(),
                    client.getChildren()
        ));
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {

        Client client = new Client();

        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());

        client = clientRepository.save(client);

        return new ClientDTO(client);
    }
}