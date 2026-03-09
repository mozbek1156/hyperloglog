package hll;

import java.util.HashSet;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("HyperLogLog Cardinality Estimation Demo");
        System.out.println("----------------------------------------");

        int p = 14; 
        HyperLogLog hll = new HyperLogLog(p);
        HashSet<String> exactSet = new HashSet<>();
        
        // 1. Estimate cardinality of a large dataset
        int numElements = 100000;
        System.out.println("Adding " + numElements + " unique randomly generated strings to HLL...");
        for (int i = 0; i < numElements; i++) {
            String element = UUID.randomUUID().toString();
            hll.add(element);
            exactSet.add(element);
        }

        long exactCount = exactSet.size();
        long hllCount = hll.count();
        double errorRate = Math.abs(exactCount - hllCount) / (double) exactCount;
        double theoreticalExpectedError = 1.04 / Math.sqrt(hll.getM());

        System.out.println("\nResults Partition 1:");
        System.out.println("Exact Count:       " + exactCount);
        System.out.println("HLL Estimate:      " + hllCount);
        System.out.printf("Actual Error Rate: %.4f%%\n", errorRate * 100);
        System.out.printf("Expected Max Err:  %.4f%%\n", theoreticalExpectedError * 100);

        // 2. Test Merging
        System.out.println("\nTesting Merge Feature...");
        HyperLogLog hll2 = new HyperLogLog(p);
        int additionalElements = 50000;
        for (int i = 0; i < additionalElements; i++) {
            String element = "MERGETEST-" + i;
            hll2.add(element);
            exactSet.add(element); // Added to exact set to get the overall distinct count
        }

        hll.merge(hll2);

        long mergedExactCount = exactSet.size();
        long mergedHllCount = hll.count();
        double mergedErrorRate = Math.abs(mergedExactCount - mergedHllCount) / (double) mergedExactCount;

        System.out.println("\nResults After Merged Sets (Partition 1 + Partition 2):");
        System.out.println("Merged Exact Count: " + mergedExactCount);
        System.out.println("Merged HLL Est:     " + mergedHllCount);
        System.out.printf("Merged Error Rate:  %.4f%%\n", mergedErrorRate * 100);

        System.out.println("\nDemo Completed Successfully.");
    }
}
