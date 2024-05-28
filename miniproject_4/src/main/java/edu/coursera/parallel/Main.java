package edu.coursera.parallel;

public class Main {
    final static private int niterations = 40000;

    private static int getNCores() {
        String ncoresStr = System.getenv("COURSERA_GRADER_NCORES");
        if (ncoresStr == null) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return Integer.parseInt(ncoresStr);
        }
    }

    private static double[] createArray(final int N, final int iterations) {
        final double[] input = new double[N + 2];
        int index = N + 1;
        while (index > 0) {
            input[index] = 1.0;
            index -= (iterations / 4);
        }
        return input;
    }

    public static void main(String[] args) {
        int N = 2 * 1024 * 1024;
        double[] myNew = createArray(N, niterations);
        double[] myVal = createArray(N, niterations);
        final double[] myNewRef = createArray(N, niterations);
        final double[] myValRef = createArray(N, niterations);

        System.out.println("Start sequential.");
        final long seqStartTime = System.currentTimeMillis();
        OneDimAveragingPhaser.runSequential(niterations, myNew, myVal, N);
        final long seqEndTime = System.currentTimeMillis();
        System.out.println("Stop sequential.");

        System.out.println("Start parallel fuzzy barrier.");
        final long fuzzyStartTime = System.currentTimeMillis();
        OneDimAveragingPhaser.runParallelFuzzyBarrier(niterations, myNewRef, myValRef, N, getNCores());
        final long fuzzyEndTime = System.currentTimeMillis();
        System.out.println("Stop parallel fuzzy barrier.");

        System.out.println(seqEndTime - seqStartTime);
        System.out.println(fuzzyEndTime - fuzzyStartTime);
    }
}
