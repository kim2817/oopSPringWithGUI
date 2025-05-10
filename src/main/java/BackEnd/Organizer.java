package BackEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Organizer extends User {
    private Wallet balance;

    public Organizer(){
        ID = "O" + System.nanoTime();
    }
    public Organizer(String email, String username, String password, DateTime dateOfBirth, Gender gen, double walletBalance) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = new Wallet(walletBalance);
        this.gen = gen;
        ID = "O" + System.nanoTime();
    }

    public void create(){
        Database.create(this);
    }


    public Event[] listOrganizedEvents() {

        Object[] T = Database.readAll((new Event()));
        Event[] eventArray = new Event[T.length];
        for (int i = 0; i < T.length; i++) {
            eventArray[i] = (Event) T[i];
        }
        int numberOfFiltered = 1;
        Event[] eventArrayFiltered = new Event[1000];
        for (int i = 0; i < (Database.readAll((new Room()))).length; i++) {
            if (eventArray[i].getEventOrg() == this) {
                eventArrayFiltered[numberOfFiltered - 1] = eventArray[i];
                numberOfFiltered++;
            }
        }
        return eventArrayFiltered;
    }
    public Room[] getAvailableRooms(DateTime slot) {
        Object[] T = Database.readAll((new Room()));
        Room[] roomArray = new Room[T.length];
        for(int i=0;i<T.length;i++){
            roomArray[i] = (Room)T[i];
        }
        int numberOfFiltered = 1;
        Room[] roomArrayFiltered = new Room[1000];
        for (int i = 0; i < (Database.readAll((new Room()))).length; i++) {
            if (roomArray[i].isAvailable(slot)) {
                roomArrayFiltered[numberOfFiltered - 1] = roomArray[i];
                numberOfFiltered++;
            }
        }
        return roomArrayFiltered;
    }
    public ArrayList<Event> getOrganizedEvents(){
        return getOrganizedEvents(null);
    }

    public ArrayList<Event> getOrganizedEvents(Category category){
        System.out.println(category);
        Object[] T = Database.readAll((new Event()));
        if(category != null) T = category.getEvents().toArray();
        Event[] eventArray = new Event[T.length];
        for(int i=0;i<T.length;i++){
            eventArray[i] = (Event)T[i];
        }
        ArrayList<Event> eventArrayFiltered = new ArrayList<>();
        for(Event event:eventArray){
            if(event.getEventOrg() == this) eventArrayFiltered.add(event);
        }
        return eventArrayFiltered;
    }

    public Wallet getBalance() {
        return balance;
    }
    public void setBalance(Wallet balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Organizer{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gen=" + gen + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
