package lbrce;

import java.util.HashSet;

public class NumProblem_day2 {

    //Question 1: Spy Number
    public static boolean isSpy(int n) {
        int sum = 0;
        int product = 1;
        int temp = n;

        while(temp > 0) {
            int digit = temp%10;
            sum += digit;
            product *= digit;
            temp = temp/10;
        }
        return sum == product; //return true/false
    }

    public static void printSpyUpToN(int n) {
        for(int i = 1; i <= n; i++) {
            if(isSpy(i))
                System.out.println(i + ", ");
        }
    }

    //Question 2: Magic Number
    public static boolean isMagic(int n) {
        int temp = n;
        while(temp > 9) {
            int sum = 0;
            while(temp > 0) {
                sum += temp%10;
                temp = temp/10;
            }
            temp = sum;
        }
        return temp == 1;

        // if(n <= 0 ) false;
        // return (n -1) % 9 == 0;
    }

    //Question 3: HAPPY NUMBER
    public static boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();
        while(n != 1 && !set.contains(n)) {
            set.add(n);
            int sum = 0;
            int temp = n;

            while(temp > 0) {
                int digit = temp%10;
                sum += (digit*digit);
                temp = temp/10;
            }
            System.out.println(n + ", ");
            n = sum;
        }
        return n == 1;
    }

    //Question 6: NEON NUMBERS
    public static boolean isNeon(int n) {
        long square = (long) n*n;
        int digitSum = 0;
        while(square > 0) {
            digitSum += square % 10;
            square /= 10;
        }
        return digitSum == n;
    }
    public static void printNeonNumbers(int lower, int upper) {
        for(int i = lower; i <= upper; i++) {
            if(isNeon(i))
                System.out.println(i + ", ");
        }
    }

    //Question 4: PERFECT NUMBER
    public static boolean isPerfect(int n) {
        if(n < 1) return false;

        int sum = 1;
        for(int i = 2; (long) i*i <= n; i++) {
            if(n % i == 0) {
                sum += i;
                if(i != n/i) {
                    sum += n/i;
                }
            }
        }
        return sum == n;
    }
    public static void printPerfectNumber(int n) {
        for(int i = 2; i <= n; i++)
            if(isPerfect(i))
                System.out.println(i + ", ");
    }

    //Question 5: PERFECT SQUARE
    public static boolean isSquare(int n) {
        if(n < 0) return false;
        if(n <= 1) return true;
        
        long low = 1, high = n/2;

        while(low <= high) {
            long mid = low + (high - low)/2;
            long midSqr = mid*mid;

            if(midSqr == n)
                return true;
            else if(midSqr < n)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return false;

        // return ((double)Math.sqrt(n)) % 1 == 0;
    }

    //Question 7: BUZZ NUMBER
    public static boolean isBuzzNumber(int n) {
        // divisible by 7 -> n % 7 == 0
        // ends with 7: n % 10 == 7
        return (n % 7 == 0) || (n % 10 == 7);
    }
    public static void printBuzzNumber(int low, int high) {
        for(int i = low; i<=high; i++) {
            if(isBuzzNumber(i))
                System.out.println(i + ", ");
        }
    }

    //Question 8: TRIBONACCI SERIES
    //T(N) = T(N-1) + T(N-2) + T(N-3)
    public static void generateTribonacci(int n) {
        if(n <= 0) return;
        long a = 0, b = 1, c = 1; // 9.22 x 10^18
        System.out.print("TRIBONACCI: ");

        for(int i = 0; i < n; i++) {
            if(i == 0) System.out.print(a + " ");
            else if(i == 1) System.out.print(b + " ");
            else if(i == 2) System.out.print(c + " ");
            else {
                long next = a + b + c;
                System.out.print(next + " ");
                a = b; b = c; c = next;
            }
        }
        System.out.println();
    }
    //PADOVAN:
    // P(n) = P(n-2) + P(n-3)
    //Base case: 1 1 1
    public static void generatePadovan(int n) {
        if(n <= 0) return;
        long a = 1, b = 1, c = 1;
        System.out.print("PADOVAN: ");
        for(int i = 0; i < n; i++) {
            if(i < 3) System.out.print("1 ");
            else {
                long next = a + b;
                System.out.print(next + " ");
                a = b; b = c; c = next;
            }
        }
        System.out.println();
    }
    //JACOBSTHAL SERIES
    // J(n) = j(n-1) + 2*J(n-2)
    public static void generateJacobsthal(int n) {
        if(n <= 0) return;
        long a = 0, b = 1;
        System.out.print("JACOBSTHAL: ");
        for(int i = 0; i < n; i++) {
            if(i == 0) System.out.print(a + " ");
            else if(i == 1) System.out.print(b + " ");
            else {
                long next = b + 2 * a;
                System.out.print(next + " ");
                a = b; b = next;
            }
        }
    }
    public static void main(String[] args) {
        // printSpyUpToN(10000);
        // System.out.println(isMagic(199));
        // System.out.println(isHappy(19));
        // printNeonNumbers(1, 1000);
        // printPerfectNumber(10000);
        // System.out.println(isSquare(400));
        // printBuzzNumber(1, 100);
        generateTribonacci(15);
        generatePadovan(15);
        generateJacobsthal(15);
    }
}
