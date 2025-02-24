package services;

import repositories.ClientRepository;
import models.Client;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.util.List;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(Connection connection) {
        this.clientRepository = new ClientRepository(connection);
    }

    public List<Client> getAllClients() throws DataAccessException {
        return clientRepository.getAll();
    }

    public int addNewClient(String name, String email, boolean isSubscribed) throws DataAccessException {
        Client newClient = new Client(name, email, isSubscribed);
        clientRepository.add(newClient);
        return newClient.getId();
    }

    public int getOrCreateClient(String name, String email, boolean isSubscribed) throws DataAccessException {
        List<Client> clients = clientRepository.getAll();

        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(name)) {
                return client.getId();
            }
        }

        return addNewClient(name, email, isSubscribed);
    }

    public Client getClientById(int clientId) throws DataAccessException {
        return clientRepository.getById(clientId);
    }
}