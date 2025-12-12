// Solved with Gemini

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day11Part1 {

    // Adjacency list to store the graph: Node -> List of children
    static Map<String, List<String>> graph = new HashMap<>();

    // Memoization map: Node -> Number of paths to 'out'
    static Map<String, Long> memo = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 1. Read input
        List<String> lines = Files.readAllLines(Path.of("AoC2025/inputs/Day11Input.txt"));

        // 2. Parse the graph
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Split "name: output1 output2"
            String[] parts = line.split(":");
            String name = parts[0].trim();

            // If there are outputs, parse them
            if (parts.length > 1) {
                String[] outputs = parts[1].trim().split("\\s+");
                graph.put(name, Arrays.asList(outputs));
            } else {
                graph.put(name, new ArrayList<>());
            }
        }

        // 3. Calculate paths
        // We use 'long' because path counts can quickly exceed integer limits
        long totalPaths = countPaths("you");

        System.out.println("Total paths from 'you' to 'out': " + totalPaths);
    }

    private static long countPaths(String current) {
        // Base Case: If we reached the target, we found 1 valid path
        if (current.equals("out")) {
            return 1;
        }

        // Check Memoization: Have we already solved this node?
        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        // If this node has no outgoing connections (dead end), it contributes 0 paths
        if (!graph.containsKey(current)) {
            return 0;
        }

        // Recursive Step: Sum the paths of all children
        long sum = 0;
        for (String neighbor : graph.get(current)) {
            sum += countPaths(neighbor);
        }

        // Store result in memo map before returning
        memo.put(current, sum);
        return sum;
    }
}