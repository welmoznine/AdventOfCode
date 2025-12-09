/**
 * Unable to solve on my own :(
 *  But learned about DSU data structures :)
 *
 * PART TWO — OVERVIEW OF THE APPROACH
 *
 * We again have many junction boxes, each with X,Y,Z coordinates.
 * Unlike Part 1 (where we only connect the closest 1000 pairs),
 * in Part 2 we must keep connecting junction boxes until **every junction box is part of a single unified circuit**.
 *
 * We must:
 * 1. Read all junction box coordinates into memory.
 * 2. Compute the distance between EVERY pair of junction boxes. (We use squared distance to avoid slow square-root operations.)
 * 3. Store every pair as: [distance, indexA, indexB].
 * 4. Sort all pairs from smallest distance to largest distance. This ensures we always consider the closest pair next.
 * 5. Create a DSU (Disjoint Set Union) structure in which each junction box begins as its own independent circuit.
 * 6. Walk through the sorted list of pairs in order.
 *      - For each pair, try to union() the two junction boxes.
 *      - If union() succeeds, it means two previously separate
 *        circuits have now become connected.
 *      - Whenever a union happens, record the X-coordinates of
 *        those two boxes.
 * 7. Stop as soon as there is **exactly one** connected component. That means all junction boxes now belong to a single circuit.
 * 8. The last pair that successfully united two circuits is the pair that finally completed the entire structure. Multiply their X coordinates to get the required answer.
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8Part2 {

    static class Point3D {
        long x, y, z;

        Point3D(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Return **squared distance** (faster, no sqrt)
        long distanceSquared(Point3D other) {
            long dx = x - other.x;
            long dy = y - other.y;
            long dz = z - other.z;
            return dx*dx + dy*dy + dz*dz;
        }
    }

    // -------------------------
    // DSU (Disjoint Set Union)
    // -------------------------
    static class DSU {
        int[] parent;
        int[] size;
        int components;     // <-- NEW: track remaining components

        DSU(int n) {
            parent = new int[n];
            size = new int[n];
            components = n; // initially, N separate circuits

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] == x) return x;
            parent[x] = find(parent[x]);
            return parent[x];
        }

        boolean union(int a, int b) {
            int ra = find(a);
            int rb = find(b);

            if (ra == rb) return false; // no merge happened

            // union by size
            if (size[ra] < size[rb]) {
                parent[ra] = rb;
                size[rb] += size[ra];
            } else {
                parent[rb] = ra;
                size[ra] += size[rb];
            }

            components--;  // merged two sets into one
            return true;   // merge performed
        }
    }

    // -------------------------
    // MAIN PART 2
    // -------------------------
    public static void main(String[] args) throws IOException {

        // Step 1: read points
        List<String> lines = Files.readAllLines(Paths.get("AoC2025/inputs/Day8Input.txt"));

        List<Point3D> points = new ArrayList<>();
        for (String line : lines) {
            String[] p = line.split(",");
            points.add(new Point3D(
                    Long.parseLong(p[0].trim()),
                    Long.parseLong(p[1].trim()),
                    Long.parseLong(p[2].trim())
            ));
        }

        int n = points.size();

        // Step 2: Build all edges (distance, indexA, indexB)
        List<long[]> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long d = points.get(i).distanceSquared(points.get(j));
                edges.add(new long[]{ d, i, j });
            }
        }

        // Step 3: Sort pairs by distance
        edges.sort(Comparator.comparingLong(a -> a[0]));

        // Step 4: DSU
        DSU dsu = new DSU(n);

        // Variables to store the LAST successful connection
        long lastX1 = -1;
        long lastX2 = -1;

        // Step 5: Keep merging until ONE circuit remains
        for (long[] e : edges) {
            int a = (int) e[1];
            int b = (int) e[2];

            boolean merged = dsu.union(a, b);

            if (merged) {
                // record the X values of this connection
                lastX1 = points.get(a).x;
                lastX2 = points.get(b).x;

                // check if only ONE circuit remains
                if (dsu.components == 1) {
                    break; // we found the final connection
                }
            }
        }

        // Step 6: Multiply X coordinates of last merged pair
        long answer = lastX1 * lastX2;

        System.out.println(answer);
    }
}
