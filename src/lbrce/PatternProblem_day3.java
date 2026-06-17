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
    public static void main(String[] args) {
        // leftAlignRightTriangle(5);
        // invertedLeftAlignRightTriangle(5);
        // centeredPyramid(5);
        // diamondPattern(5);
        // optmizedDiamond(3);
        floydTraingle(3);
    }
}
