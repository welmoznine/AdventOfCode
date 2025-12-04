import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day3Part1 {
    public static void main(String[] args) throws Exception {

        // Create a variable to track the answer (the sum of the maximum joltage from each battery bank)
        int ans = 0;

        // Read all lines from the input file into a list of strings
        List<String> allBatteryBanks = Files.readAllLines(Paths.get("AoC2025/inputs/Day3Input.txt"));

        // Iterate through all of the battery banks
        for (String bank : allBatteryBanks) {

            // First pass through, find the largest digit from index 0 to n - 1
            int largestFirstNum = 0;
            int largestFirstIndex = 0;

            for (int i = 0; i < bank.length() - 1; i++) {
                // Convert the current char into an int
                int currNum = bank.charAt(i) - '0';

                // If the current number in the battery bank is larger than the previous number, update
                if (currNum > largestFirstNum) {
                    largestFirstNum = currNum;
                    largestFirstIndex = i;
                }
            }

            // Second pass through, find the largest digit for index largestFirstIndex + 1 to n
            int largestSecondNum = 0;
            int largestSecondIndex = largestFirstIndex + 1;

            for (int i = largestFirstIndex + 1; i < bank.length(); i++) {
                // Convert the current char into an int
                int currNum = bank.charAt(i) - '0';

                // If the current number in the battery bank is larger than the previous number, update
                if (currNum > largestSecondNum) {
                    largestSecondNum = currNum;
                    largestSecondIndex = i;
                }
            }

            // Now, use the two indices to get the largest possible joltage for that bank
            String largestPossibleString =
                    "" + bank.charAt(largestFirstIndex) + bank.charAt(largestSecondIndex);

            int largestPossibleInt = Integer.parseInt(largestPossibleString);

            // Update the sum of the maximum joltage from each bank
            ans += largestPossibleInt;
        }

        // After going through each battery bank, print the sum joltage possible
        System.out.println(ans);
    }
}
