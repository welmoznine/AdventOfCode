// Solved with Gemini

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day9Part2 {

    // Simple Point class to keep code clean
    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("AoC2025/inputs/Day9Input.txt"));
        List<Point> polygon = new ArrayList<>();

        // 1. Parse Input into a list of vertices (The Polygon)
        for (String line : lines) {
            String[] split = line.trim().split(",");
            polygon.add(new Point(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1])
            ));
        }

        long maxArea = 0;
        int N = polygon.size();

        // 2. Iterate every pair of Red Tiles (vertices)
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Point p1 = polygon.get(i);
                Point p2 = polygon.get(j);

                // Calculate dimensions (inclusive)
                long width = Math.abs(p1.x - p2.x) + 1;
                long height = Math.abs(p1.y - p2.y) + 1;
                long area = width * height;

                // Optimization: Don't check complex geometry if area is already too small
                if (area <= maxArea) continue;

                // Define the rectangle bounds
                int rMinX = Math.min(p1.x, p2.x);
                int rMaxX = Math.max(p1.x, p2.x);
                int rMinY = Math.min(p1.y, p2.y);
                int rMaxY = Math.max(p1.y, p2.y);

                // 3. Check if this rectangle is valid (Inside polygon, no walls crossing it)
                if (isValid(rMinX, rMaxX, rMinY, rMaxY, polygon)) {
                    maxArea = area;
                }
            }
        }

        System.out.println(maxArea);
    }

    // Checks if the rectangle is strictly contained within the polygon
    static boolean isValid(int rMinX, int rMaxX, int rMinY, int rMaxY, List<Point> poly) {
        int N = poly.size();

        // CHECK 1: Does any polygon edge cut through the INTERIOR of the rectangle?
        // (Edges on the boundary are fine, they are Green tiles)
        for (int i = 0; i < N; i++) {
            Point u = poly.get(i);
            Point v = poly.get((i + 1) % N); // Wrap around to first point

            if (u.x == v.x) { // Vertical Polygon Edge
                int edgeX = u.x;
                int edgeY1 = Math.min(u.y, v.y);
                int edgeY2 = Math.max(u.y, v.y);

                // Is the edge strictly between left and right of rectangle?
                if (edgeX > rMinX && edgeX < rMaxX) {
                    // Does it overlap vertically with the rectangle interior?
                    // Intersection of [edgeY1, edgeY2] and (rMinY, rMaxY)
                    int overlapMin = Math.max(edgeY1, rMinY + 1);
                    int overlapMax = Math.min(edgeY2, rMaxY - 1);
                    if (overlapMin <= overlapMax) return false; // Edge cuts through!
                }

            } else { // Horizontal Polygon Edge
                int edgeY = u.y;
                int edgeX1 = Math.min(u.x, v.x);
                int edgeX2 = Math.max(u.x, v.x);

                // Is the edge strictly between top and bottom of rectangle?
                if (edgeY > rMinY && edgeY < rMaxY) {
                    // Does it overlap horizontally with the rectangle interior?
                    int overlapMin = Math.max(edgeX1, rMinX + 1);
                    int overlapMax = Math.min(edgeX2, rMaxX - 1);
                    if (overlapMin <= overlapMax) return false; // Edge cuts through!
                }
            }
        }

        // CHECK 2: Is the rectangle actually inside the polygon?
        // If no edges cross it, it is either fully inside or fully outside.
        // We pick a sample point strictly inside the rectangle and Ray Cast.
        // We use doubles (x + 0.5) to avoid hitting grid lines exactly.
        double testX = rMinX + 0.5;
        double testY = rMinY + 0.5;

        // However, if width/height is 1, the interior is empty.
        // In that case, the rectangle is just a line on the grid.
        // A line between two red tiles is valid if it doesn't cross the void.
        // For simplicity, we can offset slightly to test "just inside" the corner.
        if (rMinX == rMaxX || rMinY == rMaxY) {
            // It's a line. The "Edge Cut" check above handles most cases.
            // We just need to ensure the line itself isn't in void.
            // But usually, max area won't be a 1-unit line. We can skip logic or assume validity
            // if we are confident the max area is 2D.
            // For robustness, let's just use a tiny offset that stays within bounds.
            testX = rMinX + 0.01;
            testY = rMinY + 0.01;
        }

        int intersections = 0;
        for (int i = 0; i < N; i++) {
            Point u = poly.get(i);
            Point v = poly.get((i + 1) % N);

            // Ray casting to the right (Count Vertical Edges)
            if (u.x == v.x) {
                double edgeX = u.x;
                double y1 = Math.min(u.y, v.y);
                double y2 = Math.max(u.y, v.y);

                if (edgeX > testX) { // Edge is to the right
                    if (testY > y1 && testY < y2) { // Ray passes through edge
                        intersections++;
                    }
                }
            }
        }

        // Odd intersections = Inside. Even = Outside.
        return (intersections % 2 != 0);
    }
}