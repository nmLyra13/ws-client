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
    public ClientDTO findById(Long id) { // Este findById vai no banco de dados.
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
        copyDtoToClient(clientDTO, client);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {

        Client client = clientRepository.getReferenceById(id);
        // objeto getReferenceById não vai direto ao banco de dados.
        copyDtoToClient(clientDTO, client);
        client = clientRepository.save(client);
        return new ClientDTO(client);
    }

    private void copyDtoToClient(ClientDTO clientDTO, Client client) {
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
    }
}