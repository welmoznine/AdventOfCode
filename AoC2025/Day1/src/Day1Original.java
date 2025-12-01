import java.io.*;

public class Day1Original {
    public static void main(String[] args) {
        // Create a variable to track the answer (number of times the dial points at 0)
        int ans = 0;

        // Create a variable to track the number at which the dial is pointing, starting at 50
        int dial = 50;

        // Path to the input file containing the sequence of rotations
        String rotations = "input.txt";

        // Open input.txt to read the sequence of rotations
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

                // Update the dial position (circular from 0 to 99)
                dial = (dial + leftOrRight * number) % 100;

                // If the result is negative, wrap around to the other end of the dial
                if (dial < 0) {
                    dial += 100;
                }

                // If the dial points at 0, increment the answer
                if (dial == 0) {
                    ans++;
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
