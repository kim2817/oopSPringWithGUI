package BackEnd;

public enum Gender {
    MALE,
    FEMALE;
    public String getTitle(){
        if(this == MALE) return "Mr.";
        else return "Mrs.";
    }
}
