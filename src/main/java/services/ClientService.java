package services;

import repositories.ClientRepository;
import models.Client;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.util.Optional;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(Connection connection) {
        this.clientRepository = new ClientRepository(connection);
    }

    public int getClientIdByEmail(String email) throws DataAccessException {
        Optional<Client> client = clientRepository.getAll()
                .stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();

        return client.map(Client::getId).orElse(-1);
    }

    public int createClient(String name, String email, boolean isSubscribed) throws DataAccessException {
        if (getClientIdByEmail(email) != -1) {
            throw new DataAccessException("El email ya está registrado: " + email);
        }

        Client newClient = new Client(name, email, isSubscribed);
        clientRepository.add(newClient);
        return getClientIdByEmail(email); // Recupera el ID recién creado
    }
}
