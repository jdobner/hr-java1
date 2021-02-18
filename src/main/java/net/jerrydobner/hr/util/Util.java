package net.jerrydobner.hr.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static void assertAssert() {
        boolean assertOn = false;
        assert assertOn = true;
        if (!assertOn) {
            throw new IllegalStateException("assertions are off");
        }
    }

    public static List<List<Integer>> arraysToLists(int[][] data) {
        List<List<Integer>> listOfLists = new ArrayList<>();
        for (var row : data) {
            List<Integer> list = new ArrayList<>(row.length);
            listOfLists.add(list);
            for (var val : row) {
                list.add(val);
            }
        }
        return listOfLists;
    }
}
