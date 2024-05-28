import edu.coursera.parallel.MatrixMultiply;

import java.util.Random;

public class Main {
    public static void main (String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        int N = 768;
        // Create a random input
        final double[][] A = createMatrix(N);
        final double[][] B = createMatrix(N);
        final double[][] C = new double[N][N];
        final double[][] refC = new double[N][N];

        long startTime = System.nanoTime();
        MatrixMultiply.seqMatrixMultiply(A,B,C,N);
        long endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000);

        startTime = System.nanoTime();
        MatrixMultiply.parMatrixMultiply(A,B,C,N);
        endTime = System.nanoTime();
        System.out.println((endTime-startTime)/1000000);
    }

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }

    /**
     * Create a double[] of length N to use as input for the tests.
     *
     * @param N Size of the array to create
     * @return Initialized double array of length N
     */
    private static double[][] createMatrix(final int N) {
        final double[][] input = new double[N][N];
        final Random rand = new Random(314);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                input[i][j] = rand.nextInt(100);
            }
        }

        return input;
    }
}
