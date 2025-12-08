import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7Part2 {
    public static void main(String[] args) throws IOException {

        // Read the input file into a List of strings
        List<String> input = Files.readAllLines(Paths.get("AoC2025/inputs/Day7Input.txt"));

        // Map to track how many timelines reach each column on the current row
        // Key = column index, Value = number of timelines that arrive at that column
        Map<Integer, Long> timelineCounts = new HashMap<>();

        // Get the first line and locate the S (start)
        String firstLine = input.get(0);
        int startColumn = firstLine.indexOf('S');

        // At the top row, the particle exists in exactly 1 timeline at the start column
        timelineCounts.put(startColumn, 1L);

        // Process each row after the first
        for (int row = 1; row < input.size(); row++) {

            String currentLine = input.get(row);

            // Store the timeline counts for the NEXT row
            Map<Integer, Long> nextRowCounts = new HashMap<>();

            // Go through each column that currently has timelines
            for (Integer col : timelineCounts.keySet()) {

                long timelineAmount = timelineCounts.get(col);

                // Skip if column is outside the line
                if (col < 0 || col >= currentLine.length()) {
                    continue;
                }

                char cell = currentLine.charAt(col);

                // If we hit a splitter '^', timelines double (one goes left, one goes right)
                if (cell == '^') {

                    // Left timeline
                    int leftCol = col - 1;
                    if (leftCol >= 0) {
                        long oldValue = nextRowCounts.getOrDefault(leftCol, 0L);
                        nextRowCounts.put(leftCol, oldValue + timelineAmount);
                    }

                    // Right timeline
                    int rightCol = col + 1;
                    if (rightCol < currentLine.length()) {
                        long oldValue = nextRowCounts.getOrDefault(rightCol, 0L);
                        nextRowCounts.put(rightCol, oldValue + timelineAmount);
                    }

                } else {
                    // Otherwise '.' (timeline continues straight down)
                    long oldValue = nextRowCounts.getOrDefault(col, 0L);
                    nextRowCounts.put(col, oldValue + timelineAmount);
                }
            }

            // Move down to the next row
            timelineCounts = nextRowCounts;
        }

        // Sum all remaining timelines at the bottom
        long totalTimelines = 0;
        for (long count : timelineCounts.values()) {
            totalTimelines += count;
        }

        // Print the number of different timelines a single tachyon particle would end up on
        System.out.println(totalTimelines);
    }
}