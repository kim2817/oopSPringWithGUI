package BackEnd;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Event implements HasID {
    private String eventID;
    private String eventName;
    private Category eventCat;
    private Room eventRoom;
    private Organizer eventOrg;
    private double ticketPrice;
    private DateTime eventDate;
    private int eventRoomCap;
    private int eventAttendees=0;


    //Constructors
    //no arg constructor
    public Event(){this.eventID= "E"+System.nanoTime();}

    //argument constructor
    public Event(String eventName, Category eventCat, Room eventRoom, Organizer eventOrg ,
                 double ticketPrice, DateTime eventDate){

        //a unique username is set using nano time
        this.eventID= "E"+System.nanoTime();
        this.eventName= eventName;
        this.eventCat= eventCat;
        this.eventRoom= eventRoom;
        this.eventOrg= eventOrg;
        this.ticketPrice= ticketPrice;
        this.eventDate = eventDate;

        //adding this event object to array of this certain category
        eventCat.addEvent(this);
        this.eventRoomCap=eventRoom.getRoomCapacity();
    }

    //accessors
    public String getID(){return eventID;}
    public String getEventName(){return eventName;}
    public Category getEventCat(){return eventCat;}
    public Room getEventRoom(){return eventRoom;}
    public Organizer getEventOrg(){return eventOrg;}
    public double getTicketPrice(){return ticketPrice;}
    public DateTime getEventDate() {return eventDate;}
    public int getEventRoomCap(){return eventRoomCap;}
    public int getEventAttendees(){return eventAttendees;}

    //mutators
    public void setEventName(String eventName){this.eventName=eventName;}
    public void setEventCat(Category eventCat){this.eventCat=eventCat;}
    public void setTicketPrice(double ticketPrice){
        if (ticketPrice>0){
            this.ticketPrice=ticketPrice;
        }
    }
    public void setEventRoom(Room eventRoom) {
        this.eventRoom = eventRoom;
    }
    public void setEventDate(DateTime eventDate){
        this.eventDate=eventDate;
    }


    public boolean isThereEnough(int nOfTickets){
        return eventAttendees + nOfTickets <= eventRoomCap;
    }
    public void addAttendee(int nOfTickets){
        eventAttendees += nOfTickets;
    }


    public void rentRoom(DateTime slot, Room room){
     room.reserveSlot(slot, this);
     this.setEventRoom(room);
     this.setEventDate(slot);
    }


    //CRUD
    static Scanner input = new Scanner(System.in);
    public void update(){Database.update(this);}
    public void showEvent(){
        System.out.println(Database.read(this.eventID));
    }

    public String AttendeeToString(){
        String s;
        s="\nEvent name: "+ eventName + "\nEvent Category:"+ eventCat.getCatName() +
                "\nEvent Room:"+ eventRoom.getRoomName() + "\nTicket Price:"+
                ticketPrice + "\nNumber of tickets remaining:"+ (int)(eventRoomCap-eventAttendees) +
                " \nEvent Date(dd/mm/yyyy):" + eventDate.getDay()+"/"+eventDate.getMonth()+"/"+ eventDate.getYear() +
                "\nEvent time slot"+ eventDate.getTime() +"\n\n";

        return s;
    }
    @Override
    public String toString(){
        String s;
        s="Event ID:"+eventID+"\nEvent name:"+ eventName + "\nEvent Category:"+ eventCat.toString() +
                "\nEvent Room:"+ eventRoom.toString() + "\nEvent organizer:" + eventOrg.toString()+ "\nTicket Price:"+
                ticketPrice + "\nNumber of Event Attendees:"+ eventAttendees+ " out of" + eventRoomCap +
                " hours\nEvent Date:" + eventDate.toString() + "\n\n";

        return s;
    }

    public boolean equals(Event event){return(this.eventID.equals(event.eventID));}

}