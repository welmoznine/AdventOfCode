import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Day3Part2 {
    public static void main(String[] args) throws Exception {

        // Create a variable to track the answer (the sum of the maximum joltage from each battery bank)
        long ans = 0;

        // Read all lines from the input file into a list of strings
        List<String> allBatteryBanks = Files.readAllLines(Paths.get("AoC2025/inputs/Day3Input.txt"));

        // Iterate through all of the battery banks
        for (String bank : allBatteryBanks) {
            // Find the best 12 digits while maintaining order
            int k = 12;
            int start = 0;
            StringBuilder largestPossibleString = new StringBuilder();

            for (int picks = 0; picks < k; picks++) {

                int bestDigit = -1;
                int bestIndex = start;

                // You must leave enough digits remaining to reach 12 picks
                int maxSearchEnd = bank.length() - (k - picks);

                for (int i = start; i <= maxSearchEnd; i++) {
                    int curr = bank.charAt(i) - '0';
                    if (curr > bestDigit) {
                        bestDigit = curr;
                        bestIndex = i;
                    }
                }

                largestPossibleString.append(bank.charAt(bestIndex));
                start = bestIndex + 1;
            }

            long largestPossibleLong = Long.parseLong(largestPossibleString.toString());
            ans += largestPossibleLong;
        }

        // After going through each battery bank, print the sum joltage possible
        System.out.println(ans);
    }
}
