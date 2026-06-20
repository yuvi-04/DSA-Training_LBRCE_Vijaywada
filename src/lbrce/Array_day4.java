package lbrce;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Array_day4 {
    //Question 1: find min and max
    public static int[] findMinMax(int arr[]) {
        if(arr == null || arr.length == 0)
            throw new IllegalArgumentException(
                "Empty array"
            );
        int min = arr[0], max = arr[0];
        for(int i = 1; i < arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
            else if(arr[i] < min)
                min = arr[i];
        }
        return new int[]{min, max};

        // IntSummaryStatistics stats = 
        //     Arrays.stream(arr).summaryStatistics();
        
        // return new int[]{stats.getMin(), stats.getMax()};
    }

    //Question 2: Check array is sorted
    public static boolean isSorted(int arr[]) {
        //This approach takes O(n)
        for(int i = 0; i < arr.length - 1; i++) {
            if(arr[i] > arr[i+1])
                return false;
        }
        return true;
    }

    //Question 3: Rotate Array K times
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        //Minimize reundant full rotation
        k = k % n;

        reverse(nums, 0, n-1);
        reverse(nums, 0, k-1);
        reverse(nums, k, n-1);
    }
    public static void reverse(int[] arr, int st, int end) {
        while(st < end) {
            int temp = arr[st];
            arr[st] = arr[end];
            arr[end] = temp;
            st++;
            end--;
        }
    }

    //Q4: two sum all pairs
    public static void twoSumAllPairs(int arr[], int target) {
        if(arr == null || arr.length < 2) return;

        HashMap<Integer, Integer> nums = new HashMap<>();
        for(int num : arr) {
            int complement = target - num;
            if(nums.containsKey(complement)) {
                int count = nums.get(complement);
                for(int i = 0; i < count; i++) {
                    System.out.println(
                        "Pair Found: (" + complement +
                        ", " + num + ")");
                }
            }
            nums.put(num, nums.getOrDefault(
                num, 0) + 1);
        }
    }

    //Question 5: Move all zeros at end
    //Leetcode 283
    public static void moveZeros(int nums[]) {
        int insertPos = 0;
        for(int idx = 0; idx < nums.length; idx++) {
            if(nums[idx] != 0) {
                int temp = nums[insertPos];
                nums[insertPos] = nums[idx];
                nums[idx] = temp;

                insertPos++;
            }
        }
    }

    //Q5: Using streams
    public static void moveZeros2(int nums[]) {
        int[] result = IntStream.concat(
            Arrays.stream(nums).filter(val -> val != 0),
            Arrays.stream(nums).filter(val -> val == 0)
        ).toArray();

        System.arraycopy(result, 0, nums,
            0, nums.length);
    }

    //Question 6: longest subarray sum K
    public static int longestSubarraySum(int[] arr, int k) {
        int currentSum = 0, maxLength = 0;
        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0, -1);

        for(int i = 0; i < arr.length; i++) {
            currentSum += arr[i];
            if(map.containsKey(currentSum - k)) {
                maxLength = Math.max(
                    maxLength, i - map.get(
                        currentSum - k));
            }
            if(!map.containsKey(currentSum)) {
                map.put(currentSum, i);
            }
        }
        return maxLength;
    }

    //Question 7
    public static int maxProductSubarray(int arr[]) {
        int maxProd = arr[0], minProd = arr[0], globalMax = arr[0];

        for(int i = 1; i < arr.length; i++) {
            int curr = arr[i];

            if(curr < 0) {
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }

            maxProd = Math.max(curr, maxProd * curr);
            minProd = Math.min(curr, minProd * curr);

            globalMax = Math.max(globalMax, maxProd);
        }
        return globalMax;
    }

    //Question 8: Boyer-Moore Voting Algorithm
    public static int findMajority(int[] nums) {
        int count = 0;
        int candidate = 0;
        
        //Find Candidate
        for(int num : nums) {
            if(count == 0)
                candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        //Verification Process
        int verificationCount = 0;
        for(int num : nums) {
            if(num == candidate) verificationCount++;
        }
        if(verificationCount > nums.length/2)
            return candidate;

        throw new IllegalArgumentException("No Majority element exists");
        
        //int -> Integer
        // return Arrays.stream(nums)
        //         .boxed()
        //         .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        //         .entrySet()
        //         .stream()
        //         .max(Map.Entry.comparingByValue())
        //         .map(Map.Entry::getKey)
        //         .orElseThrow(() -> new IllegalArgumentException("Invalid Array"));
    }
    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 1, 2, 4};
        //Q1
        // System.out.println(findMinMax(arr)[0]);
        // System.out.println(findMinMax(arr)[1]);

        //Q2
        // System.out.println(isSorted(arr));
        
        //Q3: rotate array using arraycopy
        // int n = arr.length;
        // int k = 2;
        // k = k % n;

        // temporary buffer to hold last k elements
        // int[] temp = new int[k];
        // System.arraycopy(arr, n-k, temp, 0, k);
        // System.arraycopy(arr, 0, arr, k, n-k);
        // System.arraycopy(temp, 0, arr, 0, k);

        //Q3: Using Collections
        // you cannot use int[]
        // Integer[] nums = {1, 2, 3, 4, 5, 6};
        // int k1 = 2;
        // Collections.rotate(Arrays.asList(nums), k1);
        // System.out.println(Arrays.toString(nums));

        //Q3
        // rotate(arr, 2);
        // for(int i: arr)
        //     System.out.print(i + " ");

        //Q4
        // twoSumAllPairs(arr, 5);

        //Q5
        // moveZeros(arr);
        // System.out.println(Arrays.toString(arr));
        //Q5:
        // moveZeros2(arr);
        // System.out.println(Arrays.toString(arr));

        //Q6
        // System.out.println(longestSubarraySum(arr, 7));

        //Q7
        // System.out.println(maxProductSubarray(arr));

        //Q8
        System.out.println(findMajority(arr));
    }
}
