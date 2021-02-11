package net.jerrydobner.hr.water;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SolutionStream {
    public int trap(final int[] height) {

        final var size = height.length;

        final var left = new int[size];
        final var right = new int[size];



        IntStream.range(0, size - 2)
                .forEach(x -> left[x + 1] = max(height[x], left[x]));
        IntStream.iterate(size - 1, n -> n - 1).limit(size - 2)
                .forEach(x -> right[x - 1] = max(height[x], right[x]));
        return IntStream.range(1, size - 2)
                .map( x -> max(0, min(left[x], right[x]) - height[x])).sum();
    }
}
