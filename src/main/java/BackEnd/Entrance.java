package BackEnd;

import java.util.Scanner;

public class Entrance {


    public static void registerOrganizer(String email, String username, String password,
                                         String DOB,boolean gender,String walletBalance){
        Gender gen= gender?Gender.FEMALE:Gender.MALE;

        Database.create(new Organizer(email,username,password,new DateTime(DOB),gen,Double.parseDouble(walletBalance)));
    }

    public static void registerAttendee(String email, String username, String password,
                                        String DOB,boolean gender,String age, String address, String walletBalance,
                                        String interest1, String interest2, String interest3){
        Gender gen= gender?Gender.FEMALE:Gender.MALE;
        Database.create(new Attendee(email, username, password, new DateTime(DOB),gen, Integer.parseInt(age),
                address,Double.parseDouble(walletBalance),interest1, interest2, interest3));
    }

    public static User login(String username, String password){
        User curUser = Database.findUser(username, password);
        return curUser;
    }
}
