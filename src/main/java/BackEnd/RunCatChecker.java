package BackEnd;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunCatChecker {
    public static ExecutorService executor;
    RunCatChecker() {

    }
    public static void refreshCat(){
        if(executor == null||executor.isShutdown()) {
            executor = Executors.newFixedThreadPool(1);
        }
        Category r = new Category();
        executor.execute(r);
    }
}
