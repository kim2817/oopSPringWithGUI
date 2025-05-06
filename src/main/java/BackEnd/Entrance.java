package BackEnd;

import java.util.Scanner;

public class Entrance {


    public static void registerOrganizer(String email, String username, String password,
                                         String DOB,boolean gender,String walletBalance){
        Gender gen= gender?Gender.FEMALE:Gender.MALE;

        Database.create(new Organizer(email,username,password,new DateTime(DOB),gen,Double.parseDouble(walletBalance)));
    }

    public static void registerAttendee(String email, String username, String password,
                                        String DOB,boolean gender,String age, String address, String walletBalance){
        Gender gen= gender?Gender.FEMALE:Gender.MALE;
        Database.create(new Attendee(email, username, password, new DateTime(DOB),gen, Integer.parseInt(age),
                address,Double.parseDouble(walletBalance)));
    }

    public static int login(String username, String password, String ID){

        User curUser = Database.findUser(username, password);
        int ret=0;
        if(curUser instanceof Attendee) ret=2;
        if(curUser instanceof Organizer) ret=3;
        if(curUser instanceof Admin) ret=4;
        switch (ret) {
            case 1:
                return 1;
            case 2:
                ID= curUser.getID();
                return 2;
            case 3:
                ID= curUser.getID();
                return 3;
            case 4:
                ID= curUser.getID();
                return 2;
            default :
                return -1;
        }

    }
}
