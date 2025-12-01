import java.io.*;       // For IOException
import java.nio.file.*; // For Paths and Files

public class Day1Optimized {
    public static void main(String[] args) throws IOException {
        // The dial starts at position 50
        int dial = 50;

        // Variable to track the answer (number of times the dial points at 0)
        int ans = 0;

        // Read all lines from input.txt into a list of strings
        for (String line : Files.readAllLines(Paths.get("input.txt"))) {

            // Get the first character to determine rotation direction ('L' or 'R')
            char first = line.charAt(0);

            // Convert direction to numeric value: -1 for left, +1 for right
            int leftOrRight = first == 'L' ? -1 : 1;

            // Parse the number of clicks from the rest of the string
            int number = Integer.parseInt(line.substring(1));

            // Update the dial position using circular arithmetic (0–99)
            // Math.floorMod handles negative results correctly
            dial = Math.floorMod(dial + leftOrRight * number, 100);

            // If the dial points at 0, increment the answer
            if (dial == 0) ans++;
        }

        // Print the final answer (the secret password)
        System.out.println(ans);
    }
}
