package BackEnd;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;


public class Category implements HasID,Runnable {
    private final String catID; //store the ID of each category
    private String catName; // name of the category
    public static int totCats = 0; // stores total number of categories;
    private ArrayList<Event> events = new ArrayList<>();// stores events under each category;
    private static ArrayList<Category> catList = new ArrayList<>();


    Scanner input = new Scanner(System.in);

    //constructors
    // no-arg constructor
    public Category() {
        this.catID = "C" + System.nanoTime();
        totCats++;
    }

    // Arg constructor
    public Category(String catName) {
        this.catName = catName;
        this.catID = "C" + System.nanoTime();
        totCats++;
    }

    //getters & setters
    public String getID(){
        return catID;
    }
    public String getCatName() {
        return catName;
    }
    public ArrayList<Event> getEvents() {
        Object[] objects = Database.readAll(new Event());
        events.clear();
        for(Object o:objects){
            Event cur = (Event)o;
            if(cur.getEventCat() == this) events.add(cur);
        }
        return events;
    }

    public static ArrayList<Category> getCatList() {
        return catList;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public static void ValidateCatAccess(User obj){
        if (!(obj instanceof Admin)) {
            throw new AccessDenied("You do not have permission to use this method." +
                    " \n Only Admins are allowed to create categories");
        }
    }
    public static void addCatToDatabase(User obj) {
        ValidateCatAccess(obj);
        System.out.println("Enter the name of the Category");
        Scanner input = new Scanner(System.in);
        String name = input.next();
        Object[] T = Database.readAll(new Category());
        Category[] catArr = new Category[T.length];
        for(int i=0;i<T.length;i++){
            catArr[i] = (Category)T[i];
        }
        for(int i = 0;i<catArr.length;i++){
            if(Objects.equals(name, catArr[i].catName)){
                throw new AlreadyExists("This Category Already Exists");
            }


        }
        Database.create(new Category(name));
    }
    public void updateCatInDatabase(User obj){
        ValidateCatAccess(obj);
        System.out.println("What would you like to change?");
        String[] options1 = new String[]{"Category Name","Exit"};
        Scanner in = new Scanner(System.in);
        System.out.println("Choose an option:");
        for(int i=0;i<options1.length;i++){
            System.out.println((i+1) + ") " + options1[i]);
        }
        int choice = in.nextInt();
        if(choice < 1 || choice > options1.length){
            throw new InputMismatchException("Input should be inbounds.");
        }
        switch (choice){
            case 1:
                System.out.println("Original Data : \nCategory Name : " + getCatName());
                System.out.println("Choose an option:\n1. keep\n2. change");
                int choice2 = in.nextInt();
                if(choice2 < 1 || choice2 >2){
                    throw new InputMismatchException("Input should be inbounds.");
                }
                switch(choice2){
                    case 1 :
                        System.out.println("change has been canceled");
                        break;

                    case 2:
                        System.out.println("Enter a new Name");
                        setCatName(in.next());
                        break;
                }

            case 2:
                break;
        }

    }
    public void deleteCatFromDatabase(User obj){
        ValidateCatAccess(obj);
        System.out.println("Are you sure you want to delete this Category? : 1. yes, 2. No");
        int choice = input.nextInt();
        if(choice == 1){
            Database.delete(this);
        }
        else if (choice == 2){
            System.out.println("Deletion canceled");
        }
        else{
            throw new InputMismatchException("Input should be inbounds.");
        }

    }

    public static String[] listAllCategories(){
        Object[] T = Database.readAll(new Category());
        String[] catArr = new String[T.length];
        for(int i=0;i<T.length;i++){
            catArr[i] = ((Category)T[i]).getCatName();
        }
        return catArr;
    }
    public void addEvent(Event event) {
        if (events.size() >= 100) {
            throw new ExceedLimit("You have reached the limit of events for a category");
        } else {
            events.add(event);
        }
    }
    static public void listCat(){
        Object[] T = Database.readAll(new Category());

        Category[] options = new Category[T.length];
        for (int i = 0; i < T.length; i++) {
            options[i] = (Category) T[i];
        }

        catList.clear();
        for(Category e : options){
            catList.add(e);
        }
    }

    public void deleteEventFromCat(Event obj) {
        events.remove(obj);
    }
    @Override
    public void run() {
        while (true){
            try{
                listCat();
                Thread.sleep(200);
            }catch (InterruptedException e){
                System.out.println("thread was intruppted");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " + catID + "\n Name: " + catName;
    }

    public boolean equals(Category cat) {
        return this.catName.equals(cat.catName);
    }


}

