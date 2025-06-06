package BackEnd;

public enum TimeSlot{
    MORNING,
    AFTERNOON,
    EVENING;
    public static TimeSlot translate(int x){
        return switch (x) {
            case 0 -> TimeSlot.MORNING;
            case 1 -> TimeSlot.AFTERNOON;
            case 2 -> TimeSlot.EVENING;
            default -> null;
        };
    }


    public String toString() {
        if(this==MORNING){
            return "Morning";
        }else if(this==EVENING){
            return "Evening";
        }else{
            return "Afternoon";
        }

    }
    static public TimeSlot stringTo(String s){
   if(s.equals("MORNING")){
       return MORNING;
   }else if(s.equals("AFTERNOON")){
       return AFTERNOON;
   }else{
       return EVENING;
   }
    }


}