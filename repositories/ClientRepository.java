package repositories;
import java.util.HashMap;
import java.util.UUID;

import entities.*;

public interface ClientRepository {
    Client save(Client client);
    Client find(UUID uuid);
    HashMap<UUID, Client> findAll();
    Client findByEmail(String email);
}
