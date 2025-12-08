import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7Part1 {
    public static void main(String[] args) throws IOException {
        // Create a variable to track the answer (number of times the beam is split)
        int ans = 0;

        // Read the input file into a List of strings
        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day7Input.txt"));

        // Create a variable to store the set of column indexes where beams currently exist
        Set<Integer> beamCols = new HashSet<>();

        // Get the first line, locate the S (start) and initialize the beam's starting column at that index
        String firstLine = input.get(0);
        int sIndex = firstLine.indexOf('S');
        beamCols.add(sIndex);

        // Process each remaining line of the input, starting at row 1
        for (int r = 1; r < input.size(); r++) {
            String currentLine = input.get(r);

            // Set for beams in the next row after processing this one
            Set<Integer> nextBeams = new HashSet<>();

            // For each active beam, determine what happens in this row
            for (int col : beamCols) {
                // Ignore beams that fall outside the row’s bounds
                if (col < 0 || col >= currentLine.length())
                    continue;

                char cell = currentLine.charAt(col);

                if (cell == '^') {
                    // Beam is stopped, and it splits left and right
                    ans++;  // Count this split event

                    // Emit left beam
                    if (col - 1 >= 0)
                        nextBeams.add(col - 1);

                    // Emit right beam
                    if (col + 1 < currentLine.length())
                        nextBeams.add(col + 1);

                } else {
                    // Empty space '.' means the beam continues straight down
                    nextBeams.add(col);
                }
            }

            // Update beams for the next row
            beamCols = nextBeams;
        }

        // Print the total number of splits
        System.out.println(ans);
    }
}
