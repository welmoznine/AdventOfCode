import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day4Part1 {
    public static void main(String[] args) throws Exception {
        // Create a variable to track the rolls of paper that can be accessed by a forklift
        int ans = 0;

        // Open the input file
        List<String> grid = Files.readAllLines(Paths.get("AoC2025/inputs/Day4Input.txt"));

        // Number of rows in the grid
        int rows = grid.size();

        // Number of columns in the grid (length of first row)
        int cols = grid.get(0).length();

        // Iterate through every cell in the grid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Get the current cell (@ = roll of paper, . = empty)
                char cell = grid.get(r).charAt(c);

                // If the current cell in the grid is a roll of paper
                if (cell == '@') {
                    // Check the eight adjacent positions
                    boolean canAccess = checkPositions(grid, r, c);

                    // If the forklift can access this roll, increment the count
                    if (canAccess) {
                        ans += 1;
                    }
                }
            }
        }

        // Print the number of rolls of paper that can be accessed by a forklift
        System.out.println(ans);
    }

    public static boolean checkPositions(List<String> grid, int row, int col) {
        // Create a variable to track the number of rolls of paper in the eight adjacent positions
        int rolls = 0;

        // Left, Right, Up, Down, Diagonal Up Left, Diagonal Up Right, Diagonal Down Left, Diagonal Down Right
        int[][] directions = {
                {0, -1},   // Left
                {0, 1},    // Right
                {-1, 0},   // Up
                {1, 0},    // Down
                {-1, -1},  // Diagonal Up Left
                {-1, 1},   // Diagonal Up Right
                {1, -1},   // Diagonal Down Left
                {1, 1}     // Diagonal Down Right
        };

        int rows = grid.size();
        int cols = grid.get(0).length();

        // Check each direction (first check if the direction is in bound)
        for (int[] d : directions) {
            int nr = row + d[0];
            int nc = col + d[1];

            // Bounds check
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                if (grid.get(nr).charAt(nc) == '@') {
                    rolls++;

                    // If there are four or more rolls, then the forklift cannot access the current roll so break early
                    if (rolls >= 4) {
                        return false;
                    }
                }
            }
        }

        // If there are fewer than four in the eight adjacent positions, then the forklift can access so return True
        return true;
    }
}
