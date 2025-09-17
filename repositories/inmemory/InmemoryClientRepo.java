package repositories.inmemory;

import entities.Client;
import repositories.ClientRepository;

import java.util.HashMap;
import java.util.UUID;

public class InmemoryClientRepo implements ClientRepository {
    private HashMap<UUID, Client> Users = new HashMap<>();

    public Client save(Client Client) {
        Users.put(Client.getId(), Client);
        return Client;
    }
}