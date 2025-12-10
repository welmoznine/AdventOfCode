import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day9Part1 {
    public static void main(String[] args) throws Exception {

        // Read input lines like "7,3"
        List<String> tiles = Files.readAllLines(Paths.get("AoC2025/inputs/Day9Input.txt"));

        long maxArea = 0;

        for (int i = 0; i < tiles.size(); i++) {
            String[] split1 = tiles.get(i).split(",");
            int x1 = Integer.parseInt(split1[0]);
            int y1 = Integer.parseInt(split1[1]);

            for (int j = i + 1; j < tiles.size(); j++) {
                String[] split2 = tiles.get(j).split(",");
                int x2 = Integer.parseInt(split2[0]);
                int y2 = Integer.parseInt(split2[1]);

                // Width = difference in X + 1 (to include both corners)
                int width = Math.abs(x1 - x2) + 1;
                // Height = difference in Y + 1 (to include both corners)
                int height = Math.abs(y1 - y2) + 1;

                long area = (long) width * height;
                maxArea = Math.max(maxArea, area);
            }
        }

        System.out.println(maxArea);
    }
}
