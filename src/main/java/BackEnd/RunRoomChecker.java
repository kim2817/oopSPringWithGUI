package BackEnd;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunRoomChecker {
    RunRoomChecker() {

    }
    public static void refreshroom(){
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Room r = new Room();
        executor.execute(r);
    }
}
