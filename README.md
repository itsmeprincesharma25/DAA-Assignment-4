DAA Assignment 4 — Graph Algorithms for Task Scheduling & Dependency Analysis

This project demonstrates the use of graph-based algorithms to analyze complex task dependencies in a Smart City / Smart Campus scheduling scenario.
The system identifies strongly connected groups of tasks, constructs a dependency-aware DAG, determines a valid execution order, and computes both shortest and critical (longest) task completion paths.

✨ Objectives

Detect Strongly Connected Components (SCCs) using Tarjan’s Algorithm

Construct the Condensation Graph (DAG) from SCCs

Generate a Topological Ordering of tasks (valid execution sequence)

Compute:

Shortest Path in the DAG (minimum cost completion sequence)

Longest Path / Critical Path (maximum dependency chain indicating bottleneck tasks)

📂 Project Structure
src/
└─ main/java/
├─ graph/core        # Graph + Weighted Edge
├─ graph/scc         # Tarjan SCC + Condensation Graph
├─ graph/topo        # Kahn Topological Sort
├─ graph/dagsp       # Shortest & Longest Path Algorithms
└─ app/App.java      # CLI Entry Point
data/
└─ sample_tasks.json     # Dataset used for testing

🔧 Technologies Used
Component	Tool
Language	Java 17
Build System	Maven
JSON Parsing	Gson
Algorithm Testing	CLI Output
Version Control	Git + GitHub
▶️ How to Compile & Run
Build (Fat Executable JAR)
mvn -q -DskipTests package

Run Modes
Mode	Description	Command
scc	Show Strongly Connected Components	java -jar target/daa-assignment-4-all.jar scc data/sample_tasks.json
topo	Show topological order	java -jar target/daa-assignment-4-all.jar topo data/sample_tasks.json
dag-sssp	DAG Single-Source Shortest Path	java -jar target/daa-assignment-4-all.jar dag-sssp data/sample_tasks.json
dag-longest	DAG Longest/Critical Path	java -jar target/daa-assignment-4-all.jar dag-longest data/sample_tasks.json
📊 Results on sample_tasks.json
1) Strongly Connected Components
   SCC count = 6
   Component 0: [3]
   Component 1: [2]
   Component 2: [5]
   Component 3: [4]
   Component 4: [1]
   Component 5: [0]
   Condensation DAG nodes = 6


✔ No cycles between tasks → each task is an independent node in DAG.

2) Topological Ordering
   Topo order (components): [5, 4, 1, 3, 0, 2]
   Derived order (original tasks): [0, 1, 2, 4, 3, 5]


✅ This is a valid execution sequence that respects dependencies.

3) Shortest Path in DAG
   Distances from component(0 -> 5): [6, 3, 5, 4, 2, 0]


Minimum cost from starting component (5 → representing task 0) to others.

4) Longest / Critical Path
   Longest distances from component 5: [6, 3, 5, 4, 2, 0]
   Critical path (component indices) ends at 0 : [5, 4, 1, 0]


Critical Path (in task terms):

Task 0 → Task 1 → Task 4 → Task 3


✅ This represents the maximum dependency chain and indicates tasks that must not be delayed.

📌 Conclusion

The graph has no cyclic dependencies → execution is feasible.

A valid scheduling order is successfully computed.

Shortest path gives optimal execution cost.

Longest (Critical) path reveals bottleneck tasks affecting overall completion time.