import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2Part2 {
    public static void main(String[] args) throws Exception {
        // Variable to track the answer (sum of invalid IDs)
        long ans = 0;

        // Get the single long line of ID ranges from the input file
        String line = Files.readString(Paths.get("input.txt"));

        // Split the ranges by comma
        String[] ranges = line.split(",");

        // Iterate through all ranges
        for (String range : ranges) {
            // Split each range by hyphen to get start and end
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);

            // Iterate through all IDs in this range
            for (long id = start; id <= end; id++) {
                String idStr = String.valueOf(id);
                int len = idStr.length();
                boolean invalid = false;

                // Check for any repeating sequence that repeats at least twice
                for (int seqLen = 1; seqLen <= len / 2; seqLen++) {
                    if (len % seqLen != 0) continue; // only full repeats

                    String seq = idStr.substring(0, seqLen);
                    StringBuilder repeated = new StringBuilder();
                    for (int i = 0; i < len / seqLen; i++) {
                        repeated.append(seq);
                    }

                    if (repeated.toString().equals(idStr)) {
                        invalid = true;
                        break; // found a repeating sequence, no need to check further
                    }
                }

                // If invalid, add to the answer
                if (invalid) ans += id;
            }
        }

        // Print the final answer (sum of invalid IDs)
        System.out.println(ans);
    }
}
