package frontend;

import BackEnd.Attendee;
import BackEnd.Event;
import BackEnd.Room;
import BackEnd.RunRoomChecker;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventDetailsAdmin implements Runnable{
    public static ExecutorService excutor;
    public static void show(Event event) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        System.out.println(event.toString());



    }
    static void updateButtons(ArrayList<Room> rooms){
        AdminInterface.roomsVBox.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        for(Room room:rooms){
            if(room == null){
                continue;
            }
            Button button = new Button(room.getRoomName());
            button.setOnAction(e->{

            });
            buttons.add(button);
        }
        AdminInterface.roomsVBox.getChildren().addAll(buttons);
    }
    @Override
    public void run() {
        while(true){
            try{
                RunRoomChecker.refreshroom();
                Thread.sleep(200);
                Platform.runLater(()->updateButtons(Room.getRoomList()));



            }catch(InterruptedException ex){
                System.out.println("thread was intruppted");
                break;
            }

        }
    }
    public static void displayrooms(){
        if(excutor == null||excutor.isShutdown()) {
            excutor = Executors.newFixedThreadPool(3);
        }
        excutor.execute(new EventDetailsAdmin());

//        AdminInterface.tempback.setOnAction(e->{
//            excutor.shutdownNow();
//
//        });
    }
    public static void stopchecker(){
        if(excutor!= null && !excutor.isShutdown()){
            excutor.shutdownNow();
            excutor = null;
        }
    }
}
