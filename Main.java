import entities.Client;
import repositories.*;
import repositories.inmemory.*;
import services.*;
import services.implementation.*;
import utils.Login;
import utils.Register;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ClientRepository repository = new InmemoryClientRepo();
    static AuthService auth = new ImplAuth(repository);

    public static void login(){
        Login login = new Login();

        login.login();
        try{
            Optional<Client> User = auth.login(login.getEmail(), login.getPassword());
            if(User.isPresent()){
                System.out.println("Welcome " + User.get().getFullName() + " among us !!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public static void register(){

        Register register = new Register();

        try{

            register.register();
            Client User = auth.register(register.getFullName(), register.getEmail(), register.getPassword());
            if(User != null){
                System.out.println("Welcome " + User.getFullName() + " among us !!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void list(){
        List<Client> users = auth.getAllUsers();
        System.out.println(users.getFirst());
    }
    public static void main(String[] args) {
        /* important stuff to declare b7al scanner etc.... */
        Register register = new Register();
        Scanner input = new Scanner(System.in);
        boolean running = true;

        while (running) {

            System.out.println("===================================");
            System.out.println("        GESTION DES HOTELLES         ");
            System.out.println("===================================");
            System.out.println("  [1] Register a new account");
            System.out.println("  [2] Login with existing account");
            System.out.println("  [5] List all users");
            System.out.println("  [3] Exit");
            System.out.println("===================================");

            System.out.print("Select an option: ");
            int choice = input.nextInt();
            switch (choice){
                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    running = false;
                    break;

                case 5:
                    list();
                    break;
            }
        }


    }
}