package net.jerrydobner.hr.tdwater;

import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SolutionArr {
    public int trap(final int[] height) {

        final var size = height.length;

        final var left = new int[size];
        final var right = new int[size];


        for (int x = 0; x < size - 1; x++) {
            left[x + 1] = max(height[x], left[x]);
        }
        for (int x = size - 1; x > 1; x--) {
            right[x - 1] = max(height[x], right[x]);
        }
        
        int total = 0;
        for (int x = 1; x < size - 1; x++) {
            total += max(0, min(left[x], right[x]) - height[x]);
        }
        return total;
    }
}
