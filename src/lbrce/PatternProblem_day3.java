package lbrce;

public class PatternProblem_day3 {
    //Question 1
    public static void leftAlignRightTriangle(int n) {
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                System.out.print("*");
                if(j < i)
                    System.out.print(" ");
            }
            if(i < n)
                System.out.println();
        }
    }

    //Question 2:
    public static void invertedLeftAlignRightTriangle(int n) {
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n-i+1; j++) {
                System.out.print("*");
                if(j < n-i+1)
                    System.out.print(" ");
            }
            if(i < n)
                System.out.println();
        }
    }

    //Question 3: Centered Pyramids
    public static void centeredPyramid(int n) {
        for(int i = 1; i <= n; i++) {
            //spaces = N - i
            for(int j = 1; j <= n -i; j++) {
                System.out.print(" ");
            }
            //starts = 2 x i - 1 (centered pyramid)
            // k < i (isoceles triangle)
            for(int k = 1; k <= (2*i-1); k ++) {
                System.out.print("*");
                if(k < (2*i-1))
                    System.out.print(" ");
            }
            if(i < n)
                System.out.println();
        }
    }

    //Question 4: (top pyramid + inverted pyramid, 2N-1 rows
    public static void diamondPattern(int n) {
        //total rows - 2 * n - 1
        int totalRows = 2*n-1;
        for(int i = 1; i <= totalRows; i++) {
            // spaces = Math.abs(N - i)
            int spaces = Math.abs(n - i);
            int stars = 2*(n-spaces) -1;

            //print spaces
            for(int j = 1; j <= spaces; j++)
                System.out.print(" ");
            //print starts
            for(int k = 1; k <= stars; k++) {
                System.out.print("*");
                if(k < stars)
                    System.out.print(" ");
            }
            if(i < totalRows)
                System.out.println();

        }
    }

    //Optimized way
    //Only applicable for java 11+
    public static void optmizedDiamond(int n) {
        //total rows - 2 * n - 1
        int totalRows = 2*n-1;
        for(int i = 1; i <= totalRows; i++) {
            // spaces = Math.abs(N - i)
            int spaces = Math.abs(n - i);
            int stars = 2*(n-spaces) -1;

            String spaceStr = " ".repeat(spaces);
            String starStr = "* ".repeat(stars).trim();

            System.out.print(spaceStr + starStr);

            if(i < totalRows)
                System.out.println();

        }
    }

    //Question 5: Floyd Triangle
    public static void floydTraingle(int n) {
        int counter = 1;
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= i; j++) {
                System.out.print(counter);
                if(j < i)
                    System.out.print(" ");
                counter++;
            }
            if(i < n)
                System.out.println();
        }
    }

    //Question 6: PASCAL TRIANGLE
    public static void pascalTriangle(int n) {
        for(int i = 0; i<n; i++) {
            //print spaces - n-i-1
            System.out.print(" ".repeat(n-i-1));

            int val = 1;
            for(int j = 0; j <= i; j++) {
                System.out.print(val);
                if(j < i)
                    System.out.print(" ");

                val = val * (i-j)/(j+1);
            }
            if( i < n-1)
                System.out.println();
        }
    }

    //Question 7: hollow rectangle
    public static void hollowRectangle(int r, int c) {
        String boundaryRow = "* ".repeat(c).trim();

        String middleRow = 
        c > 1 ? "* " +  "  ".repeat(c - 2) + "*" : "*";

        for(int i = 1; i <= r; i++) {
            if(i == 1 || i == r)
                System.out.print(boundaryRow);
            else
                System.out.print(middleRow);

            if(i < r)
                System.out.println();
        }
    }
    
    //Question 8: Spiral Matrix
    public static void spiralMatrix(int n) {
        if(n <= 0) return;
        int[][] matrix = new int[n][n];
        int value = 1;
        int top = 0, bottom = n-1, left = 0, right = n-1;

        while(value <= n*n) {
            //traverse right
            for(int i = left; i <= right; i++)
                matrix[top][i] = value++;
            top++;

            //traverse down
            for(int i = top; i <= bottom; i++)
                matrix[i][right] = value++;
            right--;

            //traverse left
            for(int i = right; i >= left; i--)
                matrix[bottom][i] = value++;
            bottom--;

            //traverse Up
            for(int i = bottom; i >= top; i--)
                matrix[i][left] = value++;
            left++;
        }

        //print array
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++) {
                System.out.printf("%3d", matrix[i][j]);

                if(j < n-1)
                    System.out.print(" ");
            }
            if(i < n-1)
                System.out.println();
        }
    }

    //Question 9: Zig-Zag Diagnol
    public static void zigZagDiagnol(int n) {
        int[][] matrix = new int[n][n];
        int value = 1;

        for(int d = 0; d < 2*n-1; d++) {
            if(d % 2 == 0) {
                //Even diagnol - Go Up/right
                int row = (d < n) ? d: n - 1;
                int col = d - row;
                while(row >= 0 && col < n) {
                    matrix[row][col] = value++;
                    row--;
                    col++;
                }
            }
            else {
                //Odd diagnol: Go Down-Left
                int col = (d < n) ? d : n - 1;
                int row = d - col;
                while(row < n && col >= 0) {
                    matrix[row][col] = value++;
                    row++;
                    col--;
                }
            }
        }

        //print array
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                System.out.printf(
                    "%3d", matrix[i][j]);
                if(j < n - 1)
                    System.out.print(" ");
            }
            if(i < n-1)
                System.out.println();
        }
    }
    public static void main(String[] args) {
        // leftAlignRightTriangle(5);
        // invertedLeftAlignRightTriangle(5);
        // centeredPyramid(5);
        // diamondPattern(5);
        // optmizedDiamond(3);
        // floydTraingle(3);
        // pascalTriangle(8);
        // hollowRectangle(3, 4);
        // spiralMatrix(3);
        zigZagDiagnol(3);
    }
}
