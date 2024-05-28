package edu.coursera.parallel;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            System.out.println(Runtime.getRuntime().availableProcessors());
        } else {
            System.out.println(Integer.parseInt(ncoresStr));
        }
        double[] input = createArray(200000000);
        long startTime = System.nanoTime();
        double sum = ReciprocalArraySum.seqArraySum(input);
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1e6);
        startTime = System.nanoTime();
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");
        double parSum = ReciprocalArraySum.parArraySum(input);
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1e6);
        startTime = System.nanoTime();
        double parManySum = ReciprocalArraySum.parManyTaskArraySum(input, 4);
        endTime = System.nanoTime();
        System.out.println((endTime - startTime)/1e6);
        System.out.println(sum);
        System.out.println(parSum);
        System.out.println(parManySum);
    }

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    public static double[] createArray(final int N) {
        final double[] input = new double[N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            input[i] = rand.nextInt(100);
            // Don't allow zero values in the input array to prevent divide-by-zero
            if (input[i] == 0.0) {
                i--;
            }
        }
        return input;
    }
}
