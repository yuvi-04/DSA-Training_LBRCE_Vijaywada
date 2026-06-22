package lbrce;

import java.util.Arrays;

public class Search_day7 {
    //Q1: find target value in sorted array
    public static int searchValue(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target) return mid;
            else if(nums[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;

        // O(log n)
        // int result = Arrays.binarySearch(nums, target);
        // return result >= 0 ? result : -1;
    }

    public static int[] searchRange(int[] nums, int target) {
        int first = findBound(nums, target, true);
        if(first == -1) return new int[]{-1, -1};
        int last = findBound(nums, target, false);
        return new int[]{first, last};
    }
    private static int findBound(int[] nums, int target, boolean isFirst) {
        int low = 0, high = nums.length - 1, ans = -1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target) {
                ans = mid;
                if(isFirst) high = mid - 1;
                else low = mid + 1;
            }
            else if(nums[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return ans;
    }

    //Q3: Find min Element in rotated array
    public static int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        while(low < high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] > nums[high]) low = mid + 1;
            else high = mid;
        }
        return nums[low];
    }

    //Q4: Search in Rotated Array
    public static int searchRotated(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target) return mid;

            //check if left half is sorted
            if(nums[low] <= nums[mid]) {
                if(target >= nums[low] && target < nums[mid]) {
                    high = mid - 1;
                } else low = mid + 1;
            }
            else { // right half must be sorted
                if(target > nums[mid] && target <= nums[high]) {
                    low = mid + 1;
                } else high = mid - 1;
            }
        }
        return -1;
    }

    //Q5: find Sqrt using Binary Search
    public static int sqrt(int x) {
        if(x == 0) return 0;
        int low = 1, high = x, ans = 1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(mid <= x / mid) {
                ans = mid;
                low = mid + 1;
            } else high = mid -1;
        }
        return ans;
    }

    //Q6:  Book Allocation Problem
    public static int findPages(int[] A, int M, int N) {
        if(M > N) return -1;
        int low = 0, high = 0;

        for(int book : A) {
            low = Math.max(low, book);
            high += book;
        }

        int ans = -1;
        while(low <= high) {
            int mid = low + (high - low) / 2;
            if(isPossible(A, M, mid)) {
                ans = mid;
                high = mid - 1;
            } else low = mid + 1;
        }
        return ans;
    }
    private static boolean isPossible(int[] A,
        int students, int maxPages) {
            int studentsRequired = 1;
            int currentPages = 0;
            
            for(int book : A) {
                if(currentPages + book > maxPages) {
                    studentsRequired++;
                    currentPages = book;
                    if(studentsRequired > students) return false;
                }
                else currentPages += book;
            }
            return true;
    }

    //Q7: Aggressive cows
    public static int aggressiveCows(int[] stalls, int k) {
        Arrays.sort(stalls);
        int n = stalls.length;
        int low = 1, high = stalls[n-1] - stalls[0], ans = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if(canPlaceCows(stalls, k, mid)) {
                ans = mid;
                //maximizing the minimum distance
                low = mid + 1;
            } else high = mid - 1;
        }
        return ans;
    }
    private static boolean canPlaceCows(int[] stalls, int cows, int dist) {
        int count = 1;
        int lastPlaced = stalls[0];

        for(int i = 1; i < stalls.length; i++) {
            if(stalls[i] - lastPlaced >= dist) {
                count++;
                lastPlaced = stalls[i];
                if(count >= cows) return true;
            }
        }
        return false;
    }

    //Q8: Painter's Partition
    public static int painterPartition(int[] boards, int k) {
        int n = boards.length;

        //Edge Case:
        if(k > n) {
            int maxBoard = 0;
            for(int board: boards) {
                maxBoard = Math.max(maxBoard, board);
            }
            return maxBoard;
        }

        int low = 0;
        int high = 0;

        for(int board : boards) {
            low = Math.max(low, board);
            high += board;
        }
        int ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            if(isPossibleboard(boards, k, mid)) {
                ans = mid;
                high = mid - 1;
            } else low = mid + 1;
        }
        return ans;
    }
    private static boolean isPossibleboard(int[] boards, int painters, int maxTimeLimit) {
        int painterRequired = 1;
        int currentBoardSum = 0;

        for(int board : boards) {
            if(currentBoardSum + board > maxTimeLimit) {
                painterRequired++;
                currentBoardSum = board;
                if(painterRequired > painters)
                    return false;
            } else currentBoardSum += board;

        }
        return true;
    }

    public static void main(String[] args) {
        //Q2
        // int[] arr = searchRange(
        //     new int[]{5, 7, 7, 8, 8, 10}, 6);
        // System.out.println(Arrays.toString(arr));

        //Q3
        // System.out.println(findMin(
        //     new int[]{4, 5, 6, 7, 0, 1, 2}));

        //Q4
        // System.out.println(searchRotated(
        //     new int[]{4, 5, 6, 7, 0, 1, 2}, 9));

        //Q5
        // System.out.println(sqrt(40));

        //Q6
        // System.out.println(findPages(
        //     new int[]{12, 34, 67, 90}, 2, 4));

        //Q7
        System.out.println(aggressiveCows(
            new int[]{1, 2, 4, 8, 9}, 3));
    }
}
