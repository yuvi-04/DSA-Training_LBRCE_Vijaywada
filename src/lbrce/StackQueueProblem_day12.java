package lbrce;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.PriorityQueue;

public class StackQueueProblem_day12 {
    //Q1: Running median of stream of integers
    //leetcode: 295. Find Median from Data Stream
    public static void runningMedian(int[] stream) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for(int num : stream) {
            if(maxHeap.isEmpty() || num <= maxHeap.peek())
                maxHeap.add(num);
            else minHeap.add(num);

            //Step2: Balance heap if size difference > 1
            if(maxHeap.size() > minHeap.size() + 1)
                minHeap.add(maxHeap.poll());
            else if(minHeap.size() > maxHeap.size())
                maxHeap.add(minHeap.poll());

            //Step3: calculate median
            double median;
            if(maxHeap.size() == minHeap.size())
                median = (maxHeap.peek() + minHeap.peek()) / 2.0;
            else median = maxHeap.peek();

            System.out.println("Inserted: " + num + " -> Current Median: " + median);
        }
    }

    //Q2:  Decode a string encoded as k[encoded_string]
    //leetcode 394
    public static String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> stringStack = new ArrayDeque<>();
        StringBuilder currentString = new StringBuilder();
        int k = 0;

        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                k = k*10 + (ch - '0');
            }
            else if(ch == '[') {
                countStack.push(k);
                stringStack.push(currentString);
                currentString = new StringBuilder();
                k = 0;
            }
            else if(ch == ']') {
                StringBuilder decodingSegment = currentString;
                currentString = stringStack.pop();
                int repeatCount = countStack.pop();
                while (repeatCount-- > 0) {
                    currentString.append(decodingSegment);
                }
            }
            else currentString.append(ch);
        }
        return currentString.toString();
    }

    //Q3: find the minimum number of platforms
    //leetcode 253
    public static int findPlatforms(int[] arr, int[] dept, int n) {
        //Array to track net platform modification
        int[] timeline = new int[2362];

        //Step1: Record events in linear time
        for(int i = 0; i < n; i++) {
            timeline[arr[i]]++; //increment when the train arrives
            timeline[dept[i] + 1]--; //decrement the minute after train departs
        }
        //step2:
        int maxPlatforms = 0;
        int currentPlatforms = 0;
        //step3: Since the array size is fixed, this will run in O(1)
        for(int time = 0; time < 2361; time++) {
            currentPlatforms += timeline[time];
            maxPlatforms = Math.max(maxPlatforms, currentPlatforms);
        }
        return maxPlatforms;
    }

    //Q5: Sliding Window Minimum
    //leetcode 239 -> Maximum
    public static int[] minSlidingWindow(int[] nums, int k) {
        if(nums == null || nums.length == 0) throw new IllegalArgumentException("CHECK ARRAY");

        int n = nums.length;
        int[] result = new int[n-k+1];
        int ri = 0; //Result array index
        Deque<Integer> deque = new ArrayDeque<>();

        for(int i = 0; i<n; i++) {
            //step1: remove indices that are out of window
            if(!deque.isEmpty() &&
                deque.peekFirst() <= i-k) {
                    deque.pollFirst();
            }
            //Step 2: Remove elements larger than current element from back
            while (!deque.isEmpty() && nums[deque.peekLast()] >= nums[i]) {
                deque.pollLast();
            }
            //step3: add the current index
            deque.offerLast(i);

            //step4: Record output of window minimum
            if(i >= k-1)
                result[ri++] = nums[deque.peekFirst()];
        }
        return result;
    }
    public static void main(String[] args) {
        //Q1
        // runningMedian(new int[]{5, 15, 1, 3});
        
        //Q2
        // System.out.println(decodeString("3[a2[c]]"));

        //Q3
        // int[] arrivals = {900, 940, 950, 1100, 1500, 1800};
        // int[] departures = {910, 1200, 1120, 1130, 1900, 2000};
        // System.out.println(findPlatforms(arrivals, departures, arrivals.length));

        //Q5
        System.out.println(Arrays.toString(
            minSlidingWindow(new int[]{4, 3, 2, 1, 5, 7}, 6)
        ));
    }
}
