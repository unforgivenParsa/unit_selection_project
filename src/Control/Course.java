package Control;

import java.util.ArrayList;

public class Course {
    String courseName;
    String TeacherName;
    String courseExamSchedule;
    String courseSchedule;
    CourseType courseType;
    College collegeType;
    int courseCurrentCapacity;
    int courseCode;
    int DeterminedCapacity;
    int courseSize;
    ArrayList<Student> studentsOfCourse = new ArrayList<>();

    public Course(String Name, String TeacherName, int courseCode, int DeterminedCapacity, int courseSize, String courseExamSchedule, String courseSchedule, CourseType courseType, College collegeType) {
        this.TeacherName = TeacherName;
        this.courseName = Name;
        this.courseCode = courseCode;
        this.courseExamSchedule = courseExamSchedule;
        this.courseSchedule = courseSchedule;
        this.DeterminedCapacity = DeterminedCapacity;
        this.courseSize = courseSize;
        this.courseType = courseType;
        this.collegeType = collegeType;
    }

    public void printInfo() {
        System.out.println("course name : " + this.courseName + " & " + "Code : " + courseCode + " & " + "Course Type : " + courseType + " & " + "Teacher name : " + this.TeacherName + " & " + "Schedule : " + courseSchedule + " & " + "Exam Schedule : " + courseExamSchedule + " & " + "DeterminedCapacity : " + DeterminedCapacity + " & " + "Size : " + courseSize + " & " + "Remaining Capacity : " + (DeterminedCapacity - courseCurrentCapacity));
    }
}
