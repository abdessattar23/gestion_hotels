package entities;

import java.util.UUID;

public class Client {
    private UUID id;
    private String fullName;
    private String email;
    private String password;

    public Client(UUID id, String fullName, String email, String password){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.id = id;
    }
    //not dupliacted just when id is not in param while creating user fhmtini
    public Client(String fullName, String email, String password){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
