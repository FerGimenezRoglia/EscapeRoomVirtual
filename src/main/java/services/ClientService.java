package services;

import repositories.ClientRepository;
import models.Client;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.util.List;

public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(Connection connection) {
        this.clientRepository = new ClientRepository(connection); // Crear ClientRepository con la Connection
    }

    // Obtener todos los clientes
    public List<Client> getAllClients() throws DataAccessException {
        return clientRepository.getAll();
    }

    // Agregar un nuevo cliente y devolver su ID
    public int addNewClient(String name, String email, boolean isSubscribed) throws DataAccessException {
        Client newClient = new Client(name, email, isSubscribed);
        clientRepository.add(newClient); // Se asigna el ID automáticamente
        return newClient.getId();
    }

    // Obtener o crear un cliente según el nombre
    public int getOrCreateClient(String name, String email, boolean isSubscribed) throws DataAccessException {
        List<Client> clients = clientRepository.getAll();

        for (Client client : clients) {
            if (client.getName().equalsIgnoreCase(name)) {
                return client.getId(); // Si existe, devolver su ID
            }
        }

        return addNewClient(name, email, isSubscribed); // Si no existe, crearlo y devolver su ID
    }

    // 👁🔹👁️ NUEVO metodo para el Observer
    public Client getClientById(int clientId) throws DataAccessException {
        return clientRepository.getById(clientId);
    }

}
