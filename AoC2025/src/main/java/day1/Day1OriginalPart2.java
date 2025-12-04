import java.io.*;

public class Day1OriginalPart2 {
    public static void main(String[] args) {
        // Create a variable to track the answer (number of times the dial points at 0)
        int ans = 0;

        // Create a variable to track the number at which the dial is pointing, starting at 50
        int dial = 50;

        // Path to the input file containing the sequence of rotations
        String rotations = "AoC2025/inputs/Day1Input.txt";

        // Open input file to read the sequence of rotations
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(rotations))) {
            String currentLine; // Store the current line of the file

            // Process each line in the input file
            while ((currentLine = bufferReader.readLine()) != null) {

                // Get the first character to determine rotation direction
                char first = currentLine.charAt(0);
                int leftOrRight; // Flag to indicate left (-1) or right (+1) rotation

                // Determine the direction of rotation
                if (first == 'L') {
                    leftOrRight = -1; // Left rotation decreases dial
                } else if (first == 'R') {
                    leftOrRight = 1;  // Right rotation increases dial
                } else {
                    continue; // Skip lines that do not start with L or R
                }

                // Get the number of clicks after the first character
                int number = Integer.parseInt(currentLine.substring(1));

                // Instead of using modulo like in Part 1, rotate step by step
                for (int i = 0; i < number; i++) {
                    // Move the dial one click in the correct direction
                    dial += leftOrRight;

                    // Wrap around if dial goes below 0 or above 99
                    if (dial < 0) dial = 99;
                    if (dial > 99) dial = 0;

                    // Increment answer every time we hit 0
                    if (dial == 0) ans++;
                }
            }

        } catch (IOException error) {
            // Handle errors reading the file
            System.out.println("Error while reading the rotations file.");
        }

        // Print the final answer (the secret password)
        System.out.println(ans);
    }
}
