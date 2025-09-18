package services;
import entities.Client;

import java.util.List;
import java.util.Optional;

public interface AuthService {

    Client register(String fullName, String email, String password);
    List<Client> getAllUsers();
    Optional<Client> login(String email, String password);

}
