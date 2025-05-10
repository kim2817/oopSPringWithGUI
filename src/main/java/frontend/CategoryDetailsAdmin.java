package frontend;

import BackEnd.Category;
import BackEnd.Room;
import BackEnd.RunCatChecker;
import BackEnd.RunRoomChecker;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryDetailsAdmin implements Runnable {
    public static ExecutorService executor;
    CategoryDetailsAdmin(){}
    public void run() {
        while(true){
            try{
                RunCatChecker.refreshCat();
                Thread.sleep(2000);
                ArrayList<Category> Catarray = Category.getCatList();
                StringBuilder res = new StringBuilder();
                for (Category q: Catarray){
                    System.out.println(q.toString() + " \n\n");
                    res.append(q.toString());
                    res.append("\n\n");
                }
                Platform.runLater(()->AdminInterface.categories.setText(res.toString()));



            }catch(InterruptedException ex){
                System.out.println("thread was intruppted");
                break;
            }

        }
    }
    public static void displayrooms(){
        if(executor == null||executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(3);
        }
        executor.execute(new CategoryDetailsAdmin());

    }
}

