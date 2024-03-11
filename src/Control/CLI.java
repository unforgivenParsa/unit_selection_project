package Control;

import java.util.Scanner;

public class CLI {
    public void init(DataBase dataBase) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1- Student");
            System.out.println("2- Admin");
            System.out.println("3- End project ");
            String input = scanner.next();
            switch (input) {
                case "1" -> {
                    System.out.println("please Enter Your Student Code");
                    String codeString = scanner.next();
                    if (codeString.length() > 9 || !isNumeric(codeString)) {
                        System.out.println(" your code must be integer and with maximum 8 digits");
                        init(dataBase);
                    }
                    int code = Integer.parseInt(codeString);
                    if (!dataBase.studentsCodes.contains(code)) {
                        dataBase.studentsCodes.add(code);
                        Student newStudent = new Student();
                        newStudent.studentCode = code;
                        dataBase.students.add(newStudent);
                    }
                    int i = 0;
                    while (i < dataBase.students.size() && dataBase.students.get(i).studentCode != code)
                        i++;
                    studentOptions(dataBase, i, scanner);
                }
                case "2" -> adminHomePage(dataBase, scanner);
                case "3", "end" -> System.exit(0);
                default -> {
                    System.out.println("Please choose an option");
                    init(dataBase);
                }
            }
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public boolean checkTimeInterference(Course course, Student student, DataBase dataBase) {
        for (int i = 0; i < student.StudentCourses.size(); i++) {
            if (student.StudentCourses.get(i).courseExamSchedule.equals(course.courseExamSchedule) ||
                    student.StudentCourses.get(i).courseSchedule.equals(course.courseSchedule)) {
                System.out.println("this course is overlapped with other courses!");
                return true;
            }

        }
        return false;

    }

    private void studentOptions(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("1- Show my Registered Courses");
        System.out.println("2- Registering Courses");
        System.out.println("3- exit");
        String inputtedStr = scanner.next();
        if (inputtedStr.equals("exit") || inputtedStr.equals("3"))
            init(dataBase);
        int inputtedInt = Integer.parseInt(inputtedStr);
        if (inputtedInt == 1) {
            studentFirstPage(dataBase, i, scanner);
        } else if (inputtedInt == 2) {
            studentSecondPage(dataBase, i, scanner);
        } else if (inputtedInt == 3)
            studentDataGainer(dataBase);
    }

    private void studentFirstPage(DataBase dataBase, int i, Scanner scanner) {
        if (dataBase.students.get(i).StudentCourses.isEmpty()) {
            System.out.println("You didn't take any courses yet");
            studentOptions(dataBase, i, scanner);
        }
        for (int k = 0; k < dataBase.students.get(i).StudentCourses.size(); k++)
            dataBase.students.get(i).StudentCourses.get(k).printInfo();
        System.out.println("Please write your removal willing course code \n" + "write ==> back \n write ==> exit");
        String inputtedStr = scanner.next();
        if (inputtedStr.equals("back") || inputtedStr.equals("1"))
            studentOptions(dataBase, i, scanner);
        else if (inputtedStr.equals("exit") || inputtedStr.equals("2"))
            init(dataBase);
        else if (!isNumeric(inputtedStr) || inputtedStr.length() > 9) {
            System.out.println("enter again! your code must be integer and with maximum 8 digits");
            studentFirstPage(dataBase, i, scanner);
        }
        int inputtedInt = Integer.parseInt(inputtedStr);
        int i2 = 0;
        while (i2 < dataBase.students.get(i).StudentCourses.size() && dataBase.students.get(i).StudentCourses.get(i2).courseCode != inputtedInt)
            i2++;
        if (i2 == dataBase.students.get(i).StudentCourses.size()) {
            System.out.println("you don't have course with this code!!!");
            studentFirstPage(dataBase, i, scanner);
        }


        dataBase.students.get(i).numberOfUnits -= dataBase.students.get(i).StudentCourses.get(i2).courseSize;
        if (dataBase.students.get(i).StudentCourses.get(i2).courseType.equals(CourseType.General))
            dataBase.students.get(i).numberOfGeneralUnits -= dataBase.students.get(i).StudentCourses.get(i2).courseSize;
        dataBase.students.get(i).StudentCourses.get(i2).courseCurrentCapacity--;


        dataBase.students.get(i).StudentCourses.remove(i2);
        System.out.println("course removed");
        if (dataBase.students.get(i).StudentCourses.isEmpty()) {
            System.out.println("Empty");
            studentOptions(dataBase, i, scanner);
        }
        studentFirstPage(dataBase, i, scanner);

    }

    private void studentSecondPage(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("1:show Math Courses");
        System.out.println("2:show Physics Courses");
        System.out.println("3:show Chemistry Courses");
        System.out.println("4:show civilEng Courses");
        System.out.println("5:show electricalEng Courses");
        System.out.println("6:show all courses");
        System.out.println("Please enter the course code!/ write ==>  back / write ==> exit");

        String input1 = scanner.next();
        if (input1.equals("back"))
            studentOptions(dataBase, i, scanner);
        ExitString(dataBase, input1);
        if (input1.equals("1") || input1.equals("2") || input1.equals("3") || input1.equals("4") || input1.equals("5") || input1.equals("6")) {
            printTheCourses(dataBase, input1);
            System.out.println("Please enter the course code!/ write ==>  back / write ==> exit");
            input1 = scanner.next();
            if (input1.equals("back"))
                studentSecondPage(dataBase, i, scanner);
            else if (input1.equals("exit"))
                init(dataBase);
        } else if (!isNumeric(input1)) {
            System.out.println("Please enter again correctly");
            studentSecondPage(dataBase, i, scanner);
        }
        int temp5 = Integer.parseInt(input1);
        int[] requiredData = findInfoOfCourse(dataBase, temp5);
        if (requiredData[0] == 0) {
            System.out.println("the code you've entered is undefined in the system! ");
            studentSecondPage(dataBase, i, scanner);
        } else if (requiredData[0] == 1) {
            if (checkTimeInterference(dataBase.Math.CollegeCourses.get(requiredData[1]), dataBase.students.get(i), dataBase))
                studentSecondPage(dataBase, i, scanner);
            if (dataBase.Math.CollegeCourses.get(requiredData[1]).DeterminedCapacity > dataBase.Math.CollegeCourses.get(requiredData[1]).courseCurrentCapacity) {
                if (dataBase.students.get(i).numberOfUnits + dataBase.Math.CollegeCourses.get(requiredData[1]).courseSize > 20) {
                    System.out.println("you couldn't have more than 20 units!");
                    studentOptions(dataBase, i, scanner);
                }
                if (dataBase.Math.CollegeCourses.get(requiredData[1]).courseType.equals(CourseType.General)) {
                    if (dataBase.students.get(i).numberOfGeneralUnits + dataBase.Math.CollegeCourses.get(requiredData[1]).courseSize > 5) {
                        System.out.println("Size of your general courses is more than 5");
                        studentOptions(dataBase, i, scanner);
                    }
                    dataBase.students.get(i).numberOfGeneralUnits += dataBase.Math.CollegeCourses.get(requiredData[1]).courseSize;
                }
                dataBase.students.get(i).StudentCourses.add(dataBase.Math.CollegeCourses.get(requiredData[1]));
                dataBase.students.get(i).numberOfUnits += dataBase.Math.CollegeCourses.get(requiredData[1]).courseSize;
                dataBase.Math.CollegeCourses.get(requiredData[1]).courseCurrentCapacity++;
                dataBase.Math.CollegeCourses.get(requiredData[1]).studentsOfCourse.add(dataBase.students.get(i));
            }
        } else if (requiredData[0] == 2) {
            if (checkTimeInterference(dataBase.Physics.CollegeCourses.get(requiredData[1]), dataBase.students.get(i), dataBase))
                studentSecondPage(dataBase, i, scanner);
            if (dataBase.Physics.CollegeCourses.get(requiredData[1]).DeterminedCapacity > dataBase.Physics.CollegeCourses.get(requiredData[1]).courseCurrentCapacity) {
                if (dataBase.students.get(i).numberOfUnits + dataBase.Physics.CollegeCourses.get(requiredData[1]).courseSize > 20) {
                    System.out.println("you couldn't have more than 20 units!");
                    studentOptions(dataBase, i, scanner);
                }
                if (dataBase.Physics.CollegeCourses.get(requiredData[1]).courseType.equals(CourseType.General)) {
                    if (dataBase.students.get(i).numberOfGeneralUnits + dataBase.Physics.CollegeCourses.get(requiredData[1]).courseSize > 5) {
                        System.out.println("you couldn't have more than 5 units from general courses!");
                        studentOptions(dataBase, i, scanner);
                    }
                    dataBase.students.get(i).numberOfGeneralUnits += dataBase.Physics.CollegeCourses.get(requiredData[1]).courseSize;
                }
                dataBase.students.get(i).StudentCourses.add(dataBase.Physics.CollegeCourses.get(requiredData[1]));
                dataBase.students.get(i).numberOfUnits += dataBase.Physics.CollegeCourses.get(requiredData[1]).courseSize;
                dataBase.Physics.CollegeCourses.get(requiredData[1]).courseCurrentCapacity++;
                dataBase.Physics.CollegeCourses.get(requiredData[1]).studentsOfCourse.add(dataBase.students.get(i));
            }
        } else if (requiredData[0] == 3) {
            if (checkTimeInterference(dataBase.Chemistry.CollegeCourses.get(requiredData[1]), dataBase.students.get(i), dataBase))
                studentOptions(dataBase, i, scanner);
            if (dataBase.Chemistry.CollegeCourses.get(requiredData[1]).DeterminedCapacity > dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseCurrentCapacity) {
                if (dataBase.students.get(i).numberOfUnits + dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseSize > 20) {
                    System.out.println("you couldn't have more than 20 units!");
                    studentOptions(dataBase, i, scanner);
                }
                if (dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseType.equals(CourseType.General)) {
                    if (dataBase.students.get(i).numberOfGeneralUnits + dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseSize > 5) {
                        System.out.println("you couldn't have more than 5 units from general courses!");
                        studentOptions(dataBase, i, scanner);
                    }
                    dataBase.students.get(i).numberOfGeneralUnits += dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseSize;
                }
                dataBase.students.get(i).StudentCourses.add(dataBase.Chemistry.CollegeCourses.get(requiredData[1]));
                dataBase.students.get(i).numberOfUnits += dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseSize;
                dataBase.Chemistry.CollegeCourses.get(requiredData[1]).courseCurrentCapacity++;
                dataBase.Chemistry.CollegeCourses.get(requiredData[1]).studentsOfCourse.add(dataBase.students.get(i));
            }
        } else if (requiredData[0] == 4) {
            if (checkTimeInterference(dataBase.civilEng.CollegeCourses.get(requiredData[1]), dataBase.students.get(i), dataBase))
                studentOptions(dataBase, i, scanner);
            if (dataBase.civilEng.CollegeCourses.get(requiredData[1]).DeterminedCapacity > dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseCurrentCapacity) {
                if (dataBase.students.get(i).numberOfUnits + dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseSize > 20) {
                    System.out.println("you couldn't have more than 20 units!");
                    studentOptions(dataBase, i, scanner);
                }
                if (dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseType.equals(CourseType.General)) {
                    if (dataBase.students.get(i).numberOfGeneralUnits + dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseSize > 5) {
                        System.out.println("you couldn't have more than 5 units from general courses!");
                        studentOptions(dataBase, i, scanner);
                    }
                    dataBase.students.get(i).numberOfGeneralUnits += dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseSize;
                }
                dataBase.students.get(i).StudentCourses.add(dataBase.civilEng.CollegeCourses.get(requiredData[1]));
                dataBase.students.get(i).numberOfUnits += dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseSize;
                dataBase.civilEng.CollegeCourses.get(requiredData[1]).courseCurrentCapacity++;
                dataBase.civilEng.CollegeCourses.get(requiredData[1]).studentsOfCourse.add(dataBase.students.get(i));
            }
        } else if (requiredData[0] == 5) {
            if (checkTimeInterference(dataBase.electricalEng.CollegeCourses.get(requiredData[1]), dataBase.students.get(i), dataBase))
                studentOptions(dataBase, i, scanner);
            if (dataBase.electricalEng.CollegeCourses.get(requiredData[1]).DeterminedCapacity > dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseCurrentCapacity) {
                if (dataBase.students.get(i).numberOfUnits + dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseSize > 20) {
                    System.out.println("you couldn't have more than 20 units!");
                    studentOptions(dataBase, i, scanner);
                }
                if (dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseType.equals(CourseType.General)) {
                    if (dataBase.students.get(i).numberOfGeneralUnits + dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseSize > 5) {
                        System.out.println("you couldn't have more than 5 units from general courses!");
                        studentOptions(dataBase, i, scanner);
                    }
                    dataBase.students.get(i).numberOfGeneralUnits += dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseSize;
                }
                dataBase.students.get(i).StudentCourses.add(dataBase.electricalEng.CollegeCourses.get(requiredData[1]));
                dataBase.students.get(i).numberOfUnits += dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseSize;
                dataBase.electricalEng.CollegeCourses.get(requiredData[1]).courseCurrentCapacity++;
                dataBase.electricalEng.CollegeCourses.get(requiredData[1]).studentsOfCourse.add(dataBase.students.get(i));
            }
        }
        System.out.println("course added");
        studentOptions(dataBase, i, scanner);
    }

    private void studentDataGainer(DataBase dataBase) {
        init(dataBase);
    }
}
