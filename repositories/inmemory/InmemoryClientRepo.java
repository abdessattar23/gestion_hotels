package repositories.inmemory;

import entities.Client;
import repositories.ClientRepository;

import java.util.*;

public class InmemoryClientRepo implements ClientRepository {
    private HashMap<UUID, Client> Users = new HashMap<>();

    public Client save(Client Client) {
        Users.put(Client.getId(), Client);
        return Client;
    }

    public Client find(UUID uuid) {
        return Users.get(uuid);
    }

    public List<Client> findAll() {
        return new ArrayList<>(Users.values());
    }

    public Optional<Client> findByEmail(String email){
        return Users.values().stream().filter(client -> client.getEmail().equalsIgnoreCase(email)).findFirst();
    }

}