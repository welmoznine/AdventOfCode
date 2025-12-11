// Skipped, couldn't solve without AI and the adventofcode subreddit :(
//
// REVIEW NOTES:
// This solution generalizes the "lights/buttons" problem to integer counters (joltage machines):
// 1. Represent the problem as a linear system A*x = b:
//      - Rows = counters (lights or joltage counters)
//      - Columns = buttons
//      - A[i][j] = 1 if button j affects counter i
//      - b = target values for each counter
// 2. Reduced Row Echelon Form (RREF):
//      - Convert matrix to RREF to identify pivot and free variables
//      - Pivot variables are dependent, free variables can be chosen arbitrarily
// 3. Integer minimization with free variables:
//      - Particular solution: set free variables to 0
//      - Explore all combinations of free variables (DFS) to find minimal sum of button presses
//      - Prune invalid solutions (negative values or non-integers)
// 4. Key concepts to review:
//      - Linear algebra: pivot variables, free variables, back-substitution
//      - RREF and Gaussian elimination for integer systems
//      - DFS to explore combinations of free variables
//      - Integer solutions in linear systems (rounding and validation)
// 5. Practical takeaways:
//      - Converts combinatorial problems into linear algebra
//      - Efficiently handles small number of free variables for optimization
//      - Demonstrates systematic approach to minimize total button presses

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

/**
 * Day 10 Part 2 - Solve minimum number of button presses for generalized "joltage machines".
 *
 * Each machine has:
 *  - Buttons: each button affects certain counters (like lights)
 *  - Target joltage: required value for each counter
 *
 * Goal: Find the minimum total number of button presses to satisfy all counters.
 */
public class Day10Part2 {

    /**
     * Represents a single machine
     */
    static class Machine {
        List<int[]> buttons;  // Each button affects a list of counters
        int[] target;         // The target value for each counter

        Machine(List<int[]> buttons, int[] target) {
            this.buttons = buttons;
            this.target = target;
        }
    }

    public static void main(String[] args) throws IOException {
        // 1. Load all machines from input file
        List<Machine> machines = load("AoC2025/inputs/Day10Input.txt");

        // 2. Solve each machine and accumulate total presses
        long total = 0;
        for (Machine m : machines) {
            total += solveMachine(m);
        }

        // 3. Output the result
        System.out.println("Part 2 Answer = " + total);
    }

    /**
     * Load machines from the input file
     */
    static List<Machine> load(String filename) throws IOException {
        List<Machine> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // Skip blank lines

                // --- Parse buttons: (...) groups ---
                List<int[]> buttons = new ArrayList<>();
                int idx = 0;
                while ((idx = line.indexOf('(', idx)) != -1) {
                    int end = line.indexOf(')', idx);
                    String inside = line.substring(idx + 1, end).trim();
                    if (!inside.isEmpty()) {
                        String[] split = inside.split(",");
                        int[] arr = new int[split.length];
                        for (int i = 0; i < split.length; i++) arr[i] = Integer.parseInt(split[i]);
                        buttons.add(arr);
                    }
                    idx = end + 1;
                }

                // --- Parse target joltage: {...} ---
                int sb = line.indexOf('{');
                int se = line.indexOf('}');
                String inside = line.substring(sb + 1, se).trim();
                String[] s2 = inside.split(",");
                int[] target = new int[s2.length];
                for (int i = 0; i < s2.length; i++) target[i] = Integer.parseInt(s2[i]);

                // Add new machine to list
                list.add(new Machine(buttons, target));
            }
        }

        return list;
    }

    /**
     * Solve a single machine to find minimum button presses
     */
    static long solveMachine(Machine m) {
        int rows = m.target.length;  // Number of counters
        int cols = m.buttons.size();  // Number of buttons

        // Build coefficient matrix A
        int[][] A = new int[rows][cols];
        for (int j = 0; j < cols; j++) {
            for (int r : m.buttons.get(j)) {
                A[r][j] = 1; // Button j affects counter r
            }
        }

        int[] b = m.target;

        // Solve the linear system using RREF (reduced row echelon form)
        LinearSystem sys = new LinearSystem(A, b);
        sys.solveRREF();

        // Find the integer solution with minimum total presses
        return sys.minimizeIntegerSolution();
    }

    /**
     * Represents a linear system A*x = b for integer minimization
     */
    static class LinearSystem {
        double[][] M;          // Augmented matrix [A | b]
        int rows, cols;        // Rows = counters, Cols = buttons
        boolean[] isPivotCol;  // True if column is pivot
        List<Integer> freeCols; // List of free variable columns

        LinearSystem(int[][] A, int[] b) {
            rows = A.length;
            cols = A[0].length;
            M = new double[rows][cols + 1]; // extra column for b
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) M[r][c] = A[r][c];
                M[r][cols] = b[r];
            }
        }

        /**
         * Convert the matrix to Reduced Row Echelon Form (RREF)
         */
        void solveRREF() {
            int pivotRow = 0;
            isPivotCol = new boolean[cols];

            for (int c = 0; c < cols && pivotRow < rows; c++) {
                // Find pivot in column c
                int sel = -1;
                for (int r = pivotRow; r < rows; r++) {
                    if (Math.abs(M[r][c]) > 1e-9) { sel = r; break; }
                }
                if (sel == -1) continue; // No pivot in this column

                // Swap pivot row with current row
                double[] tmp = M[pivotRow]; M[pivotRow] = M[sel]; M[sel] = tmp;

                // Normalize pivot to 1
                double div = M[pivotRow][c];
                for (int cc = c; cc <= cols; cc++) M[pivotRow][cc] /= div;

                // Eliminate other rows
                for (int r = 0; r < rows; r++) {
                    if (r != pivotRow && Math.abs(M[r][c]) > 1e-9) {
                        double factor = M[r][c];
                        for (int cc = 0; cc <= cols; cc++) M[r][cc] -= factor * M[pivotRow][cc];
                    }
                }

                isPivotCol[c] = true;
                pivotRow++;
            }

            // Record free variables
            freeCols = new ArrayList<>();
            for (int c = 0; c < cols; c++) if (!isPivotCol[c]) freeCols.add(c);
        }

        /**
         * Solve system for a given set of free variable values
         * Returns null if negative values appear (invalid)
         */
        double[] solveGivenFree(double[] freeValues) {
            double[] x = new double[cols];
            for (int i = 0; i < freeCols.size(); i++) x[freeCols.get(i)] = freeValues[i];

            // Back-substitute pivot variables
            for (int r = 0; r < rows; r++) {
                int pivotCol = -1;
                for (int c = 0; c < cols; c++) {
                    if (Math.abs(M[r][c] - 1.0) < 1e-9 && isPivotCol[c]) { pivotCol = c; break; }
                }
                if (pivotCol == -1) continue;

                double sum = M[r][cols];
                for (int c = 0; c < cols; c++) if (c != pivotCol) sum -= M[r][c] * x[c];
                x[pivotCol] = sum;
            }

            // Check for negative values
            for (double v : x) if (v < -1e-9) return null;
            return x;
        }

        /**
         * Minimize total integer solution by trying all combinations of free variables
         */
        long minimizeIntegerSolution() {
            int F = freeCols.size(); // number of free variables
            int max = 0;
            for (int r = 0; r < rows; r++) max += (int) M[r][cols]; // max sum for pruning

            if (F == 0) { // No free variables
                double[] x = solveGivenFree(new double[0]);
                if (x == null) return Long.MAX_VALUE;
                long sum = 0;
                for (double v : x) sum += Math.round(v);
                return sum;
            }

            long[] best = {Long.MAX_VALUE};

            // Recursive DFS over all free variable combinations
            dfsFreeVars(0, F, max, new int[F], chosen -> {
                double[] free = new double[F];
                for (int i = 0; i < F; i++) free[i] = chosen[i];
                double[] sol = solveGivenFree(free);
                if (sol == null) return;

                long s = 0;
                for (double v : sol) {
                    if (Math.abs(v - Math.round(v)) > 1e-9) return; // Not integer
                    s += Math.round(v);
                }
                if (s < best[0]) best[0] = s;
            });

            return best[0];
        }

        /**
         * DFS helper to try all free variable combinations
         */
        void dfsFreeVars(int idx, int F, int max, int[] arr, Consumer<int[]> callback) {
            if (idx == F) {
                callback.accept(Arrays.copyOf(arr, arr.length));
                return;
            }
            for (int v = 0; v <= max; v++) {
                arr[idx] = v;
                dfsFreeVars(idx + 1, F, max, arr, callback);
            }
        }
    }
}
