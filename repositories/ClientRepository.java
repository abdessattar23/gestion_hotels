package repositories;
import java.util.HashMap;
import java.util.UUID;

import entities.*;

public interface ClientRepository {
    Client save(Client client);
}
