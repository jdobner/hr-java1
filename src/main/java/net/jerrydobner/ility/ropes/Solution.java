package net.jerrydobner.ility.ropes;

public class Solution {
    public int solution(int K, int[] A) {
        return greedy1(K, A);
    }


    public int greedy1(int K, int[] A) {
        int count = 0;
        int size = 0;
        for (var r : A) {
            size += r;
            if (size >= K) {
                count++;
                size = 0;
            }
        }
        return count;
    }
}
