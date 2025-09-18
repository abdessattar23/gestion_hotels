package entities;

import java.util.UUID;

public class Client {
    private UUID id;
    private String fullName;
    private String email;
    private String password;
    private boolean isAdmin;

    public Client(UUID id, String fullName, String email, String password, boolean isAdmin){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.id = id;
        this.isAdmin = isAdmin;
    }
    //not dupliacted just when id is not in param while creating user fhmtini
    public Client(String fullName, String email, String password, boolean isAdmin){
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.id = UUID.randomUUID();
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
    public boolean isAdmin() {return isAdmin;}
    public void setAdmin(boolean admin) {this.isAdmin = admin;}

    @Override
    public String toString(){
        return "name: " + fullName + " email" + email + " password: " + password + " id: " + id;
    }
}
