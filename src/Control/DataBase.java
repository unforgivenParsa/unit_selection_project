package Control;

import java.util.ArrayList;

public class DataBase {
    ArrayList<Student> students = new ArrayList<>();
    ArrayList<Integer> studentsCodes = new ArrayList<>();
    ArrayList<Integer> coursesCodes = new ArrayList<>();
    College Math = new College();
    College Physics = new College();
    College Chemistry = new College();
    College civilEng = new College();
    College electricalEng = new College();
    //private Storage storage = new Storage();
    private Student loggedInUser;
    public void init(){

        Course numericalAnalysis = new Course("numericalAnalysis","ranjbar",1402101,20,4,"02/3/28"
                ,"sunday1", CourseType.Exclusive,Math);
        Course discreteMathematics = new Course("discreteMathematics","rezaei",1402102,20,4,"02/3/29"
                ,"sunday2", CourseType.Exclusive,Math);
        Course algebraPhilosophy = new Course("algebraPhilosophy","shahshahani",1402103,20,4,"02/3/30"
                ,"sunday3", CourseType.General,Math);
        coursesCodes.add(1402101);
        coursesCodes.add(1402102);
        coursesCodes.add(1402103);
        Math.CollegeCourses.add(numericalAnalysis);
        Math.CollegeCourses.add(discreteMathematics);
        Math.CollegeCourses.add(algebraPhilosophy);
        Course quantumPhysics = new Course("quantumPhysics","jafari",1402201,20,4,"02/3/28"
                ,"monday1", CourseType.Exclusive,Physics);
        Course generalRelativity = new Course("generalRelativity","bahmanabadi",1402202,20,4,"02/3/29"
                ,"monday2", CourseType.Exclusive,Physics);
        Course nuclearPhysics = new Course("nuclearPhysics","mohebi",1402203,20,4,"02/3/30"
                ,"monday3", CourseType.General,Physics);
        Physics.CollegeCourses.add(quantumPhysics);
        Physics.CollegeCourses.add(generalRelativity);
        Physics.CollegeCourses.add(nuclearPhysics);
        coursesCodes.add(1402201);
        coursesCodes.add(1402202);
        coursesCodes.add(1402203);
        Course organicChemistry = new Course("organicChemistry","hazrati",1402301,20,4,"02/3/28"
                ,"sunday1", CourseType.Exclusive,Chemistry);
        Course analyticalChemistry = new Course("analyticalChemistry","hormozinezhad",1402302,20,4,"02/3/29"
                ,"sunday2", CourseType.Exclusive,Chemistry);
        Course bioChemistry = new Course("bioChemistry","yaghmaei",1402303,20,4,"02/3/30"
                ,"sunday3", CourseType.General,Chemistry);
        Chemistry.CollegeCourses.add(organicChemistry);
        Chemistry.CollegeCourses.add(analyticalChemistry);
        Chemistry.CollegeCourses.add(bioChemistry);
        coursesCodes.add(1402301);
        coursesCodes.add(1402302);
        coursesCodes.add(1402303);
        Course soilMechanics = new Course("soilMechanics","afkari",1402401,20,4,"02/3/28"
                ,"sunday1", CourseType.Exclusive,civilEng);
        Course constructionManagement = new Course("constructionManagement","moazeni",1402402,20,4,"02/3/29"
                ,"sunday2", CourseType.Exclusive,civilEng);
        Course surveying = new Course("surveying","malakotian",1402403,20,4,"02/3/30"
                ,"sunday3", CourseType.General,civilEng);
        civilEng.CollegeCourses.add(soilMechanics);
        civilEng.CollegeCourses.add(constructionManagement);
        civilEng.CollegeCourses.add(surveying);
        coursesCodes.add(1402401);
        coursesCodes.add(1402402);
        coursesCodes.add(1402403);
        Course electromagnetism = new Course("electromagnetism","esmaeili",1402501,20,4,"02/3/28"
                ,"sunday1", CourseType.Exclusive, electricalEng);
        Course controlSystem = new Course("controlSystem","hamzehloeyian",1402502,20,4,"02/3/29"
                ,"sunday2", CourseType.Exclusive, electricalEng);
        Course circuits = new Course("circuits","nemati",1402503,20,4,"02/3/30"
                ,"sunday3", CourseType.General, electricalEng);
        electricalEng.CollegeCourses.add(electromagnetism);
        electricalEng.CollegeCourses.add(controlSystem);
        electricalEng.CollegeCourses.add(circuits);
        coursesCodes.add(1402501);
        coursesCodes.add(1402502);
        coursesCodes.add(1402503);
    }
    public ArrayList<Student> getStudents() {
        return students;
    }

    public Student getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Student loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

}
