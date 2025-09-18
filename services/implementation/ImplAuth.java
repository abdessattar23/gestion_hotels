package services.implementation;

import entities.Client;
import repositories.ClientRepository;
import services.AuthService;

import java.util.List;
import java.util.Optional;

public class ImplAuth implements AuthService {
    private final ClientRepository repository;
    public ImplAuth(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client register(String fullName, String email, String password) {
        Optional<Client> user = repository.findByEmail(email);
        if(user.isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }else{
            System.out.println("Creating account ...");
        }
        if(password.length() < 6){
            throw new IllegalArgumentException("Password is weak");
        }
        Client User = new Client(fullName, email, password, false);
        repository.save(User);
        return User;
    }

    @Override
    public List<Client> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<Client> login(String email, String password) {
        Optional<Client> user = repository.findByEmail(email);
        if(user.isEmpty()){
            throw new IllegalArgumentException("Account doesn't exist");
        }
        if(user.get().getPassword().equalsIgnoreCase(password)){
            return user;
        }
        throw new IllegalArgumentException("Password is incorrect !!");
    }
}
