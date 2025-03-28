package org.groupJ.models;

public class Paramedic extends User{
    public Paramedic(String id, String loginName, String name, String password, String email, String gender, String phoneNumber) {
        super(id, loginName, name, password, email, gender, phoneNumber);
    }

    public void displayParamedicInfo() {
        System.out.println("Paramedic ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Login Name: " + getLoginName());
        System.out.println("Gender: " + getGender());
        System.out.println("Email: " + getEmail());

    }
}
