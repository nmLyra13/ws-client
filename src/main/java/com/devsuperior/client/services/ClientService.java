package com.devsuperior.client.services;

import com.devsuperior.client.dto.ClientDTO;
import com.devsuperior.client.entities.Client;
import com.devsuperior.client.exceptions.ResourceNotFoundException;
import com.devsuperior.client.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) { // Este findById vai no banco de dados.
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado! Id = " + id));
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

        try{
            Client client = clientRepository.getReferenceById(id);
            // objeto getReferenceById não vai direto ao banco de dados.
            copyDtoToClient(clientDTO, client);
            client = clientRepository.save(client);
            return new ClientDTO(client);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            clientRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToClient(ClientDTO clientDTO, Client client) {
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setIncome(clientDTO.getIncome());
        client.setBirthDate(clientDTO.getBirthDate());
        client.setChildren(clientDTO.getChildren());
    }
}