package utils;

import java.util.Scanner;

public class Register {
    private String email;
    private String password;
    private String fullName;
    private Scanner inp = new Scanner(System.in);

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getFullName(){
        return fullName;
    }

    public void register(){
        System.out.print("Enter your Full Name: ");
        this.fullName = inp.nextLine();
        System.out.print("Enter your email: ");
        this.email = inp.nextLine();
        System.out.print("Enter your password: ");
        this.password = inp.nextLine();
    }
}
