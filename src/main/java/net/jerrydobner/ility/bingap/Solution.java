package net.jerrydobner.ility.bingap;

import java.util.Scanner;

public class Solution {
    public int solution(int N) {
        return solutionSmart(N);
    }

    public int solutionSmart(int N) {
        var hasOne = false;
        var currentGap = 0;
        var largestGap = 0;
        do {
            var newN = N / 2;
            var isOne = newN * 2 != N;
            N = newN;
            if (isOne) {
                if (hasOne) {
                    largestGap = Math.max(largestGap, currentGap);
                    currentGap = 0;
                } else {
                    hasOne = true;
                }
            } else {
                if (hasOne) {
                    currentGap++;
                }
            }
        } while (N > 0);
        return largestGap;
    }

    public int solutionStupid(int N) {
        int top = 1;
        for (; top << 1 < N; top <<= 1) {

        }
        int zeroCount = 0;
        int largestGap = 0;
        for (; top > 0 ; top >>= 1) {
            if (top <= N) {
                // we have a one
                largestGap = Math.max(largestGap, zeroCount);
                zeroCount = 0;
                N -= top;
            } else {
                //we have a zero
                zeroCount++;
            }
        }
        return largestGap;
    }




    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        while(true) {
            var N = scanner.nextInt();
            System.out.printf("Result for %d: %d\n", N, test(N));
        }
    }

    public static int test(int N) {
        return new Solution().solution(N);
    }
}
