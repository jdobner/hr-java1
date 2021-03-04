package net.jerrydobner.ility.buildings;

import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class Solution {
    public int solution(int[] H) {
        SortedMap<Integer, Integer> heights = new TreeMap<>();
        for (var n : H) {
            var c = heights.computeIfAbsent(n, x-> 0);
            heights.put(n, ++c);
        }
        var len = H.length;
        var tallestLeft = 0;
        var lowestTotal = Integer.MAX_VALUE;
        for (int pos = 0; pos < H.length - 1; pos++) {
            var next = H[pos];
            tallestLeft = Math.max(tallestLeft, next);
            var c = heights.get(next) - 1;
            if (c == 0) {
                heights.remove(next);
            } else {
                heights.put(next, c);
            }
            var newTotal = (pos + 1) * tallestLeft;
            newTotal += ((len - (pos + 1)) * heights.lastKey());
            lowestTotal = Math.min(lowestTotal, newTotal);
        }
        return lowestTotal;
    }

    public static void main(String[] args) {
        var s = new Solution();
        var r = new Random();
        var sz = 100;
        for (int i = 0; i < 11; i++) {
            var heights = new int[sz];
            for (int j = 0; j < sz; j++) {
                heights[j] = r.nextInt(10_000);
            }
            var time = System.currentTimeMillis();
            var rv = s.solution(heights);
            time = System.currentTimeMillis() - time;
            double relTime = (double)time / sz;
            System.out.printf("%3dms  (%.5fms)  Sz: %,8d  rv: %d\n", time, relTime, sz, rv);
            sz *= 2;
        }

    }

}
