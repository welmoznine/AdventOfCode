# 🎄 My Advent of Code solutions

- **AoC 2025**: Learning Java
  - **Day 1**
    - Part 1: Dial rotation puzzle (count how many times the dial points at 0).  
    - Part 2: Count every dial rotation that passes 0, step by step.
      
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day1Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day1Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day1/Day1Original.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day1/Day1OriginalPart2.java)

  - **Day 2**
    - Part 1: Find invalid gift shop IDs, where numbers made of a repeated sequence of digits twice are invalid.  
    - Part 2: Find invalid gift shop IDs, where any number made of a repeating sequence at least twice is invalid.
    
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day2Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day2Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day2/Day2Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day2/Day2Part2.java)

  - **Day 3**
    - Part 1: Determine the largest possible joltage from each battery bank by turning on exactly two batteries; sum them across all banks.  
    - Part 2: Determine the largest possible joltage by turning on exactly twelve batteries; sum across all banks.
    
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day3Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day3Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day3/Day3Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day3/Day3Part2.java)

  - **Day 4**
    - Part 1: Count the rolls of paper (@) that a forklift can access.  
    - Part 2: Repeatedly remove accessible rolls until none remain.
    
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day4Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day4Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day4/Day4Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day4/Day4Part2.java)

  - **Day 5**
    - Part 1: Count fresh ingredient IDs by checking each ID.  
    - Part 2: Merge overlapping/touching fresh ID ranges and count unique IDs.
    
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day5Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day5Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day5/Day5Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day5/Day5Part2.java)

  - **Day 6**
    - Part 1: Read each homework problem *left-to-right*, where each problem is a block of columns separated by a blank column. Each block contains several multi-digit numbers (one per row) and an operator (`+` or `*`) on the last row. Parse the block, evaluate it, and add the result to the total.
    - Part 2: Read each homework problem *column-by-column*, where each column contains a vertical number, and the operator (`+` or `*`) is on the bottom row. Each column represents one number; evaluate the operation across all columns in that block.

    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day6Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day6Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day6/Day6Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day6/Day6Part2.java)

  - **Day 7**
    - Part 1: Trace a tachyon beam falling downward through a grid. When it hits a splitter (`^`), the beam stops and splits into a left and right beam. Count how many total split events occur.
    - Part 2: Instead of 1 beam, track how many timelines reach each column. A splitter duplicates timelines (left + right), and empty space continues the same number downward. Sum all timelines at the bottom.
      
    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day7Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day7Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day7/Day7Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day7/Day7Part2.java)

  - **Day 8**
    - Part 1: Treat each junction box (given by X,Y,Z coordinates) as a node. Compute all pairwise distances, sort them, then connect the 1000 closest pairs using a DSU. After all unions, determine the sizes of all circuits and multiply the sizes of the three largest circuits.
    - Part 2: Continue connecting pairs (from smallest distance upward) until all junction boxes are in a single circuit. The last successful union that merges two previously separate circuits gives the final required pair; multiply their X coordinates.

    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day8Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day8Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day8/Day8Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day8/Day8Part2.java)

  - **Day 9**
    - Part 1: Find the largest possible rectangle area that has two red tiles as opposite corners. Brute-force all pairs of tiles and calculate the max area.
    - Part 2: Find the largest rectangle that has two red tiles as opposite corners and is composed entirely of red or green tiles. Brute-force all pairs and add a geometric check (Point in Polygon / No Edge Crossing).

    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day9Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day9Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day9/Day9Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day9/Day9Part2.java)

  - **Day 10**
    - Part 1: Each machine has lights and buttons. Buttons toggle certain lights on/off. The goal is to find the minimum number of button presses to reach a given target light pattern. Solved using Gauss-Jordan elimination over GF(2) (binary system) with null space exploration to account for free variables.
    - Part 2: Generalizes Part 1 to "joltage machines" where buttons increment counters and each counter has a target value. The problem is represented as a linear system A*x = b. Reduced Row Echelon Form (RREF) identifies pivot and free variables. DFS over all free variable combinations finds the minimum total button presses that satisfy all counters.

    [![Puzzle](https://img.shields.io/badge/Puzzle-View-blue?style=flat)](AoC2025/puzzles/Day10Puzzle.txt)
    [![Input](https://img.shields.io/badge/Input-View-blue?style=flat)](AoC2025/inputs/Day10Input.txt)
    [![Part 1](https://img.shields.io/badge/Part%201-Code-lightgrey?style=flat)](AoC2025/src/main/java/day10/Day10Part1.java)
    [![Part 2](https://img.shields.io/badge/Part%202-Code-lightgrey?style=flat)](AoC2025/src/main/java/day10/Day10Part2.java)

