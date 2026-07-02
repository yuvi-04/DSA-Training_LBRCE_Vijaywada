package lbrce;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Backtracking_day15 {
    //Q1: permutations of a given array of distinct integers.
    //leetcode 46
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Deque<Integer> path = new ArrayDeque<>();
        boolean[] used = new boolean[nums.length];
        backtrackPermute(nums, used, path, result);
        return result;
    }

    private static void backtrackPermute(int[] nums, boolean[] used, Deque<Integer> path, List<List<Integer>> result) {
        //Base Case
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            //Skip if already in our current path
            if(used[i]) continue;
            //step1: Do
            used[i] = true;
            path.add(nums[i]);
            //Step2: Recurse
            backtrackPermute(nums, used, path, result);
            //step3: Backtrack
            path.removeLast();
            used[i] = false;
        }
    }

    //Q2: power set (all subsets)
    //leetcode 78
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubset(nums, 0, new ArrayDeque<>(), result);
        return result;
    }
    private static void backtrackSubset(int[] nums, int index, Deque<Integer> path, List<List<Integer>> result) {
        //Base Case: Add current subset to result
        result.add(new ArrayList<>(path));

        for (int i = index; i < nums.length; i++) {
            path.addLast(nums[i]);
            //Pass i+1 to move forward to second element
            backtrackSubset(nums, i+1, path, result);
            path.removeLast();
        }
    }
    //Q3: combinations of numbers from 1 to N -> Target (No repetition allowed)
    //leetcode 216, 39
    public static List<List<Integer>> combinationSum1ToN(int n, int target) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackComb1ToN(n, target, 1, new ArrayDeque<>(), result);
        return result;
    }
    private static void backtrackComb1ToN(int n, int remain, int start, Deque<Integer> path, List<List<Integer>> result) {
        //Base Case
        if(remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i <= n; i++) {
            //Optimization: Pruning if the number is greater than the remaining sum
            if(i > remain) break;
            path.addLast(i);
            backtrackComb1ToN(n, remain-i, i+1, path, result);
            path.removeLast();
        }
    }

    //Q4: Combination Sum II (Duplicates are allowed)
    //leetcode 40
    public static List<List<Integer>> combinationSum2(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        //Sorting is mandaotry for duplicates
        Arrays.sort(nums);
        backtrackCombSum2(nums, target, 0, new ArrayDeque<>(), result);
        return result;
    }
    private static void backtrackCombSum2(int[] arr, int remain, int start, Deque<Integer> path, List<List<Integer>> result) {
        //Base Case
        if(remain == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < arr.length; i++) {
            if(arr[i] > remain) break;
            //Skip duplicates at the same depth
            if(i > start && arr[i] == arr[i-1]) continue;

            path.addLast(arr[i]);
            backtrackCombSum2(arr, remain-arr[i], i+1, path, result);
            path.removeLast();
        }
    }

    //Q5: valid combinations of N pairs of balanced parentheses
    //leetcode 22
    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        //String Builder is much faster than string concatenation
        backtrackParenthesis(n, 0, 0, new StringBuilder(), result);
        return result;
    }
    private static void backtrackParenthesis(int n, int open, int close, StringBuilder path, List<String> result) {
        if(path.length() == n*2) {
            result.add(path.toString());
            return;
        }
        //Step1: Can add opening ( if we have not used all N
        if(open < n) {
            path.append("{");
            backtrackParenthesis(n, open+1, close, path, result);
            path.deleteCharAt(path.length() - 1); // Undo
        }
        if(close < open) {
            path.append("}");
            backtrackParenthesis(n, open, close+1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    //Q6: check if a target word exists by traversing adjacent cells. 
    //leetcode 79 Word Search
    public static boolean wordSearch(char[][] board, String word) {
        for(int r = 0; r < board.length; r++) {
            for(int c = 0; c < board[0].length; c++) {
                if(board[r][c] == word.charAt(0) && dfsWordSearch(board, r, c, 0, word))
                    return true;
            }
        }
        return false;
    }
    private static boolean dfsWordSearch(char[][] board, int r, int c, int index, String word) {
        if(index == word.length()) return true;
        if(r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != word.charAt(index))
            return false;

        char temp = board[r][c];
        board[r][c] = '#';
        boolean found = 
            dfsWordSearch(board, r+1, c, index+1, word) ||
            dfsWordSearch(board, r-1, c, index+1, word) ||
            dfsWordSearch(board, r, c+1, index+1, word) ||
            dfsWordSearch(board, r, c-1, index+1, word);
        //Backtracking
        board[r][c] = temp;
        return found;
    }

    //Q7: Letter combination of a phone number
    //leetcode 17
    //Arrays lookups are much faster than hashmap
    private static final String[] KEYPAD = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    public static List<String> letterCombination(String digits) {
        List<String> result = new ArrayList<>();
        trackLetters(digits, 0, new StringBuilder(), result);
        return result;
    }
    private static void trackLetters(String digits, int index, StringBuilder path, List<String> result) {
        if(index == digits.length()) {
            result.add(path.toString());
            return;
        }
        String possibleLetters = KEYPAD[digits.charAt(index) - '0'];
        for (char letter : possibleLetters.toCharArray()) {
            path.append(letter);
            trackLetters(digits, index+1, path, result);
            path.deleteCharAt(path.length() - 1);
        }
    }

    //Q8: N-Queens Problem
    //leetcode 51
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        for(char[] row : board) Arrays.fill(row, '.');

        //O(1) space tracking for columns and diagonals
        boolean[] cols = new boolean[n];
        boolean[] diag1 = new boolean[2*n]; // r + c
        boolean[] diag2 = new boolean[2*n]; // r - c + n
        backtrackNQueens(board, 0, cols, diag1, diag2, result);
        return result;
    }
    private static void backtrackNQueens(char[][] board, int row, boolean[] cols, boolean[] diag1, boolean[] diag2, List<List<String>> result) {
        int n = board.length;
        if (row == n) {
            List<String> validBoard = new ArrayList<>();
            for(char[] r : board) validBoard.add(new String(r));
            result.add(validBoard);
            return;
        }
        for(int col = 0; col < n; col++) {
            int d1 = row + col;
            int d2 = row - col + n;
            //Skip if Queen is under attack
            if(cols[col] || diag1[d1] || diag2[d2]) continue;

            //Place queen
            board[row][col] = 'Q';
            cols[col] = diag1[d1] = diag2[d2] = true;

            backtrackNQueens(board, row+1, cols, diag1, diag2, result);

            //Backtrack
            board[row][col] = '.';
            cols[col] = diag1[d1] = diag2[d2] = false;
        }
    }

    //Q9: Sudoku Solver 9x9
    //leetcode 37
    public static void solveSudoku(char[][] board) {
        backtrackSudoku(board);
    }
    private static boolean backtrackSudoku(char[][] board) {
        for(int r = 0; r < 9; r++) {
           for(int c = 0; c < 9; c++) {
                if(board[r][c] == '.') {
                    for(char num = '1'; num <= '9'; num++) {
                        if(isValidSudokuMove(board, r, c, num)) {
                            board[r][c] = num; //Do
                            //If a recursive call solves the board return true
                            if(backtrackSudoku(board)) return true;
                            board[r][c] = '.'; //Backtrack
                        }
                    }
                    return false; //tried 1-9, none worked must go back
                }
            } 
        }
        return true; //No empty cells left, board is complete
    }

    private static boolean isValidSudokuMove(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if(board[i][col] == c) return false; //checks col
            if(board[row][i] == c) return false; //checks row
            if(board[3*(row/3)+i/3][3*(col/3)+i%3] == c) return false; //checks 3x3 grid
        }
        return true;
    }

    public static void main(String[] args) {
        //Q1 O(N*N!)
        System.out.println(permute(new int[]{1,2,3,4}));

        //Q2 O(N*2^N)
        System.out.println(subsets(new int[]{1,2,3}));

        //Q3
        System.out.println(combinationSum1ToN(10, 7));

        //Q4
        System.out.println(combinationSum2(new int[]{2,5,2,1,2}, 6));

        //Q5
        System.out.println(generateParenthesis(3));

        //Q6
        char[][] board = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        String word = "ABCB";
        System.out.println(wordSearch(board, word));

        //Q7
        System.out.println(letterCombination("234"));

        //Q8
        List<List<String>> queens = solveNQueens(8);
        IntStream.range(0, queens.size())
            .forEach(i -> {
            System.out.println("Solution " + (i+1) + ":");
            queens.get(i).forEach(row -> 
                System.out.println(" " + row));
        });

        //Q9
        char[][] sudokuBoard = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        solveSudoku(sudokuBoard);
        // Print the solved Sudoku board
        IntStream.range(
            0, sudokuBoard.length).forEach(
                r -> {
                    String formattedRow = IntStream.range(
                        0, sudokuBoard[r].length)
                        .mapToObj(c -> String.valueOf(sudokuBoard[r][c]))
                        .collect(Collectors.joining(" "))
                        .replaceAll("((?:\\S\\s){3})", "$1| ");
                    System.out.println(" " + formattedRow.substring(
                        0, formattedRow.length() - 2));
                    
                    if((r+1) % 3 == 0 && r<8)
                        System.out.println("-------+-------+-------");
                });
    }
}
