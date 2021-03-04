package net.jerrydobner.ility.distinctinarray;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public int solution(int[] A) {
        Set<Integer> distinct = new HashSet<>();
        for (int N : A) {
            distinct.add(N);
        }
        return distinct.size();
    }
}
