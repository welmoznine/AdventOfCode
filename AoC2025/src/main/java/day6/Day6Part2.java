import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Day6Part2 {
    public static void main(String[] args) throws IOException {
        // Create a variable to track the grand total found by adding
        // together all of the answers to the individual problems
        long ans = 0;

        // Read all lines from the input file
        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day6Input.txt"));

        // Determine the number of rows and maximum number of columns
        int rows = input.size();
        int cols = 0;
        for (String line : input) {
            if (line.length() > cols) {
                cols = line.length();
            }
        }

        // Convert the input into a 2D char grid
        // Shorter lines are padded with spaces
        char[][] grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = input.get(r);
            for (int c = 0; c < cols; c++) {
                if (c < line.length()) {
                    grid[r][c] = line.charAt(c);
                } else {
                    grid[r][c] = ' ';
                }
            }
        }

        // Scan RIGHT-TO-LEFT to find each problem block
        // Problems are separated by a full column of spaces
        // Each block:
        //   - Contains numbers in columns (most significant digit at top)
        //   - The bottom row contains the operator (+ or *)
        int c = 0;
        while (c < cols) {
            // Skip blank columns (they separate problems)
            if (isBlankColumn(grid, rows, c)) {
                c++;
                continue;
            }

            // Start of a problem block
            int start = c;
            while (c < cols && !isBlankColumn(grid, rows, c)) {
                c++;
            }
            int end = c - 1; // inclusive end of block

            // Evaluate the problem block (reading numbers column by column)
            ans += evaluateProblem(grid, rows, start, end);
        }

        // Print the final grand total
        System.out.println(ans);
    }

    /**
     * Returns true if the entire column is made of spaces, false otherwise
     */
    private static boolean isBlankColumn(char[][] grid, int rows, int col) {
        for (int r = 0; r < rows; r++) {
            if (grid[r][col] != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Evaluates a single problem block defined by columns [startCol .. endCol]
     *
     * For Part 2:
     *   - Each number is in its own column
     *   - Digits are read from top to bottom in each column
     *   - The bottom row contains the operator (+ or *)
     */
    private static long evaluateProblem(char[][] grid, int rows, int startCol, int endCol) {
        List<Long> nums = new ArrayList<>();

        // Extract each column's number (digits are read top-to-bottom in each column)
        for (int c = startCol; c <= endCol; c++) {
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < rows - 1; r++) { // last row is operator
                char ch = grid[r][c];
                if (Character.isDigit(ch)) {
                    sb.append(ch);
                }
            }
            if (sb.length() > 0) {
                nums.add(Long.parseLong(sb.toString()));
            }
        }

        // The final row contains the operator: '+' or '*'
        char op = '+';
        for (int c = startCol; c <= endCol; c++) {
            char ch = grid[rows - 1][c];
            if (ch == '+' || ch == '*') {
                op = ch;
                break;
            }
        }

        // Compute the result of this problem using the operator
        long result;
        if (op == '+') { // If the operator == '+'
            result = 0;
            for (long x : nums) {
                result += x;
            }
        } else { // If the operator == '*'
            result = 1;
            for (long x : nums) {
                result *= x;
            }
        }

        return result;
    }
}
