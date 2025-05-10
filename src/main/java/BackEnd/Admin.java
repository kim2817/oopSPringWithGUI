package BackEnd;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Scanner;

import static BackEnd.Database.readAll;


public class Admin extends User{

    //Attributes
    private String role;
    private String workingHours;



    //Constructors//
    public Admin(){}

    public Admin(String email, String username, String password,
                 DateTime dateOfBirth, Gender gen, String role, String workingHours) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gen = gen;
        this.ID = "a" + System.nanoTime();
        this.workingHours = workingHours;
        this.role = role;
    }



    //Setters and getters
    public String getRole() {return role;}
    public String getWorkingHours() {
        return workingHours;
    }
    public String getID() {
        return ID;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }



    //Methods

    public static void addRooms(String roomName, int roomCapacity, double rentPrice){
        Room newroom= new Room(roomName, roomCapacity,rentPrice);
        Database.create(newroom);
    }
    public static void addCat(String catName){
        Category newCat= new Category(catName);
        Database.create(newCat);
    }

    public static ArrayList<Event> searchEvents(String EventName){
            return Database.findEvent(EventName);
    }

    public static ArrayList<Organizer> viewOrganizers(){
        Object[] T = readAll(new Organizer());
            Organizer[] options = new Organizer[T.length];
        for (int i = 0; i < T.length; i++) {
            options[i] = (Organizer) T[i];
        }
        return new ArrayList<>(Arrays.asList(options));
    }

    public static ArrayList<Attendee> viewAttendee(){
        Object[] T = readAll(new Attendee());
        Attendee[] options = new Attendee[T.length];
        for (int i = 0; i < T.length; i++) {
            options[i] = (Attendee) T[i];
        }
        return new ArrayList<>(Arrays.asList(options));

    }


    // CRUD

    @Override
    public String toString(){
        return "Admin{" +
                "email='" + this.email + '\'' +
                ", username='" + this.username + '\'' +
                ", role='" + getRole() + '\'' +
                ", workingHours='" + getWorkingHours() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Admin){
            return (this.role.equals(((Admin) o).getRole()) && (this.workingHours.equals(((Admin) o).getWorkingHours())));
        }
        else return false;
    }

}
