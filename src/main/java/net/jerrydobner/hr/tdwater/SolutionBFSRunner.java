package net.jerrydobner.hr.tdwater;

import net.jerrydobner.hr.util.Util;

public class SolutionBFSRunner {
    public static void main(String[] args) {
        Util.assertAssert();
        int[][] input = {
                {1, 4, 3, 1, 3, 2},
                {3, 2, 1, 3, 2, 4},
                {2, 3, 3, 2, 3, 1}
        };
        var result = new SolutionBFS().trapRainWater(input);
        assert result == 4 : "result is not 4, instead it's " + result;
        Solution.debug("result: %s\n\n\n", result);
        int[][] input2 = {
                {12,13,1,12},
                {13,4,13,12},
                {13,8,10,12},
                {12,13,12,12},
                {13,13,13,13}};

        result = new SolutionBFS().trapRainWater(input);
        assert result == 4 : "result is not 4, instead it's " + result;
        Solution.debug("result: %s\n", result);

    }
}
