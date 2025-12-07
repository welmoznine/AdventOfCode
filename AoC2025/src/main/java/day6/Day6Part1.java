import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Day6Part1 {
    public static void main(String[] args) throws IOException {
        // Create a variable to track grand total found by adding together all of the answers to the individual problems
        long ans = 0;

        // Read all lines from the input file
        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day6Input.txt"));

        // Number of rows and maximum number of columns
        int rows = input.size();
        int cols = 0;
        for (String line : input) {
            if (line.length() > cols) {
                cols = line.length();
            }
        }

        // Convert the input into a 2D char grid
        char[][] grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = input.get(r);
            for (int c = 0; c < cols; c++) {
                // If the column exceeds the line length, use a space
                if (c < line.length()) {
                    grid[r][c] = line.charAt(c);
                } else {
                    grid[r][c] = ' ';
                }
            }
        }

        // Scan LEFT-TO-RIGHT, column by column, to find and evaluate each problem
        // Problems are separated by a column of ONLY spaces
        int c = 0; // Current column index
        while (c < cols) {

            // Skip columns that are blank
            if (isBlankColumn(grid, rows, c)) {
                c++;
                continue;
            }

            // Start of a problem block
            int start = c;
            while (c < cols && !isBlankColumn(grid, rows, c)) {
                c++;
            }
            int end = c - 1; // inclusive end of the problem block

            // Evaluate the numbers and operator in this problem block
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
     */
    private static long evaluateProblem(char[][] grid, int rows, int startCol, int endCol) {
        List<Long> nums = new ArrayList<>();

        // Extract numbers from each row (except the last row, which has the operator)
        for (int r = 0; r < rows - 1; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = startCol; c <= endCol; c++) {
                char ch = grid[r][c];
                if (Character.isDigit(ch)) {
                    sb.append(ch);
                } else {
                    if (sb.length() > 0) {
                        break; // stop at the first non-digit
                    }
                }
            }
            if (sb.length() > 0) {
                nums.add(Long.parseLong(sb.toString()));
            }
        }

        // Find the operator in the last row of the block
        char op = '+'; // default
        for (int c = startCol; c <= endCol; c++) {
            char ch = grid[rows - 1][c];
            if (ch == '+' || ch == '*') {
                op = ch;
                break;
            }
        }

        // Compute the result
        long result;
        if (op == '+') {
            result = 0;
            for (long x : nums) {
                result += x;
            }
        } else { // op == '*'
            result = 1;
            for (long x : nums) {
                result *= x;
            }
        }

        return result;
    }
}
