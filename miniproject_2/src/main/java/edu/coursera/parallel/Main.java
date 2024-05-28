package edu.coursera.parallel;

import java.util.Random;

public class Main {
    final static int REPEATS = 10;
    private final static String[] firstNames = {"Sanjay", "Yunming", "John", "Vivek", "Shams", "Max"};
    private final static String[] lastNames = {"Chatterjee", "Zhang", "Smith", "Sarkar", "Imam", "Grossman"};

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }

    private static Student[] generateStudentData() {
        final int N_STUDENTS = 2000000;
        final int N_CURRENT_STUDENTS = 600000;

        Student[] students = new Student[N_STUDENTS];
        Random r = new Random(123);

        for (int s = 0; s < N_STUDENTS; s++) {
            final String firstName = firstNames[r.nextInt(firstNames.length)];
            final String lastName = lastNames[r.nextInt(lastNames.length)];
            final double age = r.nextDouble() * 100.0;
            final int grade = 1 + r.nextInt(100);
            final boolean current = (s < N_CURRENT_STUDENTS);

            students[s] = new Student(firstName, lastName, age, grade, current);
        }

        return students;
    }

    public static void main(String[] args) {

        Student[] students = generateStudentData();
        StudentAnalytics studentAnalytics = new StudentAnalytics();
        long startTime = System.currentTimeMillis();
        long endTime;
//        studentAnalytics.averageAgeOfEnrolledStudentsParallelStream(students);
        String result = studentAnalytics.mostCommonFirstNameOfInactiveStudentsImperative(students);
        endTime = System.currentTimeMillis();
        System.out.println("Time to mostCommonFirstNameOfInactiveStudentsImperative: " + (endTime - startTime) + " with result = " + result);
        startTime = System.currentTimeMillis();
        result = studentAnalytics.mostCommonFirstNameOfInactiveStudentsParallelStream(students);
        endTime = System.currentTimeMillis();
        System.out.println("Time to mostCommonFirstNameOfInactiveStudentsParallelStream: " + (endTime - startTime) + " with result = " + result);
    }
}
