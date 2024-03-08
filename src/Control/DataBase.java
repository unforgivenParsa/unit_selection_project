package Control;

import java.util.ArrayList;

public class DataBase {
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Integer> studentsCodes = new ArrayList<>();
    ArrayList<Integer> coursesCodes = new ArrayList<>();
    College Math = new College();
    College Physics = new College();
    College Chemistry = new College();
    College Law = new College();
    College Finance = new College();
    //private Storage storage = new Storage();
    private Student loggedInUser;

}
