package frontend;

import BackEnd.Attendee;
import BackEnd.Event;
import BackEnd.Room;
import BackEnd.RunRoomChecker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventDetailsAdmin implements Runnable{
    public static void show(Event event) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        System.out.println(event.toString());



    }

    @Override
    public void run() {
        while(true){
            try{
                RunRoomChecker.refreshroom();
                Thread.sleep(2000);
                ArrayList<Room>roomsarray = Room.getRoomList();
                for (Room q: roomsarray){
                    AdminInterface.rooms.setText(q.toString());
                    System.out.println(q.toString() + " \n\n");
                }
            }catch(InterruptedException ex){
                System.out.println("thread was intruppted");
            }

        }
    }
    public static void displayrooms(){
        ExecutorService excutor = Executors.newFixedThreadPool(3);
        excutor.execute(new EventDetailsAdmin());



    }
}
