package frontend;

import BackEnd.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static BackEnd.DateTime.displayTime;

public class EventDetailsAdmin implements Runnable{
    public static ExecutorService excutor;
    public static void show(Event event) {
        Stage stage = new Stage();
        stage.setTitle("Event Details");
        System.out.println(event.toString());



    }
    static void updateRoomButtons(ArrayList<Room> rooms){
        AdminInterface.roomsVBox.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        for(Room room:rooms){
            Button button = new Button(room.getRoomName());
            button.getStyleClass().add("rounded-soft-button");
            button.setOnAction(e->{

            });
            buttons.add(button);
        }
        AdminInterface.roomsVBox.getChildren().addAll(buttons);
    }
    static void updateCategoryButtons(ArrayList<Category> categories){
//        AdminInterface.catVPane.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        for(Category category:categories){
            Button button = new Button(category.getCatName());
            button.getStyleClass().add("rounded-soft-button");
            button.setOnAction(e->{

            });
            buttons.add(button);
        }
        AdminInterface.roomsVBox.getChildren().addAll(buttons);
    }
    static void update(){
        updateRoomButtons(Room.getRoomList());
        updateCategoryButtons(Category.getCatList());
    }
    @Override
    public void run() {
        while(true){
            try{
                RunRoomChecker.refreshroom();
                Thread.sleep(2000);
                Platform.runLater(()->update());

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
}
