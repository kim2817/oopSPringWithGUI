package BackEnd;
import java.util.Arrays;

import java.util.Scanner;


public class Admin extends User{

    //Attributes
    private String role;
    private String workingHours;



    //Constructors//
    public Admin(){}

    public Admin(String email, String username, String contactNo, String password,
                 DateTime dateOfBirth, String address, Gender gen, String role, String workingHours) {
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
    public void setID(String ID) {
        this.ID = "a" + System.nanoTime();
    }


    //Methods

    public void addRooms(Room o, String roomName, int roomCapacity, double rentPrice){
        Database.create(o);
        o.setRoomName(roomName);
        o.setRoomCapacity(roomCapacity);
        o.setRentPrice(rentPrice);

    }

    public void viewEvents(){
        Scanner input = new Scanner(System.in);
        Object[] E = Database.readAll(new Event());
        Event[] options = new Event[E.length];
        for(int i=0;i<E.length;i++){
            options[i] = (Event)E[i];
        }
        System.out.println("Please choose a category to view for details: ");
        for (int i=0;i< options.length;i++){
            System.out.println("(" + (i+1) + ")" + "Event name: " + options[i].getEventName() + "   Event ID: " + options[i].getID());
        }
        System.out.println(Arrays.toString(Database.readAll(options[input.nextInt()])));


        input.close();
    }

    public void viewOrganizers(){
        System.out.println(Arrays.toString(Database.readAll(new Organizer())));
    }

    public void viewAttendee(){
        System.out.println(Arrays.toString(Database.readAll(new Attendee())));
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
