package frontend;

import BackEnd.Category;
import BackEnd.Room;
import BackEnd.RunCatChecker;
import BackEnd.RunRoomChecker;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryDetailsAdmin implements Runnable {
    public static ExecutorService executor;
    CategoryDetailsAdmin(){}
    static void updateCategoryButtons(ArrayList<Category> categories){
        System.out.println("hi");
        AdminInterface.catsVBox.getChildren().clear();
        ArrayList<Button> buttons = new ArrayList<>();
        for(Category category:categories){
            if(category == null) continue;
            Button button = new Button(category.getCatName());
            button.getStyleClass().add("rounded-soft-button");
            button.setOnAction(e->{

            });
            buttons.add(button);
        }
        AdminInterface.catsVBox.getChildren().addAll(buttons);
    }
    static void update(){
        updateCategoryButtons(Category.getCatList());
    }
    public void run() {
        update();
        while(true){
            try{
                RunCatChecker.refreshCat();
                Thread.sleep(2000);
                Platform.runLater(CategoryDetailsAdmin::update);



            }catch(InterruptedException ex){
                System.out.println("thread was intruppted");
                break;
            }

        }
    }
    public static void displayrooms(){
//        update();
        if(executor == null||executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(1);
        }
        executor.execute(new CategoryDetailsAdmin());

    }
}

