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

    public Client find(UUID uuid) {
        return Users.get(uuid);
    }

    public HashMap<UUID, Client> findAll() {
        return Users;
    }

    public Client findByEmail(String email){
        return Users.get(UUID.fromString(email));
    }

}