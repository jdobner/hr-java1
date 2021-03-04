package net.jerrydobner.ility.ropes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SolutionTest {
    final Solution solution = new Solution();


    @Test
    void testBasic() {
        runTest(3, 4, 1, 2, 3, 4, 1, 1, 3);
    }

    @Test
    void testBasic2() {
        runTest(3, 4, 1,2,3,3,1,1,1,1,1,1,10);
    }

    void runTest(int expected, int k, int... n) {
        int result = solution.greedy1(k, n);
        Assertions.assertEquals(expected, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme