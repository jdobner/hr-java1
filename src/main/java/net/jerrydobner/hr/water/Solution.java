package net.jerrydobner.hr.water;

import java.util.LinkedList;

public class Solution {
    public int trap(int[] height) {
        int total = 0;

        var max_left_list = new LinkedList<Integer>();
        int max_left = 0;
        for (int i = 0; i < height.length - 2; i++) {
            max_left = Math.max(max_left, height[i]);
            max_left_list.addFirst(max_left);
        }

        var max_right_list = new LinkedList<Integer>();
        int max_right = 0;
        for (int i = height.length - 1; i > 1; i--) {
            max_right = Math.max(max_right, height[i]);
            max_right_list.add(max_right);
        }
        for (int i = 1; i < height.length - 1; i++ ) {
            int wall_height = Math.min(max_left_list.removeLast(), max_right_list.removeLast());
            var toAdd = Math.max(0, wall_height - height[i]);
            total += toAdd;
        }
        return total;
    }
}
