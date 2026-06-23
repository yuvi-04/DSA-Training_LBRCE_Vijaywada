package lbrce;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.stream.IntStream;

public class Sorting_day8 {
    //Q1: Find inversion count in array
    //leetcode 315
    public static long inversionCount(int[] arr) {
        int[] temp = new int[arr.length];
	    return mergeSortAndCount(
            arr, temp, 0, arr.length-1);
    }
    private static long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        long count = 0;
        if(left < right) {
            int mid = left + (right - left) / 2;
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid+1, right);
            count += mergeAndCount(arr, temp, left, mid, right);
        }
        return count;
    }
    private static long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left, j = mid+1, k = left;
        long count = 0;

        while(i <= mid && j <= right) {
            if(arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                //arr[i] > arr[j] all will be inversion
                count += (mid - i + 1);
            }
        }

        while(i <= mid)
            temp[k++] = arr[i++];
        while(j <= right)
            temp[k++] = arr[j++];
        for(i = left; i <= right; i++)
            arr[i] = temp[i];
        return count;
    }

    //Q2: Dutch Flag
    //Count sort -> this can be solved using this
    //leetcode 75
    public static void dutchFlag(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        
        while(mid <= high) {
            if(nums[mid] == 0) {
                swap(nums, low, mid);
                low++;
                mid++;
            } else if(nums[mid] == 1) {
                mid++;
            } else if(nums[mid] == 2) {
                swap(nums, mid, high);
                high--;
                //mid will not be incremented here
                //becuase the element that is swapped needs
                //to be evaluated in the next iteration
            }
        }
    }
    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //Q3: Kth largest element in an array
    //count sort -> this can be solved using this O(n)
    //count sort works if range is upto 10^5
    //leetcode 215
    public static int kLargestQuickSelect(int[] nums, int k) {
	    int targetIdx = nums.length - k;
	    return quickSelect(nums, 0, nums.length - 1, targetIdx);
    }

    private static int quickSelect(int[] nums, int left, int right, int targetIdx) {
        if(left == right) return nums[left];
        
        Random rand = new Random();
        int pivotIdx = left + rand.nextInt(right - left + 1);

        pivotIdx = partition(nums, left, right, pivotIdx);

        if(pivotIdx == targetIdx)
            return nums[pivotIdx];
        else if(pivotIdx < targetIdx)
            return quickSelect(nums, pivotIdx+1, right, targetIdx);
        else return quickSelect(nums, left, pivotIdx-1, targetIdx);

    }
    private static int partition(int[] nums, int left, int right, int pivotIdx) {
        int pivotValue = nums[pivotIdx];
        swap(nums, pivotIdx, right);
        int storeIdx = left;

        for(int i = left; i < right; i++) {
            if(nums[i] < pivotValue) {
                swap(nums, i, storeIdx);
                storeIdx++;
            }
        }
        //Move pivot to its correct position
        swap(nums, storeIdx, right);
        return storeIdx;
    }

    //Q4: Count reverse Pairs
    // Fenwick Trees / Binary Indexed Trees using these as well
    //leetcode 493
    public static int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length-1);
    }
    private static int mergeSort(int[] nums, int start, int end) {
        if(start >= end) return 0;

        int mid = start + (end - start) / 2;
        int count = mergeSort(nums, start, mid) +
            mergeSort(nums, mid+1, end);

        int j = mid+1;
        for(int i = start; i <= mid; i++) {
            while(j <= end && nums[i] > 2.0*nums[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }
        merge(nums, start, mid, end);
        return count;
    }
    private static void merge(int[] nums, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;

        while(i <= mid && j <= end) {
            if(nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        while(i <= mid) {
            temp[k++] = nums[i++];
        }
        while(j <= end) {
            temp[k++] = nums[j++];
        }

        System.arraycopy(temp, 0, nums, start, temp.length);
    }

    //Q5: Sort a nearly-sorted (k-sorted) array efficiently
    //Min heap
    //leetcode 23
    public static void kSortedArray(int[] arr, int k) {
        if(arr == null || arr.length == 0) return;

        //By default priority Queue is min Heap
        PriorityQueue<Integer> minheap = new PriorityQueue<>();
        int n = arr.length;
        int length = Math.min(n , k+1);

        for(int i = 0; i<length; i++)
            minheap.add(arr[i]);
        
        int targetIdx = 0;
        for(int i = k+1; i < n; i++) {
            arr[targetIdx++] = minheap.poll();
            minheap.add(arr[i]);
        }
        while(!minheap.isEmpty())
            arr[targetIdx++] = minheap.poll();
    }

    //Q6: Minimum number of swaps using cycle detection
    //leetcode 765
    public static int minSwaps(int[] arr) {
        int n = arr.length;

        Integer[] map = IntStream.range(
            0, n)
            .boxed().toArray(Integer[]::new);

        Arrays.sort(map, Comparator.comparingInt(i -> arr[i]));
        boolean[] visited = new boolean[n];
        int totalSwaps = 0;
        
        for(int i = 0; i<n; i++) {
            if(visited[i] || map[i] == i)
                continue;

            int cycleSize = 0;
            int j = i;
            while(!visited[j]) {
                visited[j] = true;
                j = map[i];
                cycleSize++;
            }
            if(cycleSize > 0)
                totalSwaps += (cycleSize - 1);
        }
        return totalSwaps;
    }

    public static void main(String[] args) {
        //Q1
        // System.out.println(inversionCount(new int[]{8, 4, 2, 1}));

        //Q2
        int[] arr = {6, 5, 3, 2, 8, 10, 9};
        // dutchFlag(arr);
        // System.out.println(Arrays.toString(arr));

        //Q3
        // System.out.println(kLargestQuickSelect(arr, 2));

        //Q4
        // System.out.println(reversePairs(arr));

        //Q5
        kSortedArray(arr, 3);
        System.out.println(Arrays.toString(arr));

    }
}
