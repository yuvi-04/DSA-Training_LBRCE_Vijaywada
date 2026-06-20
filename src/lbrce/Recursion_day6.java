package lbrce;

import java.util.HashSet;

public class Recursion_day6 {
    //Question 1: factorial of N
    public static int factorial(int n) {
        if(n==0 || n==1){
            return 1;
        }
        return n*factorial(n-1);
    }

    //Question 2: Fibonacci series
    // 2^n -> O(n)
    public static int fibonacci(int n, int[] memo) {
        if(n==0 || n==1){
            return n;
        }
        if(memo[n]!=0){
            return memo[n];
        }
        memo[n] = fibonacci(n-1, memo) + fibonacci(n-2, memo);
        return memo[n];
    }

    //Question 3: Fast Exponentiation
    public static long fastExpo(long b, int e) {
        if(e == 0) return 1;

        long half = fastExpo(b, e/2);
        long halfSq = half * half;

        if(e % 2 == 0)
            return halfSq;
        else
            return b * halfSq;
    }

    //Question 4: Sum of Digits
    public static int sumOfDigits(int n) {
        n = Math.abs(n); // Handle negative numbers
        if(n == 0) return 0;
        return n % 10 + sumOfDigits(n / 10);
    }

    //Question 5: Reverse a number
    public static int reverseNumber(int n) {
        return reverseHelper(n, 0);
    }
    private static int reverseHelper(int n, int acc) {
        if(n == 0) return acc;
        return reverseHelper(
            n / 10, acc * 10 + n % 10);
    }

    //Question 6: Tower of Hanoi
    public static void solveHanoi(int n,
        char source, char destination, char auxillary) {
            if(n == 1) {
                System.out.println(
                    "Move Disk 1 from " + source +
                    " to " + destination);
                return;
            }
            //step 1
            solveHanoi(n-1, source, auxillary, destination);

            //step 2
            System.out.println("Move Disk " + n +
            " from " + source + " to " + destination);

            //step 3
            solveHanoi(n-1, auxillary, destination, source);
    }

    //Question 8: find Pair Sum
    public static void findPairSum(int[] arr,
        int target, int index, HashSet<Integer> seen) {
            if(index == arr.length) return;

            int needed = target - arr[index];

            if(seen.contains(needed)) {
                System.out.printf(
                    "Pair Found: %d, %d",
                    needed, arr[index]
                );
            }
            seen.add(arr[index]);
            findPairSum(arr, target, index + 1, seen);
    }

    //Question 9: Josephus Problem
    public static int josephus(int n, int k) {
        return josephusZeroBased(n, k) + 1;
        // Convert to 1-based index
    }
    private static int josephusZeroBased(int n, int k) {
        if(n == 1) return 0;
        // Base case: only one person left
        return (josephusZeroBased(n - 1, k) + k) % n;
    }

    //Question 9
    //this one is better
    // O(n) time complexity, O(1) space complexity
    public static int josephusIterative(int n, int k) {
        int result = 0;
        // Base case: josephus(1, k) = 0
        for(int i = 2; i <= n; i++) {
            result = (result + k) % i;
        }
        return result + 1;
        // Convert to 1-based index
    }

    public static void main(String[] args) {
        // System.out.println(factorial(5));
        
        // int n = 10;
        // int[] memo = new int[n+1];
        // System.out.println(fibonacci(n, memo));

        // System.out.println(fastExpo(5, 10));

        // System.out.println(sumOfDigits(-12345));

        // System.out.println(reverseNumber(12345));

        // solveHanoi(7, 'A', 'C', 'B');

        // findPairSum(new int[]{3, 2, 4}, 7, 0, new HashSet<>());

        System.out.println(josephus(41, 2));
    }
}
