package BackEnd;
import java.util.Arrays;

import java.util.Scanner;
import java.util.ArrayList;


public class Attendee extends User implements HasID {
    private String ID;
    private int age;
    private String city;
    private Wallet balance;
    private Category[] interest = new Category[3];
    private ArrayList<Event> bookedEvents = new ArrayList<>();

    public Attendee() {
        this.ID = "A" + System.nanoTime();
    }

    public Attendee(String email, String username,String password, DateTime dateOfBirth, Gender gen,int age,
                    String address, double walletBalance, String interest1, String interest2, String interest3) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gen = gen;
        this.ID = "A" + System.nanoTime();
        this.age = age;
        this.balance = new Wallet(walletBalance);
        interest[0] =Database.findCat(interest1);
        interest[1] =Database.findCat(interest2);
        interest[2] =Database.findCat(interest3);

    }


    public int getAge() {
        return age;
    }
    public String getCity() {
        return city;
    }
    public double getBalance() {
        return balance.getBalance();
    }
    public void attendeeDeposit(double money){
        balance.deposit(money);
    }

    public Category[] getInterest() {
        return interest;
    }
    public ArrayList<Event> getBookedEvents() {
        return bookedEvents;
    }
    public String getAgeGroup(){
        if(age < 18){
            return "<18";
        }
        else if(age < 30){
            return "18-30";
        }
        else if(age < 50){
            return "30-50";
        }
        else return ">50";
    }


    public void setID(String ID) {
        this.ID = ID;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setBalance(Wallet balance) {
        this.balance = balance;
    }
    public void setInterest(Category[] interest) {
        this.interest = interest;
    }
    public void setBookedEvents(ArrayList<Event> bookedEvents) {
        this.bookedEvents = bookedEvents;
    }

    public String showInterest() {
        return (this.interest[0].getCatName() + "\n" + this.interest[1].getCatName()+ "\n" + this.interest[2].getCatName());
    }

    public String showBookedEvents() {
        if (bookedEvents.isEmpty()) {
            return "No booked events";
        }
        else {
            String s="";
            for (Event e : bookedEvents) {
                s+=e.AttendeeToString()+"\n\n\n";
            }
            return s;
        }
    }

    public void chooseEvent() {
        Scanner input = new Scanner(System.in);
        Object[] T = Database.readAll(new Event());
        Event[] options = new Event[T.length];
        for (int i = 0; i < T.length; i++) {
            options[i] = (Event) T[i];
        }
        System.out.println("Please select an event:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ")" + options[i].AttendeeToString());
        }
        try{
            int temp = input.nextInt();
            temp = temp-1;
            Event chosenEvent = options[temp];
            System.out.println(chosenEvent);
            System.out.println("number of tickets");
            int count = input.nextInt();
            buyTickets(count, chosenEvent.getID());
        }catch (Exception ex){
            System.out.println("Something wrong happened here -_-"+ ex.getMessage());
        }
    }

    public void buyTickets(int noOfTickets, String eventID) {
        Event temppurchased = (Event) Database.read(eventID);
        double price = temppurchased.getTicketPrice();
        double total = price * noOfTickets;
        if(noOfTickets < 0){
            throw new NotPostiveAmount("number of tickers is 0");
        }
        if (!balance.isSufficient(total)){
            throw new FundsNOTenough("Not enough money g");
        } if(!temppurchased.isThereEnough(noOfTickets)) {
            throw new EventnotAvaible("Event is not avaible hehe");
        }
        balance.withdraw(total);
        temppurchased.addAttendee(new Attendee(),noOfTickets);
        bookedEvents.add(temppurchased);
        System.out.println("You have purchased " + noOfTickets + " ticket(s) for event ID " + eventID);


    }

    public void viewWalletDetails() {
        System.out.println("Wallet Details: " + balance);
    }



    @Override
    public String toString() {
        return  "Attendee[age=" + age + ", city=" + city + ", balance=" + balance.getBalance() + ", DoB: " + dateOfBirth + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Attendee) {
            return this.age == ((Attendee) o).age && this.city.equals(((Attendee) o).city);
        } else return false;
    }
    @Override
    public String getID() {
        return ID;
    }

    public void bookTickets(Event event, Attendee attendee, int ticketCount) {
        this.balance.withdraw(ticketCount*event.getTicketPrice());
        bookedEvents.add(event);
        event.addAttendee(attendee,ticketCount);
    }
}
