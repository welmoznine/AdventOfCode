import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class Day5Part2 {
    public static void main(String[] args) throws Exception {
        // Create a variable to track the number of ingredient IDs
        // that are considered to be fresh according to the fresh ingredient ID ranges
        long ans = 0;

        // Read the entire input file
        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day5Input.txt"));

        // Find the blank line separating the fresh ranges from the ingredient ID list
        int blankLineIndex = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).isBlank()) {
                blankLineIndex = i;
                break;
            }
        }

        // Split the file into the list of fresh ingredient ID ranges
        List<String> ingredientRanges = input.subList(0, blankLineIndex);

        // NOTE: Don't naively add (end - start). We need to first merge overlapping to avoid double-counting IDs.
        // Parse the list of fresh ingredient ID ranges into long[] pairs
        List<long[]> ranges = new ArrayList<>();
        for (String range : ingredientRanges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end   = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }

        // Sort all ranges by their starting value so we can merge them
        ranges.sort(Comparator.comparingLong(r -> r[0]));

        // Begin merging the ranges
        long currentStart = ranges.get(0)[0];
        long currentEnd   = ranges.get(0)[1];

        // Iterate through the sorted list of ranges
        for (int i = 1; i < ranges.size(); i++) {
            long s = ranges.get(i)[0];
            long e = ranges.get(i)[1];

            if (s <= currentEnd + 1) {
                // If the next range overlaps or touches the current merged range,
                // extend the current merged range to include it
                currentEnd = Math.max(currentEnd, e);
            } else {
                // Otherwise, close off the current merged interval and count its size
                ans += (currentEnd - currentStart + 1);

                // Start a new merged interval
                currentStart = s;
                currentEnd   = e;
            }
        }

        // Add the final merged interval
        ans += (currentEnd - currentStart + 1);

        // Print the number of ingredient IDs that considered to be fresh according to the fresh ingredient ID ranges
        System.out.println(ans);
    }
}
