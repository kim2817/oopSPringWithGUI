package BackEnd;

public class RunRoomChecker {
    RunRoomChecker() {

    }
    public static void Displayrooms(DateTime Userslot){
        Room r = new Room();
        r.setUserslot(Userslot);
        Thread t = new Thread(r);
        t.start();

    }
}
