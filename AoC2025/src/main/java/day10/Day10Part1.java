// Skipped, couldn't solve without AI :(
//
// REVIEW NOTES:
// This solution uses concepts from linear algebra applied to a binary system (lights/buttons):
// 1. Represent the problem as a linear system over GF(2) (binary XOR):
//      - Lights = rows, Buttons = columns
//      - A[i][j] = 1 if button j toggles light i
//      - Augmented matrix includes target lights as last column
// 2. Gauss-Jordan elimination (RREF) over GF(2):
//      - Find pivot columns (dependent variables) and free variables
//      - XOR instead of subtraction since binary system
// 3. Null space and particular solution:
//      - Particular solution: set all free variables to 0
//      - Null space vectors: adding them preserves solution but changes button counts
// 4. Minimize total button presses:
//      - Try all combinations of free variables (2^k) to find the minimal sum
// 5. Key concepts to review for deeper understanding:
//      - Linear algebra over GF(2)
//      - Gauss-Jordan elimination and RREF
//      - Null space / free variables / pivot variables
//      - Binary operations (XOR) in linear systems
// 6. Practical takeaways:
//      - Converting combinatorial toggling problems to linear algebra
//      - Using brute force over small null space dimension to minimize integer sums
//      - Handling augmented matrices and pivot tracking in code


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Day 10 Part 1 - Solve the minimum number of button presses to achieve a target light pattern.
 *
 * The machine has:
 *  - Lights (each can be ON=# or OFF=.)
 *  - Buttons (each toggles some subset of lights)
 *
 * The goal is to find the **minimum number of button presses** to reach the target lights.
 */
public class Day10Part1 {

    /**
     * Represents one machine configuration (lights + buttons + augmented matrix)
     */
    static class Machine {
        final int numLights;           // number of lights in this machine
        final int numButtons;          // number of buttons in this machine
        final int[][] augmentedMatrix; // the matrix [A | b] for linear system

        /**
         * Parse a line from input to initialize a machine
         * Example line: [.#.#] (0,2) (1,3) {1,0,1,1}
         */
        Machine(String line) {
            // Regex to extract the lights and button sections
            Pattern p = Pattern.compile("\\[(.*)\\]\\s*([^\\{]*)\\{.*\\}");
            Matcher m = p.matcher(line);
            if (!m.find()) throw new IllegalArgumentException("Invalid line: " + line);

            String lightPattern = m.group(1).trim();  // lights pattern like ".#.#"
            String buttonsString = m.group(2).trim(); // buttons like "(0,2) (1,3)"

            this.numLights = lightPattern.length();

            // Parse target lights: 1 if '#', 0 if '.'
            List<Integer> target = new ArrayList<>();
            for (char c : lightPattern.toCharArray()) {
                target.add(c == '#' ? 1 : 0);
            }

            // Parse button mappings: each button toggles some lights
            Pattern buttonP = Pattern.compile("\\(([0-9,]*)\\)");
            Matcher buttonM = buttonP.matcher(buttonsString);
            List<List<Integer>> buttonMappings = new ArrayList<>();
            while (buttonM.find()) {
                List<Integer> toggled = new ArrayList<>();
                for (String s : buttonM.group(1).split(",")) {
                    if (!s.isEmpty()) toggled.add(Integer.parseInt(s.trim()));
                }
                buttonMappings.add(toggled);
            }

            this.numButtons = buttonMappings.size();

            // Create augmented matrix [A | b]
            // A[i][j] = 1 if button j toggles light i
            // Last column of matrix = target light value (b)
            this.augmentedMatrix = new int[numLights][numButtons + 1];
            for (int j = 0; j < numButtons; j++) {
                for (int light : buttonMappings.get(j)) {
                    if (light < numLights) augmentedMatrix[light][j] = 1;
                }
            }
            for (int i = 0; i < numLights; i++) {
                augmentedMatrix[i][numButtons] = target.get(i);
            }
        }
    }

    /**
     * Solve one machine: return the minimum number of button presses
     */
    public static int solveMachine(Machine machine) {
        int R = machine.numLights;   // number of rows (lights)
        int C = machine.numButtons;  // number of columns (buttons)
        int[][] mat = machine.augmentedMatrix;

        // --- 1. Gauss-Jordan elimination to RREF ---
        // This puts the matrix in reduced row echelon form
        int pivotRow = 0;
        int[] pivotRowForCol = new int[C]; // stores which row is pivot for each column
        for (int i = 0; i < C; i++) pivotRowForCol[i] = -1; // -1 means free variable

        for (int col = 0; col < C && pivotRow < R; col++) {
            // Find pivot in current column
            int pivotI = pivotRow;
            while (pivotI < R && mat[pivotI][col] == 0) pivotI++;

            if (pivotI < R) {
                // Swap pivot row with current row
                int[] temp = mat[pivotRow]; mat[pivotRow] = mat[pivotI]; mat[pivotI] = temp;

                // Record pivot
                pivotRowForCol[col] = pivotRow;

                // Eliminate other rows: XOR operation for binary system
                for (int i = 0; i < R; i++) {
                    if (i != pivotRow && mat[i][col] == 1) {
                        for (int k = col; k <= C; k++) mat[i][k] ^= mat[pivotRow][k];
                    }
                }
                pivotRow++;
            }
        }

        // Check if the system is consistent: no row like [0 0 ... 0 | 1]
        for (int i = pivotRow; i < R; i++) {
            if (mat[i][C] == 1) return 0; // Impossible case
        }

        // --- 2. Build particular solution xp ---
        // Set all free variables = 0
        int[] xp = new int[C];
        for (int j = 0; j < C; j++) {
            if (pivotRowForCol[j] != -1) {
                xp[j] = mat[pivotRowForCol[j]][C]; // value from pivot row
            } else {
                xp[j] = 0; // free variable
            }
        }

        // --- 3. Build null space basis vectors ---
        // Each free variable can be 0 or 1; adding basis vectors preserves solution
        List<int[]> nullSpaceBasis = new ArrayList<>();
        for (int j = 0; j < C; j++) {
            if (pivotRowForCol[j] == -1) { // free variable
                int[] v = new int[C];
                v[j] = 1;
                // Adjust pivot variables to maintain consistency
                for (int k = 0; k < C; k++) {
                    if (pivotRowForCol[k] != -1) {
                        int row = pivotRowForCol[k];
                        if (mat[row][j] == 1) {
                            v[k] = 1;
                        }
                    }
                }
                nullSpaceBasis.add(v);
            }
        }

        // --- 4. Try all combinations of free variables ---
        int minPresses = Integer.MAX_VALUE;
        int numFreeVars = nullSpaceBasis.size();
        int combinations = 1 << numFreeVars; // 2^numFreeVars

        for (int i = 0; i < combinations; i++) {
            // Start with particular solution
            int[] candidate = xp.clone();

            // Add basis vectors according to the bits of i
            for (int b = 0; b < numFreeVars; b++) {
                if (((i >> b) & 1) == 1) {
                    int[] basis = nullSpaceBasis.get(b);
                    for (int k = 0; k < C; k++) candidate[k] ^= basis[k]; // XOR
                }
            }

            // Count total button presses (number of 1s)
            int currentPresses = 0;
            for (int val : candidate) currentPresses += val;

            if (currentPresses < minPresses) minPresses = currentPresses;
        }

        return minPresses;
    }

    /**
     * Main entry point: read input and solve all machines
     */
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("AoC2025/inputs/Day10Input.txt"));
        long totalPresses = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                totalPresses += solveMachine(new Machine(line));
            }
        }
        System.out.println("Total minimum button presses for all machines: " + totalPresses);
    }
}
