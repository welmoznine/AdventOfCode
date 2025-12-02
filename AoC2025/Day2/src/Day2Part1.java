import java.nio.file.Files;
import java.nio.file.Paths;

public class Day2Part1 {
    public static void main(String[] args) throws Exception {
        // Create a variable to track the answer (sum of invalid IDs)
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

                // Check for repeating patterns (only even length IDs can repeat twice)
                int len = idStr.length();
                if (len % 2 == 0) {
                    // Get the first half and the second half of the even length ID
                    String firstHalf = idStr.substring(0, len / 2);
                    String secondHalf = idStr.substring(len / 2);
                    // If the two halves match, update the answer
                    if (firstHalf.equals(secondHalf)) {
                        ans += id;
                    }
                }
            }
        }

        // Print the final answer (sum of invalid IDs)
        System.out.println(ans);
    }
}
