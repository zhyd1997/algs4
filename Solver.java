import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private int minMoves = -1;
    private ArrayList<Board> boards = new ArrayList<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Board searchNode = initial;
        Board prevSearchNode;
        if (searchNode.isGoal()) {
            minMoves = 0;
        } else {
            MinPQ<Board> pq = new MinPQ<>();
            // 1. insert the initial search node (the initial board, 0 moves, and a null previous search node) into a priority queue.
            pq.insert(searchNode);
            prevSearchNode = null;
            int i = 0;

            boards.add(searchNode);

            Board twin = searchNode.twin();

            while (searchNode != null && !searchNode.isGoal() && i < 128*128) {
                if (i == 0) {
                    minMoves = 0;
                }

                int min_manhattan = 1000000000;

                int count = 0;

                Board temp1 = null;
                Board temp2 = null;
                Board temp3 = null;
                Board temp4 = null;

                Board temp = null;
                // 2. delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes
                for (Board neighbor: searchNode.neighbors()) {
                    if (neighbor.equals(prevSearchNode)) {
                        continue;
                    }
                    if (count == 0) {
                        temp1 = neighbor;
                    } else if (count == 1) {
                        temp2 = neighbor;
                    } else if (count == 2) {
                        temp3 = neighbor;
                    } else if (count == 3) {
                        temp4 = neighbor;
                    }
                    count += 1;
                }

                if (temp1 != null) {
                    int a = temp1.manhattan();
                    if (a < min_manhattan) {
                        min_manhattan = a;
                        temp = temp1;
                    }
                }

                if (temp2 != null) {
                    int a = temp2.manhattan();
                    if (a < min_manhattan) {
                        min_manhattan = a;
                        temp = temp2;
                    }
                }

                if (temp3 != null) {
                    int a = temp3.manhattan();
                    if (a < min_manhattan) {
                        min_manhattan = a;
                        temp = temp3;
                    }
                }

                if (temp4 != null) {
                    int a = temp4.manhattan();
                    if (a < min_manhattan) {
                        min_manhattan = a;
                        temp = temp4;
                    }
                }

                prevSearchNode = searchNode;
                searchNode = temp;
                minMoves += 1;
                boards.add(searchNode);
                i += 1;
            }

            if (searchNode != null && !searchNode.isGoal()) {
                minMoves = -1;
                boards = null;
            }

            if (i == 0) {
                minMoves = -1;
                boards = null;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return minMoves >= 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (minMoves < 0) {
            return null;
        }
        return boards;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
