package lbrce;

public class Bitwise_day1 {
    //Question 1
    public static String decimalToBinary(int n) {
        if(n == 0) {
            return "0";
        }
        StringBuilder ans = new StringBuilder();
        while(n > 0) {
            ans.append(n & 1);
            n = n >> 1; //Divide by 2
        }
        return ans.reverse().toString();
        //return Integer.toBinaryString(n);
    }

    //Question 2
    public static int binaryToDecimal(String binary) {
        // 1101
        // a[0] = 1, a[1] = 1, a[2] = 0, a[3] = 1
        int ans = 0;
        for(char ch: binary.toCharArray()) {
            ans = (ans << 1) + (ch - '0');
        }
        return ans;
    }

    //Question 3
    public static boolean hasOppositeSigns(int a, int b) {
        return (a ^ b) < 0;
    }

    //Question 4
    public static int addOne(int n) {
        return -~n;
    }

    //Question 5
    public static int[] swapXOR(int a, int b) {
        a = a ^ b;
        b = a ^ b; //b = (a ^ b) ^ b = a
        a = a ^ b; //a = (a ^ b) ^ a = b
        return new int[]{a, b};
    }

    //Question 6
    public static int turnOnBit(int n, int k) {
        return n | (1 << k);
    }
    // ~n = -(n + 1)
    public static int turnOffBit(int n, int k) {
        return n & ~(1 << k);
    }

    //Question 7
    public static boolean binaryPlaindrome(int n) {
        int original = n;
        int reverse = 0;
        while(n > 0) {
            reverse = (reverse << 1) + (n & 1);
            n = n >> 1;
        }
        return original == reverse;
    }

    //Question 8
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n-1)) == 0;
    }

    //Question 9
    // 1 in binary is 0001
    // -1 in binary is 1111 (2's complement)
    public static int countBits(int n) {
        if(n < 0) {
            n = n & Integer.MAX_VALUE; // Handle negative numbers
        }

        int count = 0;
        while(n != 0) {
            n = (n & (n-1));
            count++;
        }
        return count;
    }

    //Question 10
    //0x55555555 is a hexadecimal number that 
    // represents a 32-bit integer where all the 
    // odd bits are set to 0 and all the even bits
    // are set to 1. In binary, it looks like this:
    //01010101010101010101010101010101
    public static boolean isPowerOfFour(int n) {
        return n > 0 &&
            (n & (n-1)) == 0 &&
            (n & 0x55555555) != 0;
    }

    //HW4
    public static int findMissing(int[] arr, int n) {
        int xor1 = 0;
        int xor2 = 0;

        // complete XOR for 1...n
        for(int i = 1; i <= n; i++) {
            xor1 ^= i;
        }
        //XOR for array values
        for(int num: arr) {
            xor2 ^= num;
        }
        return xor1 ^ xor2;
    }

    //Question 11
    public static int findUnique(int arr[]) {
        int result = 0;
        for(int x : arr)
            result = result ^ x;
        return result;
    }

    //Question 12
    public static void findTwounique(int arr[]) {
        int xor = 0;
        for(int x : arr) {
            xor ^= x;
        }
        // Get the rightmost set bit
        int setBit = xor & -xor;

        int num1 = 0, num2 = 0;
        for(int x : arr) {
            if((x & setBit) != 0) {
                num1 ^= x;
            } else {
                num2 ^= x;
            }
        }
        System.out.println("The two unique numbers are: " + num1 + " and " + num2);
    }

    //HW2
    public static int getBitsToFlip(int a, int b) {
        return Integer.bitCount(a^b);
    }
    public static void main(String[] args) {
        // System.out.println(decimalToBinary(10));
        // System.out.println(binaryToDecimal("1010"));
        // System.out.println(hasOppositeSigns(5, -3));
        // System.out.println(addOne(5));
        // int a = 5, b = 10;
        // int[] swapped = swapXOR(a, b);
        // System.out.println("a: " + swapped[0] + ", b: " + swapped[1]);
        // System.out.println(turnOnBit(10, 2));
        // System.out.println(turnOffBit(5, 2));
        // System.out.println(binaryPlaindrome(9)); // 1001
        // System.out.println(binaryPlaindrome(15)); // 1111
        // System.out.println(isPowerOfTwo(90));
        // System.out.println(countBits(-1)); // 1
        // System.out.println(isPowerOfFour(8));

        // int arr[] = {1, 2, 3, 5, 6};
        // System.out.println(findMissing(arr, 6));

        // int ar[] = {2, 3, 4, 4, 3};
        // System.out.println(findUnique(ar));

        // int arr[] = {2, 3, 4, 5, 4, 3};
        // findTwounique(arr);

        System.out.println(getBitsToFlip(29, 15));
    }
}
