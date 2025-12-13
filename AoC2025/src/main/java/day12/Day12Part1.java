import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day12Part1 {

    public static void main(String[] args) throws IOException {
        solve("AoC2025/inputs/Day12Input.txt");
    }

    private static void solve(String inputPath) throws IOException {

        // Read entire input as one string
        String input = Files.readString(Path.of(inputPath))
                .replace("\r\n", "\n"); // Normalize line endings

        // Split input blocks by blank lines
        String[] blocks = input.split("\n\n");

        // Map: shape ID -> number of '#' cells (area)
        Map<Integer, Integer> shapeAreas = new HashMap<>();

        /* -------------------- Parse Shapes -------------------- */
        // All blocks except the last describe shapes
        for (int i = 0; i < blocks.length - 1; i++) {
            String[] lines = blocks[i].split("\n");

            // First line is the shape ID (e.g. "4:")
            int shapeId = Integer.parseInt(lines[0].replace(":", "").trim());

            int area = 0;
            for (int j = 1; j < lines.length; j++) {
                for (char ch : lines[j].toCharArray()) {
                    if (ch == '#') area++;
                }
            }

            shapeAreas.put(shapeId, area);
        }

        /* -------------------- Parse Regions -------------------- */
        int answer = 0;
        String[] regionLines = blocks[blocks.length - 1].split("\n");

        for (String line : regionLines) {
            if (line.isBlank()) continue;

            // Example: "12x5: 1 0 1 0 3 2"
            String[] parts = line.split(": ");
            String[] dims = parts[0].split("x");
            String[] counts = parts[1].split("\\s+");

            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);
            int gridArea = width * height;

            int presentArea = 0;
            for (int i = 0; i < counts.length; i++) {
                int count = Integer.parseInt(counts[i]);
                if (count > 0) {
                    presentArea += count * shapeAreas.get(i);
                }
            }

            /* -------------------- The Trick -------------------- */
            if (presentArea * 1.3 < gridArea) {
                answer++;
            }
        }

        System.out.println("Answer: " + answer);
    }
}
