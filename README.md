# DAA Assignment 4 — Graph Algorithms for Task Scheduling & Dependency Analysis

This project demonstrates the use of **graph algorithms** to analyze complex task dependencies in a Smart City / Smart Campus scheduling scenario.  
The system identifies strongly connected tasks, forms a Directed Acyclic Graph (DAG), determines a valid execution sequence, and computes both **shortest** and **critical (longest)** task completion paths.

---

## ✨ Objectives

- Detect **Strongly Connected Components (SCCs)** using **Tarjan’s Algorithm**
- Construct the **Condensation Graph (DAG)** from SCCs
- Generate a **Topological Ordering** (valid dependency-safe execution order)
- Compute:
    - **Shortest Path** in the DAG (minimum execution cost/time)
    - **Longest / Critical Path** (bottleneck chain affecting total completion)

---

## 📂 Project Structure
```bash
src/
└─ main/java/
├─ graph/core # Graph + WeightedEdge
├─ graph/scc # Tarjan SCC + CondensationGraph
├─ graph/topo # Kahn Topological Sort
├─ graph/dagsp # Shortest & Longest Path in DAG
└─ app/App.java # CLI Entry Point

data/
└─ sample_tasks.json # Input dataset

```
---

## 🔧 Technologies Used

| Component | Tool |
|----------|------|
| Language | Java 17 |
| Build System | Maven |
| JSON Parsing | Gson |
| Execution & Testing | CLI Output |
| Version Control | Git + GitHub |

---

## ▶️ How to Compile & Run

### **Build Executable JAR**
```bash
mvn -q -DskipTests package

Execution Commands
| Mode          | Description             | Command                                                                        |
| ------------- | ----------------------- | ------------------------------------------------------------------------------ |
| `scc`         | Show SCC components     | `java -jar target/daa-assignment-4-all.jar scc data/sample_tasks.json`         |
| `topo`        | Topological sorting     | `java -jar target/daa-assignment-4-all.jar topo data/sample_tasks.json`        |
| `dag-sssp`    | Shortest path in DAG    | `java -jar target/daa-assignment-4-all.jar dag-sssp data/sample_tasks.json`    |
| `dag-longest` | Critical (Longest) path | `java -jar target/daa-assignment-4-all.jar dag-longest data/sample_tasks.json` |

📊 Results (on sample_tasks.json)
1) Strongly Connected Components (SCC)
SCC count = 6
Component 0: [3]
Component 1: [2]
Component 2: [5]
Component 3: [4]
Component 4: [1]
Component 5: [0]

Condensation DAG nodes = 6
✅ No cycles present → all tasks are independent in final DAG.

2) Topological Ordering
Topo order (components): [5, 4, 1, 3, 0, 2]
Derived order (original tasks): [0, 1, 2, 4, 3, 5]
✅ This ordering respects all dependencies → tasks can be executed in this sequence safely.

3) DAG Shortest Path
Distances from component(0 -> 5): [6, 3, 5, 4, 2, 0]
Minimum execution cost from starting node to all others.

4) DAG Longest / Critical Path
Longest distances from component 5: [6, 3, 5, 4, 2, 0]
Critical path (component indices) ends at 0 : [5, 4, 1, 0]
Critical Path (Converted to Original Tasks):

Task 0 → Task 1 → Task 4 → Task 3


🔥 These tasks must not be delayed, or the entire schedule completion time will increase.

✅ Conclusion

The graph contains no cyclic dependencies, ensuring safe scheduling.

A valid topological order of tasks was computed.

Shortest path gives optimal minimal execution cost.

Longest (Critical) path identifies tasks that determine total completion time.

This reflects real-world project planning, scheduling, and workflow optimization.

🏁 Submitted By

Name: Prince Sharma
Course: Design and Analysis of Algorithms (DAA)
Assignment: 4




