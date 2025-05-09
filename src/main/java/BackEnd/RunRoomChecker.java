package BackEnd;
import frontend.EventDetailsAdmin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunRoomChecker {
    public static ExecutorService executor = Executors.newFixedThreadPool(5);
    RunRoomChecker() {

    }
    public static void refreshroom(){
        Room r = new Room();
        executor.execute(r);
    }

    public static void aykalam(){
        if(executor == null||executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(3);
        }
        executor.execute(new EventDetailsAdmin());

    }
}
