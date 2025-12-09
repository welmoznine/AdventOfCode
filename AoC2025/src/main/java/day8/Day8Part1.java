/**
 * Unable to solve on my own :(
 * But learned about DSU data structures :)
 *
 * PART ONE  - OVERVIEW OF THE APPROACH
 *
 * We have many junction boxes, each with X,Y,Z coordinates.
 * We must:
 *   1. Compute the distance between EVERY pair of junction boxes.
 *   2. Sort the pairs from smallest distance to largest.
 *   3. Take the FIRST 1000 pairs and connect them using DSU.
 *   4. After all unions, determine the sizes of all resulting circuits.
 *   5. Multiply the sizes of the three largest circuits.
 * */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day8Part1 {

    // Helper class: 3D point
    static class Point3D {
        long x, y, z;

        Point3D(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        // Computes the squared distance to another point.
        long distanceSquared(Point3D other) {
            long dx = this.x - other.x;
            long dy = this.y - other.y;
            long dz = this.z - other.z;
            return dx * dx + dy * dy + dz * dz;
        }
    }

    // DSU (Disjoint Set Union) Data Structure
    static class DSU {
        int[] parent;   // parent[i] = parent of node i
        int[] size;     // size[i] = size of the set for the root i

        DSU(int n) {
            parent = new int[n];
            size   = new int[n];

            // Initially, each element is its OWN parent (root)
            // and each set has size 1.
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // Find the root of a set with path compression
        int find(int x) {
            if (parent[x] == x)
                return x;

            // Path compression: flatten the tree
            parent[x] = find(parent[x]);
            return parent[x];
        }

        // Merge the sets that contain a and b
        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);

            // If already in the same set, do nothing
            if (rootA == rootB)
                return;

            // Union by size: attach smaller tree under larger one
            if (size[rootA] < size[rootB]) {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            } else {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
        }
    }


    public static void main(String[] args) throws IOException {

        // Step 1: Read all lines (each line contains "X,Y,Z")
        List<String> lines = Files.readAllLines(Paths.get("AoC2025/inputs/Day8Input.txt"));

        // Step 2: Convert each line into a Point3D object
        List<Point3D> points = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0].trim());
            long y = Long.parseLong(parts[1].trim());
            long z = Long.parseLong(parts[2].trim());
            points.add(new Point3D(x, y, z));
        }

        int n = points.size();

        // Step 3: Build a list of ALL PAIRS and their distances
        // We store: [distance, i, j]
        List<long[]> edges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                long dist = points.get(i).distanceSquared(points.get(j));

                edges.add(new long[]{ dist, i, j });
            }
        }

        // Step 4: Sort pairs by distance (ascending)
        edges.sort(Comparator.comparingLong(a -> a[0]));

        // Step 5: Create DSU for N items
        DSU dsu = new DSU(n);

        // Step 6: Perform the first 1000 shortest connections
        int LIMIT = 1000;
        for (int i = 0; i < LIMIT; i++) {
            long[] e = edges.get(i);
            int a = (int) e[1];
            int b = (int) e[2];
            dsu.union(a, b);
        }

        // Step 7: Count the size of each final circuit
        // A root's size is stored in dsu.size[root]
        Map<Integer, Integer> circuitSizes = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int root = dsu.find(i);
            circuitSizes.put(root, dsu.size[root]);
        }

        // Step 8: Extract all circuit sizes
        List<Integer> allSizes = new ArrayList<>(circuitSizes.values());

        // Sort sizes descending
        allSizes.sort(Collections.reverseOrder());

        // Step 9: Multiply the 3 largest circuit sizes
        long answer = 1L;
        answer *= allSizes.get(0);
        answer *= allSizes.get(1);
        answer *= allSizes.get(2);

        // Step 10: Print the answer
        System.out.println(answer);
    }
}
