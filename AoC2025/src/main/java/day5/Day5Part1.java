// -------------------------- ATTEMPT #2: REVISED --------------------------
// Correct approach: Parse each fresh-ingredient range as an inclusive interval,
// then for each ingredient ID, check whether it falls within ANY of the ranges.
// This works even when ranges overlap because we simply accept the first interval
// that contains the value. No assumptions about ordering or non-overlap are made.

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class Day5Part1 {
    public static void main(String[] args) throws Exception {
        // Create a variable to track the number of available ingredient IDs that are fresh
        int ans = 0;

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

        // Split the file into:
        //   1. List of fresh ingredient ID ranges
        //   2. List of available ingredient IDs
        List<String> ingredientRanges = input.subList(0, blankLineIndex);
        List<String> ingredientIDs = input.subList(blankLineIndex + 1, input.size());

        // Store the ingredient ID ranges as long pairs
        List<long[]> ranges = new ArrayList<>();
        for (String range : ingredientRanges) {
            String[] parts = range.split("-");
            long start = Long.parseLong(parts[0]);
            long end   = Long.parseLong(parts[1]);
            ranges.add(new long[]{start, end});
        }

        // Check each ingredient ID
        for (String idStr : ingredientIDs) {
            long id = Long.parseLong(idStr);

            // Check if the current id is within any fresh range
            boolean isFresh = false;
            for (long[] r : ranges) {
                if (id >= r[0] && id <= r[1]) {
                    isFresh = true;
                    break;
                }
            }

            // If it does, increment the answer
            if (isFresh) ans++;
        }

        // Print the total number of available ingredient IDs that are fresh
        System.out.println(ans);
    }
}

// -------------------------- ATTEMPT #1: WRONG APPROACH & DOESN'T WORK --------------------------
// This approach is incorrect because it assumes that each fresh range covers ONLY the
// explicitly listed numbers or that the ranges don’t overlap. In reality, the fresh
// ingredient ranges are fully inclusive (e.g., 3-5 means 3, 4, 5) *and* can overlap.
// Because of that, you cannot treat the ranges independently or rely on direct equality
// checks; you must check whether each ingredient ID falls within ANY inclusive range.
// The flawed approach fails because it doesn’t correctly verify membership within
// these inclusive intervals and doesn’t account for overlapping ranges.

//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.HashSet;
//
//public class Day5Part1 {
//    public static void main(String[] args) throws Exception {
//        // Create a variable to track the number of available ingredient IDs that are fresh
//        int ans = 0;
//
//        // Read the entire input file
//        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day5Input.txt"));
//
//        // Find the blank line separating the fresh ranges from the ingredient ID list
//        int blankLineIndex = 0;
//        for (int i = 0; i < input.size(); i++) {
//            if (input.get(i).isBlank()) {
//                blankLineIndex = i;
//                break;
//            }
//        }
//
//        // Split the file into:
//        //   1. List of fresh ingredient ID ranges
//        //   2. List of available ingredient IDs
//        List<String> ingredientRanges = input.subList(0, blankLineIndex);
//        List<String> ingredientIDs = input.subList(blankLineIndex + 1, input.size());
//
//        // Create a HashSet to store all fresh ingredient IDs found in the ranges
//        HashSet<Long> freshIDs = new HashSet<>();
//
//        // Convert each range into actual ID numbers and store them in the HashSet
//        for (String range : ingredientRanges) {
//            // Each line looks like "start-end"
//            String[] startAndEnd = range.split("-");
//
//            // Convert start and end to long since values exceed int range
//            long start = Long.parseLong(startAndEnd[0]);
//            long end = Long.parseLong(startAndEnd[1]);
//
//            // Populate the HashSet with all values from start (inclusive) to end (exclusive)
//            for (long i = start; i < end; i++) {
//                freshIDs.add(i);
//            }
//        }
//
//        // Now check each available ingredient ID
//        for (String id : ingredientIDs) {
//            long val = Long.parseLong(id);
//
//            // Count it if it exists in the fresh ID set
//            if (freshIDs.contains(val)) {
//                ans += 1;
//            }
//        }
//
//        // Print the total number of available ingredient IDs that are fresh
//        System.out.println(ans);
//    }
//}
