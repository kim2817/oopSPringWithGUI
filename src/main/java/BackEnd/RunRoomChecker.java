package BackEnd;
import frontend.EventDetailsAdmin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunRoomChecker {
    public static ExecutorService executor;
    RunRoomChecker() {

    }
    public static void refreshroom(){
        if(executor == null||executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(3);
        }
        Room r = new Room();
        executor.execute(r);
    }
}
