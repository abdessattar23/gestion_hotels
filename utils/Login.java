package utils;

import java.util.Scanner;

public class Login {
    private String email;
    private String password;
    private Scanner inp = new Scanner(System.in);

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void login(){
        System.out.print("Enter your email: ");
        this.email = inp.nextLine();
        System.out.print("Enter your password: ");
        this.password = inp.nextLine();
    }
}
