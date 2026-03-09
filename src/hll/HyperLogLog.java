package hll;

import java.util.Arrays;

public class HyperLogLog {
    private final int p;
    private final int m;
    private final double alphaMM;
    private final byte[] registers;

    /**
     * @param p The number of bits used for bucketing. Typically 14 or 16.
     */
    public HyperLogLog(int p) {
        if (p < 4 || p > 16) {
            throw new IllegalArgumentException("p must be between 4 and 16");
        }
        this.p = p;
        this.m = 1 << p;
        this.registers = new byte[m];

        // Calculate alpha_m * m^2
        double alpha;
        switch (m) {
            case 16:
                alpha = 0.673;
                break;
            case 32:
                alpha = 0.697;
                break;
            case 64:
                alpha = 0.709;
                break;
            default:
                alpha = 0.7213 / (1.0 + 1.079 / m);
                break;
        }
        this.alphaMM = alpha * m * m;
    }

    /**
     * Adds an element to the HyperLogLog.
     */
    public void add(String element) {
        int hash = MurmurHash.hash(element);
        
        // Use first p bits for bucket index
        int index = hash >>> (32 - p);
        
        // Remaining 32-p bits for zero counting
        // We add a 1 essentially to the beginning of the bit string implicitly in the original paper
        // But simply using numberOfLeadingZeros of the left shifted value is standard.
        // We shift left by p to remove the bucket bits, then count leading zeros + 1.
        int w = hash << p;
        int rho = Integer.numberOfLeadingZeros(w) + 1;
        
        // If the remaining bits were all 0 (w=0 text), then rho string has length 32-p+1
        if (w == 0) {
            rho = 32 - p + 1;
        }
        
        if (rho > registers[index]) {
            registers[index] = (byte) rho;
        }
    }

    /**
     * Estimates the cardinality based on the internal registers.
     */
    public long count() {
        double z = 0;
        int zeroRegisters = 0;
        for (int i = 0; i < m; i++) {
            z += 1.0 / (1L << registers[i]);
            if (registers[i] == 0) {
                zeroRegisters++;
            }
        }
        
        double e = alphaMM / z;
        
        // Small range correction
        if (e <= 2.5 * m) {
            if (zeroRegisters > 0) {
                e = m * Math.log((double) m / zeroRegisters);
            }
        } 
        // Large range correction (for 32-bit hash)
        else if (e > (1L << 32) / 30.0) {
            double pow32 = 1L << 32;
            e = -pow32 * Math.log(1.0 - e / pow32);
        }
        
        return Math.round(e);
    }

    /**
     * Modifies this HyperLogLog structure by fusing it with another one.
     */
    public void merge(HyperLogLog other) {
        if (this.p != other.p) {
            throw new IllegalArgumentException("Cannot merge HyperLogLogs with different p values.");
        }
        for (int i = 0; i < m; i++) {
            if (other.registers[i] > this.registers[i]) {
                this.registers[i] = other.registers[i];
            }
        }
    }

    public int getP() {
        return p;
    }

    public int getM() {
        return m;
    }
}
