import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day4Part2 {
    public static void main(String[] args) throws Exception {
        // Create a variable to track the total rolls of paper that can be removed
        int totalRemoved = 0;

        // Open the input file
        List<String> original = Files.readAllLines(Paths.get("AoC2025/inputs/Day4Input.txt"));

        // Convert to mutable char grid (since List<String> is immutable)
        int rows = original.size();
        int cols = original.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            grid[r] = original.get(r).toCharArray();
        }

        while (true) {
            // Track how many rolls can be removed in this round
            int removedThisRound = 0;

            // Track positions to remove (so we remove all at once)
            boolean[][] remove = new boolean[rows][cols];

            // Iterate through every cell in the grid
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    char cell = grid[r][c];

                    if (cell == '@') {
                        // Check the eight adjacent positions
                        boolean canAccess = checkPositions(grid, r, c);

                        if (canAccess) {
                            remove[r][c] = true;
                            removedThisRound++;
                        }
                    }
                }
            }

            // Stop if we can't remove anything else
            if (removedThisRound == 0) {
                break;
            }

            // Remove all accessible rolls
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (remove[r][c]) {
                        grid[r][c] = '.';
                    }
                }
            }

            totalRemoved += removedThisRound;
        }

        // Print the number of rolls of paper that can be accessed & removed
        System.out.println(totalRemoved);
    }

    public static boolean checkPositions(char[][] grid, int row, int col) {
        // Create a variable to track the number of rolls of paper in the eight adjacent positions
        int rolls = 0;

        // Left, Right, Up, Down, Diagonal Up Left, Diagonal Up Right, Diagonal Down Left, Diagonal Down Right
        int[][] directions = {
                {0, -1}, {0, 1}, {-1, 0}, {1, 0},
                {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };

        int rows = grid.length;
        int cols = grid[0].length;

        // Check each direction (first check if the direction is in bound)
        for (int[] d : directions) {
            int nr = row + d[0];
            int nc = col + d[1];

            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                if (grid[nr][nc] == '@') {
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
