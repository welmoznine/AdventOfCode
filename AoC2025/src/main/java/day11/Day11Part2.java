// Solved with Gemini

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day11Part2 {

    static Map<String, List<String>> graph = new HashMap<>();
    static Map<String, Long> memo = new HashMap<>();

    public static void main(String[] args) throws Exception {
        // 1. Read input
        List<String> lines = Files.readAllLines(Path.of("AoC2025/inputs/Day11Input.txt"));

        // 2. Parse the graph
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(":");
            String name = parts[0].trim();

            if (parts.length > 1) {
                // Split by whitespace or comma
                String[] outputs = parts[1].trim().split("[\\s,]+");
                List<String> connections = new ArrayList<>();
                for (String out : outputs) {
                    if (!out.isEmpty()) connections.add(out.trim());
                }
                graph.put(name, connections);
            } else {
                graph.put(name, new ArrayList<>());
            }
        }

        // 3. Calculate Sequence A: svr -> dac -> fft -> out
        // We must clear memo between steps because the 'target' changes!
        memo.clear();
        long svrToDac = countPaths("svr", "dac");

        memo.clear();
        long dacToFft = countPaths("dac", "fft");

        memo.clear();
        long fftToOut = countPaths("fft", "out");

        long sequenceA = svrToDac * dacToFft * fftToOut;

        // 4. Calculate Sequence B: svr -> fft -> dac -> out
        memo.clear();
        long svrToFft = countPaths("svr", "fft");

        memo.clear();
        long fftToDac = countPaths("fft", "dac");

        memo.clear();
        long dacToOut = countPaths("dac", "out");

        long sequenceB = svrToFft * fftToDac * dacToOut;

        // 5. Total
        // Note: In a DAG, usually only one of these sequences will be > 0.
        // If they are parallel branches, both will be 0.
        long totalValidPaths = sequenceA + sequenceB;

        System.out.println("--- Results ---");
        System.out.println("Sequence A (svr->dac->fft->out): " + sequenceA);
        System.out.println("Sequence B (svr->fft->dac->out): " + sequenceB);
        System.out.println("Total paths visiting both: " + totalValidPaths);
    }

    // Updated to accept a specific 'target' node
    private static long countPaths(String current, String target) {
        // Base Case: Reached the specific target for this segment
        if (current.equals(target)) {
            return 1;
        }

        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        if (!graph.containsKey(current)) {
            return 0;
        }

        long sum = 0;
        for (String neighbor : graph.get(current)) {
            sum += countPaths(neighbor, target);
        }

        memo.put(current, sum);
        return sum;
    }
}