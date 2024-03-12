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

    private void adminHomePage(DataBase dataBase, Scanner scanner) {
        System.out.println("1 : show the courses and changes");
        System.out.println("2 : show the information about each courses and add or remove students");
        System.out.println("3 : Exit");
        String txt = scanner.next();
        switch (txt) {
            case "1" -> adminManipulatingCoursesAbility(dataBase, scanner);
            case "2" -> adminPage(dataBase, scanner);
            case "3" -> init(dataBase);
            default -> {
                System.out.println("write 1 or 2 or 3 please");
                adminHomePage(dataBase, scanner);
            }
        }
    }

    private void adminManipulatingCoursesAbility(DataBase dataBase, Scanner scanner) {
        printTheCourses(dataBase, "6");
        System.out.println("1 : add course");
        System.out.println("2 : remove a course");
        System.out.println("3 : add capacity");
        System.out.println("4 : back");
        System.out.println("5 : exit");
        String inputtedSTr = scanner.next();
        if (inputtedSTr.equals("4") || inputtedSTr.equals("back"))
            adminHomePage(dataBase, scanner);
        else if (inputtedSTr.equals("5") || inputtedSTr.equals("exit"))
            init(dataBase);
        if (!isNumeric(inputtedSTr)) {
            System.out.println("choose 1-2-3-4-5");
            adminManipulatingCoursesAbility(dataBase, scanner);
        }
        int temp5 = Integer.parseInt(inputtedSTr);
        switch (temp5) {
            case 1 -> adminAddingCourseAbility(dataBase, scanner);
            case 2 -> adminRemovingCourseAbility(dataBase, scanner);
            case 3 -> adminAddingCapacityAbility(dataBase, scanner);
            default -> {
                System.out.println("choose 1-2-3-4-5");
                adminManipulatingCoursesAbility(dataBase, scanner);
            }
        }
    }

    private void adminPage(DataBase dataBase, Scanner scanner) {
        System.out.println("1:see the courses");
        System.out.println("Enter the code of the course / write ==> back / write ==> exit");
        String codeString = scanner.next();
        if (codeString.equals("back"))
            adminHomePage(dataBase, scanner);
        ExitString(dataBase, codeString);
        if (!isNumeric(codeString)) {
            System.out.println("write a valid code");
            adminPage(dataBase, scanner);
        }
        int code = Integer.parseInt(codeString);
        if (code == 1) {
            printTheCourses(dataBase, "6");
            System.out.println("enter the code");
            code = scanner.nextInt();
        } else if (!dataBase.coursesCodes.contains(code)) {
            System.out.println("this is undefined in the system");
            adminPage(dataBase, scanner);
        }
        int[] info = findInfoOfCourse(dataBase, code);
        adminManipulatingStudentsOfTheCourse(dataBase, info[0], info[1], scanner);
    }

    private void adminManipulatingStudentsOfTheCourse(DataBase dataBase, int college, int i, Scanner scanner) {
        if (college == 1) {
            if (dataBase.Math.CollegeCourses.get(i).studentsOfCourse.size() == 0) {
                System.out.println("the course is empty");
            }
            for (int j = 0; j < dataBase.Math.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
                dataBase.Math.CollegeCourses.get(i).studentsOfCourse.get(j).printInfoStudents();
            }
            dataBase.Math.CollegeCourses.get(i).printInfo();
        } else if (college == 2) {
            if (dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.size() == 0) {
                System.out.println("the course is empty");
            }
            for (int j = 0; j < dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
                dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.get(j).printInfoStudents();
            }
            dataBase.Physics.CollegeCourses.get(i).printInfo();
        } else if (college == 3) {
            if (dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.size() == 0) {
                System.out.println("the course doesn't have any students");
            }
            for (int j = 0; j < dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
                dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.get(j).printInfoStudents();
            }
            dataBase.Chemistry.CollegeCourses.get(i).printInfo();
        } else if (college == 4) {
            if (dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.size() == 0) {
                System.out.println("the course doesn't have any students");
            }
            for (int j = 0; j < dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
                dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.get(j).printInfoStudents();
            }
            dataBase.civilEng.CollegeCourses.get(i).printInfo();
        } else if (college == 5) {
            if (dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.size() == 0) {
                System.out.println("the course doesn't have any students");
            }
            for (int j = 0; j < dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
                dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.get(j).printInfoStudents();
            }
            dataBase.electricalEng.CollegeCourses.get(i).printInfo();
        }
        Course course = new Course("", "", 0, 0, 0, "", "", CourseType.Exclusive, dataBase.Math);
        switch (college) {
            case 1 -> course = dataBase.Math.CollegeCourses.get(i);
            case 2 -> course = dataBase.Physics.CollegeCourses.get(i);
            case 3 -> course = dataBase.Chemistry.CollegeCourses.get(i);
            case 4 -> course = dataBase.civilEng.CollegeCourses.get(i);
            case 5 -> course = dataBase.electricalEng.CollegeCourses.get(i);
            default -> adminHomePage(dataBase, scanner);
        }
        System.out.println("Enter the student code in order to add or remove / write ==> back / ==> exit");
        String codeString = scanner.next();
        if (codeString.equals("back"))
            adminPage(dataBase, scanner);
        else if (codeString.equals("exit"))
            init(dataBase);
        if (!isNumeric(codeString)) {
            System.out.println("please enter a valid code!");
            adminManipulatingStudentsOfTheCourse(dataBase, college, i, scanner);
        }
        int code = Integer.parseInt(codeString);
        System.out.println("1:add Student to Course");
        System.out.println("2:remove Student from Course");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> adminAddingStudentToCourseAbility(dataBase, code, course, scanner);
            case 2 -> adminRemovingStudentFromCourseAbility(dataBase, code, course, scanner);
            default -> {
                System.out.println("number is undefined");
                adminHomePage(dataBase, scanner);
            }
        }
        adminHomePage(dataBase, scanner);
    }

    private void adminRemovingStudentFromCourseAbility(DataBase dataBase, int code, Course course, Scanner scanner) {
        int i2 = 0;
        while (i2 < course.studentsOfCourse.size() && course.studentsOfCourse.get(i2).studentCode != code)
            i2++;
        if (i2 == course.studentsOfCourse.size()) {
            System.out.println("Student doesn't have this course");
            adminHomePage(dataBase, scanner);
        }
        course.studentsOfCourse.get(i2).numberOfUnits -= course.courseSize;
        if (course.courseType == CourseType.General)
            course.studentsOfCourse.get(i2).numberOfGeneralUnits -= course.courseSize;
        course.studentsOfCourse.get(i2).StudentCourses.remove(course);
        course.courseCurrentCapacity--;
        course.studentsOfCourse.remove(i2);
    }

    private void adminAddingStudentToCourseAbility(DataBase dataBase, int code, Course course, Scanner scanner) {
        Student newStudent = new Student();
        if (!dataBase.studentsCodes.contains(code)) {
            dataBase.studentsCodes.add(code);
            newStudent.studentCode = code;
            dataBase.students.add(newStudent);
        }
        int i = 0;
        while (i < dataBase.students.size() && dataBase.students.get(i).studentCode != code)
            i++;
        if (course.courseCurrentCapacity == course.DeterminedCapacity) {
            System.out.println("course's capacity is full");
            adminHomePage(dataBase, scanner);
        } else if (dataBase.students.get(i).numberOfUnits + course.courseSize > 20) {
            System.out.println("number of general units is maximum 20");
            adminHomePage(dataBase, scanner);
        } else if (dataBase.students.get(i).numberOfGeneralUnits + course.courseSize > 5) {
            System.out.println("number of general units is maximum 5");
            adminHomePage(dataBase, scanner);
        } else if ((dataBase.students.get(i).StudentCourses.contains(course))) {
            System.out.println("Student already has this course!!!");
            adminHomePage(dataBase, scanner);
        } else {
            dataBase.students.get(i).StudentCourses.add(course);
            dataBase.students.get(i).numberOfUnits += course.courseSize;
            if (dataBase.students.get(i).numberOfGeneralUnits + course.courseSize > 5) {
                dataBase.students.get(i).numberOfGeneralUnits += course.courseSize;
            }
            course.courseCurrentCapacity++;
            course.studentsOfCourse.add(dataBase.students.get(i));
            System.out.println("student added");
        }
    }


    private void adminAddingCapacityAbility(DataBase dataBase, Scanner scanner) {
        System.out.println("1:see the courses");
        System.out.println("Enter the code of the course / write ==> back / write ==> exit");
        String codeChange = scanner.next();
        if (codeChange.equals("back"))
            adminManipulatingCoursesAbility(dataBase, scanner);
        else if (codeChange.equals("exit"))
            init(dataBase);
        else if (codeChange.equals("1")) {
            printTheCourses(dataBase, "6");
            adminAddingCapacityAbility(dataBase, scanner);
        }
        else if (!isNumeric(codeChange)) {
            System.out.println("enter a valid code");
            adminAddingCapacityAbility(dataBase, scanner);
        }
        int codeChangeNew = Integer.parseInt(codeChange);
        if (!dataBase.coursesCodes.contains(codeChangeNew)) {
            System.out.println("there is no course with that code");
            adminAddingCapacityAbility(dataBase, scanner);
        }
        int[] inputted_Data = findInfoOfCourse(dataBase, codeChangeNew);
        switch (inputted_Data[0]) {
            case 1 -> addCapacityToMaths(dataBase, inputted_Data[1], scanner);
            case 2 -> addCapacityToPhysics(dataBase, inputted_Data[1], scanner);
            case 3 -> addCapacityToChemistry(dataBase, inputted_Data[1], scanner);
            case 4 -> addCapacityToCivilEng(dataBase, inputted_Data[1], scanner);
            case 5 -> addCapacityToElectricalEng(dataBase, inputted_Data[1], scanner);
            default -> {
                System.out.println("The Code is invalid");
                adminAddingCapacityAbility(dataBase, scanner);
            }
        }
        System.out.println("the capacity has changed");
        adminHomePage(dataBase, scanner);
    }
    private void addCapacityToElectricalEng(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("Current capacity:");
        System.out.println(dataBase.electricalEng.CollegeCourses.get(i).DeterminedCapacity);
        System.out.println("Number of filled capacity:");
        System.out.println(dataBase.electricalEng.CollegeCourses.get(i).courseCurrentCapacity);
        System.out.println("Enter the new Capacity");
        int newCapacity = scanner.nextInt();
        dataBase.electricalEng.CollegeCourses.get(i).DeterminedCapacity = newCapacity;
    }
    private void addCapacityToCivilEng(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("Current capacity:");
        System.out.println(dataBase.civilEng.CollegeCourses.get(i).DeterminedCapacity);
        System.out.println("Number of filled capacity:");
        System.out.println(dataBase.civilEng.CollegeCourses.get(i).courseCurrentCapacity);
        System.out.println("Enter the new Capacity");
        int newCapacity = scanner.nextInt();
        dataBase.civilEng.CollegeCourses.get(i).DeterminedCapacity = newCapacity;
    }
    private void addCapacityToChemistry(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("Current capacity:");
        System.out.println(dataBase.Chemistry.CollegeCourses.get(i).DeterminedCapacity);
        System.out.println("Number of filled capacity:");
        System.out.println(dataBase.Chemistry.CollegeCourses.get(i).courseCurrentCapacity);
        System.out.println("Enter the new Capacity");
        int newCapacity = scanner.nextInt();
        dataBase.Chemistry.CollegeCourses.get(i).DeterminedCapacity = newCapacity;
    }
    private void addCapacityToPhysics(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("Current capacity:");
        System.out.println(dataBase.Physics.CollegeCourses.get(i).DeterminedCapacity);
        System.out.println("Number of filled capacity:");
        System.out.println(dataBase.Physics.CollegeCourses.get(i).courseCurrentCapacity);
        System.out.println("Enter the new Capacity");
        int newCapacity = scanner.nextInt();
        dataBase.Physics.CollegeCourses.get(i).DeterminedCapacity = newCapacity;
    }
    private void addCapacityToMaths(DataBase dataBase, int i, Scanner scanner) {
        System.out.println("Current capacity:");
        System.out.println(dataBase.Math.CollegeCourses.get(i).DeterminedCapacity);
        System.out.println("Number of filled capacity:");
        System.out.println(dataBase.Math.CollegeCourses.get(i).courseCurrentCapacity);
        System.out.println("Enter the new Capacity");
        int newCapacity = scanner.nextInt();
        dataBase.Math.CollegeCourses.get(i).DeterminedCapacity = newCapacity;
    }


    private void adminRemovingCourseAbility(DataBase dataBase, Scanner scanner) {
        System.out.println("Enter the code of the course you want to remove / write ==> back / write ==> exit");
        String codeRemove2 = scanner.next();
        if (codeRemove2.equals("back"))
            adminManipulatingCoursesAbility(dataBase, scanner);
        else if (codeRemove2.equals("exit"))
            init(dataBase);
        else if (!isNumeric(codeRemove2)) {
            System.out.println("Enter A number");
            adminRemovingCourseAbility(dataBase, scanner);
        }
        int codeRemove = Integer.parseInt(codeRemove2);
        if (!dataBase.coursesCodes.contains(codeRemove)) {
            System.out.println("there is no course with that code");
            adminRemovingCourseAbility(dataBase, scanner);
        }
        int[] info = findInfoOfCourse(dataBase, codeRemove);
        switch (info[0]) {
            case 1 -> removeCourseFromMaths(dataBase, info[1]);
            case 2 -> removeCourseFromPhysics(dataBase, info[1]);
            case 3 -> removeCourseFromChemistry(dataBase, info[1]);
            case 4 -> removeCourseFromCivilEng(dataBase, info[1]);
            case 5 -> removeCourseFromElectricalEng(dataBase, info[1]);
            default -> {
                System.out.println("The Code Isn't in the courses");
                adminRemovingCourseAbility(dataBase, scanner);
            }
        }
        System.out.println("course removed");

    }
    private void removeCourseFromMaths(DataBase dataBase, int i) {
        for (int j = 0; j < dataBase.Math.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
            dataBase.Math.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfUnits -= dataBase.Math.CollegeCourses.get(i).courseSize;
            if (dataBase.Math.CollegeCourses.get(i).courseType == CourseType.General)
                dataBase.Math.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfGeneralUnits -= dataBase.Math.CollegeCourses.get(i).courseSize;
            dataBase.Math.CollegeCourses.get(i).studentsOfCourse.get(j).StudentCourses.remove(dataBase.Math.CollegeCourses.get(i));
        }
        dataBase.Math.CollegeCourses.remove(dataBase.Math.CollegeCourses.get(i));
    }
    private void removeCourseFromPhysics(DataBase dataBase, int i) {
        for (int j = 0; j < dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
            dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfUnits -= dataBase.Physics.CollegeCourses.get(i).courseSize;
            if (dataBase.Physics.CollegeCourses.get(i).courseType == CourseType.General)
                dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfGeneralUnits -= dataBase.Physics.CollegeCourses.get(i).courseSize;
            dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.get(j).StudentCourses.remove(dataBase.Physics.CollegeCourses.get(i));
        }
        dataBase.Physics.CollegeCourses.remove(dataBase.Physics.CollegeCourses.get(i));
    }
    private void removeCourseFromChemistry(DataBase dataBase, int i) {
        for (int j = 0; j < dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
            dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfUnits -= dataBase.Chemistry.CollegeCourses.get(i).courseSize;
            if (dataBase.Chemistry.CollegeCourses.get(i).courseType == CourseType.General)
                dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfGeneralUnits -= dataBase.Chemistry.CollegeCourses.get(i).courseSize;
            dataBase.Chemistry.CollegeCourses.get(i).studentsOfCourse.get(j).StudentCourses.remove(dataBase.Chemistry.CollegeCourses.get(i));
        }
        dataBase.Chemistry.CollegeCourses.remove(dataBase.Chemistry.CollegeCourses.get(i));
    }
    private void removeCourseFromCivilEng(DataBase dataBase, int i) {
        for (int j = 0; j < dataBase.Physics.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
            dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfUnits -= dataBase.civilEng.CollegeCourses.get(i).courseSize;
            if (dataBase.civilEng.CollegeCourses.get(i).courseType == CourseType.General)
                dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfGeneralUnits -= dataBase.civilEng.CollegeCourses.get(i).courseSize;
            dataBase.civilEng.CollegeCourses.get(i).studentsOfCourse.get(j).StudentCourses.remove(dataBase.civilEng.CollegeCourses.get(i));
        }
        dataBase.civilEng.CollegeCourses.remove(dataBase.civilEng.CollegeCourses.get(i));
    }
    private void removeCourseFromElectricalEng(DataBase dataBase, int i) {
        for (int j = 0; j < dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.size(); j++) {
            dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfUnits -= dataBase.electricalEng.CollegeCourses.get(i).courseSize;
            if (dataBase.electricalEng.CollegeCourses.get(i).courseType == CourseType.General)
                dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.get(j).numberOfGeneralUnits -= dataBase.electricalEng.CollegeCourses.get(i).courseSize;
            dataBase.electricalEng.CollegeCourses.get(i).studentsOfCourse.get(j).StudentCourses.remove(dataBase.electricalEng.CollegeCourses.get(i));
        }
        dataBase.electricalEng.CollegeCourses.remove(dataBase.electricalEng.CollegeCourses.get(i));
    }

    private int adminGenerateTheCodeOfNewCourse(DataBase dataBase, Scanner scanner) {
        System.out.println("Enter the course's code");
        System.out.println("1:back or 2: exit");
        String code = scanner.next();
        //
        if (code.equals("back")|| code.equals("1"))
            adminManipulatingCoursesAbility(dataBase, scanner);
        if (code.equals("exit")|| code.equals("2"))
            init(dataBase);
        if (!isNumeric(code)) {
            System.out.println("write a code");
            adminGenerateTheCodeOfNewCourse(dataBase, scanner);
        }
        int codeNew = Integer.parseInt(code);
        if (dataBase.coursesCodes.contains(codeNew)) {
            System.out.println("this course code has already existed");
            adminGenerateTheCodeOfNewCourse(dataBase, scanner);
        }
        return codeNew;
    }
    private String adminGivesCollegeOfNewCourse(DataBase dataBase,Scanner scanner) {
        System.out.println("please write the college name Of the course");
        System.out.println("1:Math " + "2:Physics " + "3:Chemistry " + "4:civilEng " + "5:electricalEng");
        System.out.println("6:back to previous step");
        System.out.println("7:go back to give the information again");
        System.out.println("8:exit");
        String college = scanner.next();

        switch (college) {
            case "6" -> adminGenerateTheCodeOfNewCourse(dataBase, scanner);
            case "7", "back" -> adminAddingCourseAbility(dataBase, scanner);
            case "exit", "8" -> init(dataBase);
        }
        if (!isNumeric(college) || Integer.parseInt(college)>8 || Integer.parseInt(college)<1) {
            System.out.println("please choose an option ==> 1-2-3-4-5-6-7-8");
            adminGivesCollegeOfNewCourse(dataBase, scanner);
        }
        return college;
    }
    private void checkNameInterference(DataBase dataBase,String nameNew,Scanner scanner) {
        for (int i=0; i<dataBase.Math.CollegeCourses.size(); i++) {
            if (dataBase.Math.CollegeCourses.get(i).courseName.equals(nameNew)) {
                System.out.println("name already is taken by another course");
                adminGivesNameOfNewCourse(dataBase,scanner);
            }
        }
        for (int i=0; i<dataBase.Physics.CollegeCourses.size(); i++) {
            if (dataBase.Physics.CollegeCourses.get(i).courseName.equals(nameNew)) {
                System.out.println("name already is taken by another course");
                adminGivesNameOfNewCourse(dataBase,scanner);
            }
        }
        for (int i=0; i<dataBase.Chemistry.CollegeCourses.size(); i++) {
            if (dataBase.Chemistry.CollegeCourses.get(i).courseName.equals(nameNew)) {
                System.out.println("name already is taken by another course");
                adminGivesNameOfNewCourse(dataBase,scanner);
            }
        }
        for (int i=0; i<dataBase.civilEng.CollegeCourses.size(); i++) {
            if (dataBase.civilEng.CollegeCourses.get(i).courseName.equals(nameNew)) {
                System.out.println("name already is taken by another course");
                adminGivesNameOfNewCourse(dataBase,scanner);
            }
        }
        for (int i=0; i<dataBase.electricalEng.CollegeCourses.size(); i++) {
            if (dataBase.electricalEng.CollegeCourses.get(i).courseName.equals(nameNew)) {
                System.out.println("name already is taken by another course");
                adminGivesNameOfNewCourse(dataBase,scanner);
            }
        }
    }
    private String adminGivesNameOfNewCourse(DataBase dataBase,Scanner scanner) {
        System.out.println("Enter the course's name or write back or exit");
        System.out.println("1:back one step");
        System.out.println("2:go back to give the information again");
        System.out.println("3:exit");
        String name = scanner.next();
        switch (name) {
            case "1" -> adminGivesCollegeOfNewCourse(dataBase, scanner);
            case "2", "back" -> adminAddingCourseAbility(dataBase, scanner);
            case "3", "exit" -> init(dataBase);
        }
        checkNameInterference(dataBase,name,scanner);
        return name;
    }
    private String adminGivesNameTeacherOfNewCourse(DataBase dataBase,Scanner scanner) {
        System.out.println("write the course's teacher or");
        System.out.println("1 : back one step");
        System.out.println("2 : go back to give the information again");
        System.out.println("3 : exit");
        String nameTeacher = scanner.next();

        switch (nameTeacher) {
            case "1" -> adminGivesNameOfNewCourse(dataBase, scanner);
            case "back", "2" -> adminAddingCourseAbility(dataBase, scanner);
            case "exit", "3" -> init(dataBase);
        }
        return nameTeacher;
    }
    private int adminGivesCapacityOfNewCourse(DataBase dataBase,Scanner scanner) {
        System.out.println("Enter the course's capacity or write back or exit");
        System.out.println("1 : back one step");
        System.out.println("2 : go back to give the information again");
        System.out.println("3:exit");
        String capacity = scanner.next();
        //
        if (capacity.equals("1"))
            adminGivesNameTeacherOfNewCourse(dataBase, scanner);
        else if (capacity.equals("back") || capacity.equals("2"))
            adminAddingCourseAbility(dataBase, scanner);
        else if (capacity.equals("exit") || capacity.equals("3"))
            init(dataBase);
        else if (!isNumeric(capacity)) {
            System.out.println("write a number");
            adminGivesCapacityOfNewCourse(dataBase, scanner);
        }
        return Integer.parseInt(capacity);
    }
    private int adminGivesSizeOfNewCourse(DataBase dataBase,Scanner scanner) {
        System.out.println("Enter the course's size / write ==> back / write ==> exit");
        System.out.println("1 : back one step");
        System.out.println("2 : go back to give the information again");
        System.out.println("3 : exit");
        String size = scanner.next();

        if (size.equals("1"))
            adminGivesCapacityOfNewCourse(dataBase, scanner);
        else if (size.equals("back") || size.equals("2"))
            adminAddingCourseAbility(dataBase, scanner);
        else if (size.equals("exit") || size.equals("3"))
            init(dataBase);
        else if (!isNumeric(size)) {
            System.out.println("write a number");
            adminGivesSizeOfNewCourse(dataBase, scanner);
        }
        return Integer.parseInt(size);
    }
    private void adminAddingCourseAbility(DataBase dataBase, Scanner scanner) {
        int codeNew = adminGenerateTheCodeOfNewCourse(dataBase,scanner);

        String college = adminGivesCollegeOfNewCourse(dataBase,scanner);

        String name = adminGivesNameOfNewCourse(dataBase,scanner);

        String nameTeacher = adminGivesNameTeacherOfNewCourse(dataBase,scanner);

        int capacityNew = adminGivesCapacityOfNewCourse(dataBase,scanner);

        int sizeNew = adminGivesSizeOfNewCourse(dataBase,scanner);

        System.out.println("please write the exam schedule Time / write ==> back / write ==> exit");
        String examTime = scanner.next();

        if (examTime.equals("back"))
            adminAddingCourseAbility(dataBase, scanner);
        else if (examTime.equals("exit"))
            init(dataBase);

        System.out.println("please write the course schedule / write ==> back / write ==> exit");
        String time = scanner.next();
        if (time.equals("back"))
            adminAddingCourseAbility(dataBase, scanner);
        else if (time.equals("exit"))
            init(dataBase);

        System.out.println("please write the course type, either general or exclusive / write ==> back / write ==> exit");
        String type = scanner.next();

        if (type.equals("back"))
            adminAddingCourseAbility(dataBase, scanner);
        else if (type.equals("exit"))
            init(dataBase);

        CourseType typeNew;
        typeNew = CourseType.General;
        if (type.equals("Exclusive") || type.equals("exclusive"))
            typeNew = CourseType.Exclusive;
        else if (type.equals("General") || type.equals("general"))
            typeNew = CourseType.General;
        else {
            System.out.println("wrong written information");
            adminAddingCourseAbility(dataBase, scanner);
        }

        switch (college) {
            case "1" -> {

                Course newCourse1 = new Course(name, nameTeacher, codeNew, capacityNew, sizeNew, examTime, time, typeNew, dataBase.Math);
                dataBase.Math.CollegeCourses.add(newCourse1);
                dataBase.coursesCodes.add(codeNew);
            }
            case "2" -> {
                Course newCourse2 = new Course(name, nameTeacher, codeNew, capacityNew, sizeNew, examTime, time, typeNew, dataBase.Physics);
                dataBase.Physics.CollegeCourses.add(newCourse2);
                dataBase.coursesCodes.add(codeNew);
            }
            case "3" -> {
                Course newCourse3 = new Course(name, nameTeacher, codeNew, capacityNew, sizeNew, examTime, time, typeNew, dataBase.Chemistry);
                dataBase.Chemistry.CollegeCourses.add(newCourse3);
                dataBase.coursesCodes.add(codeNew);
            }
            case "4" -> {
                Course newCourse4 = new Course(name, nameTeacher, codeNew, capacityNew, sizeNew, examTime, time, typeNew, dataBase.civilEng);
                dataBase.civilEng.CollegeCourses.add(newCourse4);
                dataBase.coursesCodes.add(codeNew);
            }
            case "5" -> {
                Course newCourse5 = new Course(name, nameTeacher, codeNew, capacityNew, sizeNew, examTime, time, typeNew, dataBase.electricalEng);
                dataBase.electricalEng.CollegeCourses.add(newCourse5);
                dataBase.coursesCodes.add(codeNew);
            }
            default -> {
                System.out.println("wrong written information");
                adminAddingCourseAbility(dataBase, scanner);
            }
        }
    }
    private void ExitString(DataBase dataBase, String string) {
        if (string.equals("exit"))
            init(dataBase);
    }
    private void printTheCourses(DataBase dataBase, String inputCollege) {
        switch (inputCollege) {
            case "1" -> {
                System.out.println("Math:");
                for (int j = 0; j < dataBase.Math.CollegeCourses.size(); j++) {
                    dataBase.Math.CollegeCourses.get(j).printInfo();
                }
            }
            case "2" -> {
                System.out.println("Physics:");
                for (int j = 0; j < dataBase.Physics.CollegeCourses.size(); j++) {
                    dataBase.Physics.CollegeCourses.get(j).printInfo();
                }
            }
            case "3" -> {
                System.out.println("Chemistry:");
                for (int j = 0; j < dataBase.Chemistry.CollegeCourses.size(); j++) {
                    dataBase.Chemistry.CollegeCourses.get(j).printInfo();
                }
            }
            case "4" -> {
                System.out.println("civilEng :");
                for (int j = 0; j < dataBase.civilEng.CollegeCourses.size(); j++) {
                    dataBase.civilEng.CollegeCourses.get(j).printInfo();
                }
            }
            case "5" -> {
                System.out.println("electricalEng :");
                for (int j = 0; j < dataBase.electricalEng.CollegeCourses.size(); j++) {
                    dataBase.electricalEng.CollegeCourses.get(j).printInfo();
                }
            }
            case "6" -> {
                printTheCourses(dataBase, "1");
                printTheCourses(dataBase, "2");
                printTheCourses(dataBase, "3");
                printTheCourses(dataBase, "4");
                printTheCourses(dataBase, "5");
            }
        }

    }
    private int[] findInfoOfCourse(DataBase dataBase, int input_int) {
        int i = 0;
        int[] data_list = {0, 0};
        while (i < dataBase.Math.CollegeCourses.size() && dataBase.Math.CollegeCourses.get(i).courseCode != input_int)
            i++;
        if (i < dataBase.Math.CollegeCourses.size()) {
            data_list[0] = 1;
            data_list[1] = i;
            return data_list;
        }
        i = 0;
        while (i < dataBase.Physics.CollegeCourses.size() && dataBase.Physics.CollegeCourses.get(i).courseCode != input_int)
            i++;
        if (i < dataBase.Physics.CollegeCourses.size()) {
            data_list[0] = 2;
            data_list[1] = i;
            return data_list;
        }
        i = 0;
        while (i < dataBase.Chemistry.CollegeCourses.size() && dataBase.Chemistry.CollegeCourses.get(i).courseCode != input_int)
            i++;
        if (i < dataBase.Chemistry.CollegeCourses.size()) {
            data_list[0] = 3;
            data_list[1] = i;
            return data_list;
        }
        i = 0;
        while (i < dataBase.civilEng.CollegeCourses.size() && dataBase.civilEng.CollegeCourses.get(i).courseCode != input_int)
            i++;
        if (i < dataBase.civilEng.CollegeCourses.size()) {
            data_list[0] = 4;
            data_list[1] = i;
            return data_list;
        }
        i = 0;
        while (i < dataBase.electricalEng.CollegeCourses.size() && dataBase.electricalEng.CollegeCourses.get(i).courseCode != input_int)
            i++;
        if (i < dataBase.electricalEng.CollegeCourses.size()) {
            data_list[0] = 5;
            data_list[1] = i;
            return data_list;
        }
        else
            return data_list;
    }


}
