package BackEnd;

public class DateTime {
    // attributes
    private int day;
    private int month;
    private int year;
    public TimeSlot time;
    public DateTime(){}
    public DateTime(int day,int month , int year){
        this(day,month,year,null);
    }
    public DateTime(int day, int month, int year, TimeSlot time){
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
    }
    public DateTime(String S){
        S = checkFormat(S);
        if(S == null){
            throw new RuntimeException("invalid format");
        }
        this(Integer.parseInt(S.substring(0,2)),Integer.parseInt(S.substring(3,5)),Integer.parseInt(S.substring(6)));
    }
    public static String checkFormat(String s){
        if(s.length() < 8) return null;
        if(s.charAt(1)=='/') s = "0" + s;
        if(s.charAt(4)=='/') s = s.substring(0,3) + "0" + s.substring(3);
        if(s.length() != 10) return null;
        if(s.charAt(2) != '/' || s.charAt(5) != '/') return null;
        return s;
    }
    // setters & getters
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public TimeSlot getTime() {
        return time;
    }

    public void setDay(int day) {

        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTime(TimeSlot time) {
        this.time = time;
    }
    public static String displayTime(Event event){
        return event.getEventDate().getDay() + "/" + event.getEventDate().getMonth()+ "/"+ event.getEventDate().getYear()+
                "\n  at time:"+ event.getEventDate().getTime();
    }

    public String DOBToString(){
        return (this.getDay()+"/"+this.getMonth()+"/"+this.getYear());
    }

    @Override
    public String toString() {
        String ret = "Day: " + day + "; Month: " + month + "; Year: " + year;
        if(time != null) return ret + "; Time: " + time;
        return ret;
    }
}
