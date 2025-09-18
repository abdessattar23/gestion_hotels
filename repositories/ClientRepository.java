package repositories;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import entities.*;

public interface ClientRepository {
    Client save(Client client);
    Client find(UUID uuid);
    List<Client> findAll();
    Optional<Client> findByEmail(String email);
}
