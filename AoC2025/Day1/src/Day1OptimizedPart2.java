import java.io.*; // For IOException and BufferedReader
import java.nio.file.*; // Optional if you want Paths/Files alternative

public class Day1OptimizedPart2 {
    public static void main(String[] args) {
        // Variable to track the answer (number of times the dial points at 0)
        int ans = 0;

        // The dial starts at position 50
        int dial = 50;

        // Path to the input file containing the sequence of rotations
        String rotations = "input.txt";

        // Open input.txt and read it line by line
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(rotations))) {
            String currentLine; // Stores the current rotation instruction

            // Process each line in the input file
            while ((currentLine = bufferReader.readLine()) != null) {

                // Get the first character to determine rotation direction ('L' or 'R')
                char first = currentLine.charAt(0);
                int leftOrRight; // -1 for left, +1 for right

                if (first == 'L') {
                    leftOrRight = -1; // Left rotation decreases dial
                } else if (first == 'R') {
                    leftOrRight = 1; // Right rotation increases dial
                } else {
                    // Skip invalid lines that do not start with L or R
                    continue;
                }

                // Parse the number of clicks from the rest of the line
                int number = Integer.parseInt(currentLine.substring(1));

                // Rotate the dial **step by step** to count every time it passes 0
                for (int i = 0; i < number; i++) {
                    // Move the dial one click in the correct direction
                    dial += leftOrRight;

                    // Wrap around if dial goes below 0 (go to 99) or above 99 (go to 0)
                    if (dial < 0) dial = 99;
                    if (dial > 99) dial = 0;

                    // Increment answer every time we hit 0
                    if (dial == 0) ans++;
                }
            }

        } catch (IOException error) {
            // Handle file reading errors gracefully
            System.out.println("Error while reading the rotations file.");
        }

        // Print the final answer (the secret password for Part Two)
        System.out.println(ans);
    }
}
