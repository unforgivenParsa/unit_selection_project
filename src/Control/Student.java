package Control;

import java.util.ArrayList;

public class Student {
    ArrayList<Course> StudentCourses = new ArrayList<>();
    int numberOfUnits;
    int numberOfGeneralUnits;
    int studentCode;

    public void printInfoStudents() {
        System.out.println("numberOfUnits : " + numberOfUnits + " numberOfGeneralUnits : " + numberOfGeneralUnits + " student Code:" + studentCode);
    }
}
