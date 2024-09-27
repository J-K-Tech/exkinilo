package kn.jktech.exkinilo;

import java.util.Random;

public class perlin {
    private static final int PERMUTATION_SIZE = 512;
    private int[] permutation = new int[PERMUTATION_SIZE];
    private double scale;

    public perlin(long seed, double scale) {
        this.scale = scale;
        Random random = new Random(seed);
        int[] p = new int[256];

        // Initialize the permutation array
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Shuffle the array using the seed
        for (int i = 255; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = p[i];
            p[i] = p[j];
            p[j] = tmp;
        }

        // Populate the permutation array
        for (int i = 0; i < 256; i++) {
            permutation[i] = p[i];
            permutation[i + 256] = p[i];
        }
    }

    // Fade function as defined by Ken Perlin
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    // Linear interpolation
    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    // Gradient function to calculate dot product of gradient vector
    private static double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public double noise(double x, double y, double z) {
        // Apply scaling factor
        x *= scale;
        y *= scale*1.5;
        z *= scale;

        // Calculate unit cube coordinates
        int X = (int) Math.floor(x) & 255;
        int Y = (int) Math.floor(y) & 255;
        int Z = (int) Math.floor(z) & 255;

        // Calculate relative coordinates in cube
        x -= Math.floor(x);
        y -= Math.floor(y);
        z -= Math.floor(z);

        // Calculate fade curves for x, y, z
        double u = fade(x);
        double v = fade(y);
        double w = fade(z);

        // Hash coordinates of cube corners
        int A = permutation[X] + Y;
        int AA = permutation[A] + Z;
        int AB = permutation[A + 1] + Z;
        int B = permutation[X + 1] + Y;
        int BA = permutation[B] + Z;
        int BB = permutation[B + 1] + Z;

        // Interpolate along each axis
        return flattenTop(lerp(w,
                lerp(v,
                        lerp(u, grad(permutation[AA], x, y, z),
                                grad(permutation[BA], x - 1, y, z)),
                        lerp(u, grad(permutation[AB], x, y - 1, z),
                                grad(permutation[BB], x - 1, y - 1, z))),
                lerp(v,
                        lerp(u, grad(permutation[AA + 1], x, y, z - 1),
                                grad(permutation[BA + 1], x - 1, y, z - 1)),
                        lerp(u, grad(permutation[AB + 1], x, y - 1, z - 1),
                                grad(permutation[BB + 1], x - 1, y - 1, z - 1))))

                );
    }

    private double flattenTop(double noiseValue) {
        double flattenThreshold = 0.2;  // Noise values above this will be flattened
        double flatIntensity = 0.1;     // Adjust this to control how flat the top should be

        if (noiseValue > flattenThreshold) {
            return flattenThreshold + (noiseValue - flattenThreshold) * flatIntensity;
        } else {
            return noiseValue;
        }
    }
}

